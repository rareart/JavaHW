package task5;

public interface HeavyCalc {
    @Cache
    double cachedCalc(int value);

    double unCachedCalc(int value);

    @Cache
    double cachedUnsupportedCalc(int value, int value2);

    double unCachedUnsupportedCalc(String value, int value2);


}
