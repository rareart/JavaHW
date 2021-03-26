package proxy;

public class CacheProxy {

    private boolean defaultZipEnabled;
    private Cache defaultCacheType;
    private String rootFolder;

    public CacheProxy() {
        rootFolder = "";
    }

    public CacheProxy(String rootFolder, boolean defaultZipEnabled, Cache defaultCacheType) {
        this.rootFolder = "/" + rootFolder;
        this.defaultZipEnabled = defaultZipEnabled;
        this.defaultCacheType = defaultCacheType;
    }

    public <T> T cache(T newCachedObj){

        return null; //дописать
    }
}
