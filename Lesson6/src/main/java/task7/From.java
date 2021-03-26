package task7;

import java.util.ArrayList;
import java.util.List;

public class From {
    private final int value1;
    private final double value2;
    private final double value3;
    private final String someText;
    private final List<Long> longList;
    private final String anotherText;
    private final ArrayList<Long> anotherLongList;
    private final Double aDouble;

    public From(){
        value1 = 111;
        value2 = 111.11D;
        value3 = 222.22D;
        someText = "awesome";
        longList = new ArrayList<>();
        longList.add(1111111L);
        longList.add(2222222L);
        anotherText = "another awesome";
        anotherLongList = new ArrayList<>();
        anotherLongList.add(3333333L);
        anotherLongList.add(4444444L);
        aDouble = 777.77D;
    }

    public Double getNumber() {
        return aDouble;
    }

    public int getValue1() {
        return value1;
    }

    public double getValue2() {
        return value2;
    }

    public double getValue3() {
        return value3;
    }

    public String getSomeText() {
        return someText;
    }

    public List<Long> getLongList() {
        return longList;
    }

    public String getAnotherText() {
        return anotherText;
    }

    public List<Long> getAnotherLongList() {
        return anotherLongList;
    }

    public int calcSquare(int value){
        return value*value;
    }

    public void setStringToVoid(String str){

    }
}
