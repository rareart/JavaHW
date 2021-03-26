package service;

import proxy.Cache;

import java.time.LocalDate;
import java.util.List;

import static proxy.CacheTypes.FILE;
import static proxy.CacheTypes.IN_MEMORY;

public interface Service {
    @Cache(cacheType = FILE, fileNamePrefix = "cached_data", zip = true, identityBy = {String.class, double.class})
    List<String> run(String item, int size, double value, LocalDate date);

    @Cache(cacheType = IN_MEMORY, listList = 100_000)
    List<String> work(String item, int size);
}
