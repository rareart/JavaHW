package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServiceImpl implements Service {

    private final Random random;

    public ServiceImpl(){
        random = new Random();
    }

    @Override
    public List<String> run(String item, int size, double value, LocalDate date) {
        List<String> result = new ArrayList<>();
        for(int i = 1; i<=size; i++){
            Double buf = random.nextDouble()*value*i;
            result.add(buf.toString());
        }
        return result;
    }

    @Override
    public int calc(String name, int num, int multiple) {
        return num*multiple;
    }

    @Override
    public List<String> work(String item, int size) {
        List<String> result = new ArrayList<>();
        for(int i = 1; i<=size; i++){
            Double buf = random.nextDouble()*10D*i;
            result.add(buf.toString());
        }
        return result;
    }

    @Override
    public List<String> work2(String item, int size) {
        List<String> result = new ArrayList<>();
        for(int i = 1; i<=size; i++){
            Double buf = random.nextDouble()*10D*i;
            result.add(buf.toString());
        }
        return result;
    }
}
