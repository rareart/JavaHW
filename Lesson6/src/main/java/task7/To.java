package task7;

import java.util.Date;
import java.util.List;

public class To {
    private int value1;
    private double value2;
    private long value3;
    private String someText;
    private List<Long> longList;
    private List<Long> anotherLongList;
    private Date date;
    private Number number;

    public Number getNumber() {
        return number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    public void setValue3(long value3) {
        this.value3 = value3;
    }

    public void setSomeText(String someText) {
        this.someText = someText;
    }

    public void setLongList(List<Long> longList) {
        this.longList = longList;
    }

    public void setAnotherLongList(List<Long> anotherLongList) {
        this.anotherLongList = anotherLongList;
    }

    public int getValue1() {
        return value1;
    }

    public double getValue2() {
        return value2;
    }

    public long getValue3() {
        return value3;
    }

    public String getSomeText() {
        return someText;
    }

    public List<Long> getLongList() {
        return longList;
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


