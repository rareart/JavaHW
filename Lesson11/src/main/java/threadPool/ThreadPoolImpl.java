package threadPool;

public class ThreadPoolImpl implements ThreadPool {

    private final DequeBasedSynchronizedQueue<Runnable> queue;
    private final Thread[] threads;
    private Thread[] extraThreads;
    private Thread runner;
    private volatile int task_counter;
    private final boolean logs;
    private final boolean scalable;

    public ThreadPoolImpl(int fixedThreadPoolSize, boolean logs) throws IllegalArgumentException {
        queue = new DequeBasedSynchronizedQueue<>();
        if (fixedThreadPoolSize < 1) {
            throw new IllegalArgumentException("The number of threads must be positive.");
        }
        threads = new Thread[fixedThreadPoolSize];
        this.logs = logs;
        scalable = false;
    }

    public ThreadPoolImpl(int min, int max, boolean logs) throws IllegalArgumentException {
        queue = new DequeBasedSynchronizedQueue<>();
        if (min < 1 || min > max) {
            throw new IllegalArgumentException("The number of threads must be positive.\nThe maximum must be greater than the minimum.");
        }
        this.threads = new Thread[min];
        this.extraThreads = new Thread[max-min];
        this.logs = logs;
        scalable = true;
    }

    @Override
    public void start() {
        this.runner = new Thread(this::doThreads);
        runner.start();
    }

    @Override
    public void execute(Runnable runnable) {
        queue.add(runnable);
        if(runner!=null && !runner.isAlive()){
            if (logs) {
                System.out.println("Threads running again...");
            }
            start();
        }
    }

    public void finish(){
        //нужен для ожидания фонового потока-раннера главным потоком
        if(runner!=null){
            try {
                runner.join();
            } catch (InterruptedException e) {
                if (logs){
                    System.out.println(Thread.currentThread().getName() + " is interrupted");
                }
                Thread.currentThread().interrupt();
            }
        }
    }

    private void doThreads(){
        int internal_task_counter = task_counter;
        boolean allThreadsBusy = true;
        while (queue.notEmpty()){
            for (int i=0; i<threads.length; i++) {
                if ((threads[i] == null || !threads[i].isAlive()) && queue.notEmpty()) {
                    threads[i] = new Thread(queue.poll());
                    threads[i].setName("Thread " + (i+1));
                    threads[i].start();
                    if (logs){
                        System.out.println(threads[i].getName() + " is started with task " + ++internal_task_counter);
                    }
                    allThreadsBusy = false;
                } else {
                    allThreadsBusy = true;
                }
            }
            if (scalable && allThreadsBusy){
                for(int i=0; i<extraThreads.length; i++){
                    if ((extraThreads[i] == null || !extraThreads[i].isAlive()) && queue.notEmpty()) {
                        extraThreads[i] = new Thread(queue.poll());
                        extraThreads[i].setName("extraThread " + (i+1));
                        extraThreads[i].start();
                        if (logs){
                            System.out.println(extraThreads[i].getName() + " is started with task " + ++internal_task_counter);
                        }
                    }
                }
            }
        }
        for (Thread thread : threads) {
            if (thread != null && thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    if (logs){
                        System.out.println(Thread.currentThread().getName() + " is interrupted");
                    }
                    Thread.currentThread().interrupt();
                }
            }
        }
        if(scalable){
            for (Thread thread : extraThreads) {
                if (thread != null && thread.isAlive()) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        if (logs){
                            System.out.println(Thread.currentThread().getName() + " is interrupted");
                        }
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        task_counter = internal_task_counter;
    }
}
