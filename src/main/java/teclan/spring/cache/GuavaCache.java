package teclan.spring.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import teclan.spring.util.PropertyConfigUtil;

import java.util.concurrent.TimeUnit;

public class GuavaCache {
    private final static PropertyConfigUtil propertyconfigUtil;
    private static int sessionTimeout = 10;
    private static int guavaCacheSize = 10;
    private static int guavaConcurrencyLevel = 10;


    static {
        propertyconfigUtil = PropertyConfigUtil.getInstance("config.properties");
        sessionTimeout = propertyconfigUtil.getIntValue("sesseionTimeout");
        guavaCacheSize = propertyconfigUtil.getIntValue("guavaCacheSize");
        guavaConcurrencyLevel = propertyconfigUtil.getIntValue("guavaConcurrencyLevel");
    }

    private static Cache<String, String> cache =null;

    public static Cache<String, String> init(){
        if(cache==null){
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(guavaCacheSize)
                    //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
                    .concurrencyLevel(guavaConcurrencyLevel)
                    .expireAfterWrite(sessionTimeout, TimeUnit.MINUTES)
                    //构建cache实例
                    .build();
        }

        return cache;
    }


    public static Cache<String, String> init(int size,int level,int timeout){
        if(cache==null){
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(size)
                    //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
                    .concurrencyLevel(level)
                    .expireAfterWrite(timeout, TimeUnit.MINUTES)
                    //构建cache实例
                    .build();
        }
        return cache;
    }



    public static void set(String key, String value) {
        cache.put(key, value);
    }

    public static String get(String key) {
       return cache.getIfPresent(key);
    }

    public static void remove(String key){
        cache.invalidate(key);
    }


    public static void clear() {
        cache.invalidateAll();
    }


}
