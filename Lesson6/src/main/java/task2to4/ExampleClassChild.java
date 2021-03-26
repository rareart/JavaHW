package task2to4;

public class ExampleClassChild extends ExampleClass {

    int anotherSomeNum;
    public static final String MONDAY = "MONDAY";
    private static final String SUNDAY = "SUNDAY";
    private final String SATURDAY = "SATURDAY";
    private String text;

    public ExampleClassChild(int someNumber, int anotherSomeNum) {
        super(someNumber);
        this.anotherSomeNum = anotherSomeNum;
        this.text = "some text";
    }

    public int getAnotherSomeNum() {
        return anotherSomeNum;
    }

    public void setAnotherSomeNum(int anotherSomeNum) {
        this.anotherSomeNum = anotherSomeNum;
    }

    public String getMONDAY() {
        return MONDAY;
    }

    public String getSUNDAY() {
        return SUNDAY;
    }

    public int anotherSomeCalc(int inputValue){
        return legacyCalc(inputValue)*getConst()*anotherSomeNum;
    }

    private int legacyCalc(int inputValue){
        return super.calcSome(inputValue);
    }

    private int getConst(){
        return 100;
    }
}
