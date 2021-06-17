import java.util.ArrayList;
import java.util.List;

public class Task2 {
    public static void main(String[] args) {
        demo1();
        //demo2();
    }

    public static void demo1(){
        Object object;
        List<Object> list = new ArrayList<>();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                Thread.sleep(500);
                //object = new byte[1024*1024]; //for eden demo only
                list.add(new byte[1024*1024]);
            } catch (OutOfMemoryError e){
                e.printStackTrace();
                list.clear();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void demo2(){
        List<Object> list = new ArrayList<>();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0; i<100; i++){
            try {
                Thread.sleep(10);
                list.add(new byte[1024*1024]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e){
                e.printStackTrace();
                list.clear();
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.clear();
            while (true){

            }

        }
    }
}
