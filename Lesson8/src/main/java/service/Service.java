package service;

import proxy.Cache;

import java.time.LocalDate;
import java.util.List;

import static proxy.CacheTypes.FILE;
import static proxy.CacheTypes.IN_MEMORY;
import static proxy.CuttingOptions.CUT_AFTER;

public interface Service {
    @Cache(cacheType = FILE, identityBy = {String.class, double.class})
    List<String> run(String item, int size, double value, LocalDate date);

    @Cache(cacheType = FILE, identityBy = {String.class, int.class})
    int calc(String name, int num, int multiple);

    @Cache(cacheType = IN_MEMORY, listList = 20)
    List<String> work(String item, int size);

    @Cache(cacheType = IN_MEMORY, listList = 10, listCuttingOption = CUT_AFTER)
    List<String> work2(String item, int size);
}
