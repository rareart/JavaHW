package proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    CacheTypes cacheType() default CacheTypes.IN_MEMORY;
    String fileNamePrefix() default "";
    boolean zip() default false;
    Class<?>[] identityBy() default {};
    long listList() default Long.MAX_VALUE;
    CuttingOptions listCuttingOption() default CuttingOptions.СUT_AFTER;
}
