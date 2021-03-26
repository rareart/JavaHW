package task2to4;

public class ExampleClass {
    private int someNumber;

    public ExampleClass(int someNumber) {
        this.someNumber = someNumber;
    }

    public int getSomeNumber() {
        return someNumber;
    }

    public void setSomeNumber(int someNumber) {
        this.someNumber = someNumber;
    }

    public int calcSome(int inputNum){
        return someNumber*inputNum;
    }

    private double getPi(){
        return 3.14D;
    }
}
