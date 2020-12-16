package com.company.lesson1;

import java.util.Scanner;

public class SortArrays {
    private final Scanner input = new Scanner(System.in);
    private double[] arr;
    private boolean isSorted;

    public SortArrays(double[] inputArray){
        setArray(inputArray);
    }

    public void setArray(double[] inputArray){
        double[] tempArray = new double[inputArray.length];
        System.arraycopy(inputArray, 0, tempArray, 0, inputArray.length);
        this.arr = tempArray;
        //this.arr = inputArray;
        isSorted = false;
    }

    public double[] getArr() {
        return arr;
    }

    public void arrayOptions(){
        System.out.println("Введите 1 для сортировки пузырьком по возрастанию,\n2 - по убыванию, 3 - для бинарного поиска эл-та:");
        int selector = input.nextInt();
        switch (selector){
            case 1:
                bubbleSortMinToMax(true);
                break;
            case 2:
                bubbleSortMaxToMin();
                break;
            case 3:
                binarySearch();
                break;
            default:
                input.close();
                System.out.println("Ошибка ввода!");
                break;
        }
    }

    private void bubbleSortMinToMax(boolean info){
        double buf;
        boolean flag = true;
        for (int i = 0; i < (arr.length - 1) && flag; i++) {
            flag = false;
            for (int j = 0; j < arr.length - (i+1); j++) {
                if(arr[j]>arr[j+1]){
                    buf = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = buf;
                    flag = true;
                }
            }
        }

        isSorted = true;
        if(info) System.out.println("Массив был отсортирован по возрастанию!"); else binarySearch();
    }
    private void bubbleSortMaxToMin(){
        double buf;
        boolean flag = true;
        for (int i = 0; i < (arr.length - 1) && flag; i++) {
            flag = false;
            for (int j = 0; j < arr.length - (i+1); j++) {
                if(arr[j]<arr[j+1]){
                    buf = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = buf;
                    flag = true;
                }
            }
        }

        System.out.println("Массив был отсортирован по убыванию!");
        isSorted = true;
    }

    private void binarySearch(){
        if(!isSorted){
            bubbleSortMinToMax(false);
        } else {
            System.out.println("Введите искомое значние:");
            double key = input.nextDouble();
            int low = 0;
            int high = arr.length-1;
            int index = -1;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (arr[mid] < key) {
                    low = mid + 1;
                } else if (arr[mid] > key) {
                    high = mid - 1;
                } else if (arr[mid] == key) {
                    index = mid;
                    break;
                }
            }
            if(index == -1) {
                System.out.println("Ошибка: искомое значение отсутствует в массиве!");
            } else {
                System.out.println("Искомый индекс в отсортированном массиве для значения " + key + " равен [" + index + "].");
            }
        }
    }

}
