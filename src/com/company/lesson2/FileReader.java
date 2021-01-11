package com.company.lesson2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {
    private ArrayList<String> listOfLines;
    private Scanner input;
    private ListIterator<String> listOfLinesIterator;
    private final String filename;

    public FileReader(String filename){
        this.filename = filename;
        try {
            ScannerInit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File " + this.filename + " not found");
        }
        ArrayListInit();
    }

    public void closeFile(){
        input.close();
    }

    public int getNumberOfUniqueWords(){
        HashSet<String> uniqueWords = new HashSet<>();
        while (listOfLinesIterator.hasNext()){
            String[] wordsFromLine = listOfLinesIterator.next().replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
            uniqueWords.addAll(Arrays.asList(wordsFromLine));
        }
        return uniqueWords.size();
    }

    public void printUniqueSortedWords(){
        HashSet<String> uniqueWords = new HashSet<>();
        while (listOfLinesIterator.hasNext()){
            String[] wordsFromLine = listOfLinesIterator.next().replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
            uniqueWords.addAll(Arrays.asList(wordsFromLine));
        }
        ArrayList<String> sortedUniqueWords = new ArrayList<>(uniqueWords);
        StringComparator stringComparator = new StringComparator();
        sortedUniqueWords.sort(stringComparator);
        for (String sortedUniqueWord : sortedUniqueWords) {
            System.out.println(sortedUniqueWord);
        }

    }

    public void PrintWordFrequency(){
        ArrayList<String> words = new ArrayList<>();
        while (listOfLinesIterator.hasNext()){
            String[] wordsFromLine = listOfLinesIterator.next().replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
            words.addAll(Arrays.asList(wordsFromLine));
        }
        ListIterator<String> wordsIterator = words.listIterator();
        HashMap<String, Integer> wordFrequencyMap = new HashMap<>();
        for(String word : words){
            wordFrequencyMap.put(word, 0);
        }
        while (wordsIterator.hasNext()){
            Object word = wordsIterator.next();
            if(wordFrequencyMap.containsKey(word)){
                int tmpCounter = wordFrequencyMap.get(word);
                tmpCounter++;
                wordFrequencyMap.replace(word.toString(), tmpCounter);
            }
        }
        Iterator<Map.Entry<String,Integer>> wordFrequencyMapIterator = wordFrequencyMap.entrySet().iterator();
        System.out.println("Word frequency for this text:");
        while (wordFrequencyMapIterator.hasNext()){
            System.out.println(wordFrequencyMapIterator.next());
        }
    }

    public void printReverseLines(){
        ReverseIterator reverseItr = new ReverseIterator(listOfLines); //тут же использован Iterator из 5-го задания
        while (reverseItr.hasNext()){
            System.out.println(reverseItr.next());
        }
    }

    public void printSelectedLines(){
        Scanner fromKeyboard = new Scanner(System.in);
        System.out.println("Enter the number of required line:");
        int selector = fromKeyboard.nextInt();
        selector--;
        if(selector>=0 && selector<listOfLines.size()){
            System.out.println(listOfLines.get(selector));
        } else {
            System.out.println("Error: line not found");
        }
    }

    private void ArrayListInit(){
        listOfLines = new ArrayList<>();
        while (input.hasNextLine()){
            listOfLines.add(input.nextLine());
        }
        listOfLinesIterator = listOfLines.listIterator();
    }

    private void ScannerInit() throws FileNotFoundException {
        input = new Scanner(new File(this.filename));
    }
}
