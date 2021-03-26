package task5;

public class HeavyCalcImpl implements HeavyCalc {
    @Override
    public double cachedCalc(int value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return value*0.5D;
    }

    @Override
    public double unCachedCalc(int value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return value*0.5D;
    }

    @Override
    public double cachedUnsupportedCalc(int value, int value2){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return value*0.5D;
    }

    @Override
    public double unCachedUnsupportedCalc(String value, int value2) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return value2*0.5D;
    }


}
