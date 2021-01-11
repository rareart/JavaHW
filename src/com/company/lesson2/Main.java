package com.company.lesson2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //carListsByTypes();
        //textTask1();
        //textTask2();
        //textTask3();
        //textTask4();
        //Задание 5 реализовано в классе ReverseIterator и использовано в textTask4()
        //textTask6();
    }

    public static void carListsByTypes(){
        ArrayList<Car> carArrayList = new ArrayList<>();
        CarListLoader.init(carArrayList);
        System.out.println("Original cars list: ");
        CarListLoader.print(carArrayList);
        System.out.println("----------------------------");
        ListsByTypes groupedLists = new ListsByTypes(carArrayList);
        groupedLists.printLists();
    }

    //Actions With Text (Part2):

    public static void textTask1(){
        FileReader text = new FileReader("lesson2_file1.txt");
        System.out.println("Number of unique words in this text is " + text.getNumberOfUniqueWords());
        text.closeFile();
    }

    public static void textTask2(){
        FileReader text = new FileReader("lesson2_file1.txt");
        text.printUniqueSortedWords();
        text.closeFile();
    }

    public static void textTask3(){
        FileReader text = new FileReader("lesson2_file1.txt");
        text.PrintWordFrequency();
        text.closeFile();
    }

    public static void textTask4(){
        FileReader text = new FileReader("lesson2_file1.txt");
        text.printReverseLines();
        text.closeFile();
    }

    public static void textTask6(){
        FileReader text = new FileReader("lesson2_file1.txt");
        text.printSelectedLines();
        text.closeFile();
    }

}
