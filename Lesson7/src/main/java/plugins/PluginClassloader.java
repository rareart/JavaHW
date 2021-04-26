package plugins;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginClassloader {
    private final List<ClassMeta> classLoaderHistory;
    private final URLClassLoader urlClassLoader;
    boolean log;

    public PluginClassloader(List<ClassMeta> classLoaderHistory, URLClassLoader urlClassLoader, boolean log){
        this.classLoaderHistory = classLoaderHistory;
        this.urlClassLoader = urlClassLoader;
        this.log = log;

    }

    public Class<?> loadClass(ClassMeta loadedClassMeta) throws IOException, ClassNotFoundException, URISyntaxException {
        if (log) {
            System.out.println("PCL: load class: \"" + loadedClassMeta.getClassName() + "\",");
            System.out.println("from: " + loadedClassMeta.getClassPath() + loadedClassMeta.getPluginName());
        }
        if (classLoaderHistory.contains(loadedClassMeta)) {
            for (ClassMeta meta : classLoaderHistory) {
                //если содержит то же имя класса, в той же директории (получить из кэша)
                String metaFullPath = meta.getClassPath()+meta.getPluginName();
                String loadedClassFullPath = loadedClassMeta.getClassPath()+loadedClassMeta.getPluginName();
                if (meta.getClassName().equals(loadedClassMeta.getClassName()) && metaFullPath.equals(loadedClassFullPath)) {
                    if(log){
                        System.out.println("PCL: loading exist class from URLClassLoader cache as \""
                                + loadedClassMeta.getClassName() + "\"");
                    }
                    return urlClassLoader.loadClass(loadedClassMeta.getPluginName()+ "." + loadedClassMeta.getClassName());
                }
            }
            for (ClassMeta meta : classLoaderHistory) {
                //если содержит то же имя класса, но в других директориях
                //загрузку выполняет новый объект класса URLClassLoader, для разрешения одинаковых имен
                //поэтому отдаваемый класс не кешируется основным urlClassLoader
                if (meta.getClassName().equals(loadedClassMeta.getClassName())) {
                    //classLoaderHistory.add(loadedClassMeta); - альтернативное поведение кеша
                    if(log){
                        System.out.println("PCL: loading another class with the same name by new URLClassLoader: " + "\"" + loadedClassMeta.getClassName() + "\"");
                        System.out.println("Warning: This class will not be cached!");
                    }
                    URL path = PluginClassloader.class.getResource(loadedClassMeta.getClassPath());
                    return (new URLClassLoader(
                            new URL[]{path})
                            .loadClass(loadedClassMeta.getPluginName()+ "." + loadedClassMeta.getClassName()));
                }
            }
        }
        //если содержт еще ни разу не загруженное имя класса
        classLoaderHistory.add(loadedClassMeta);
        if(log){
            System.out.println("PCL: loading new class by URLClassLoader as \""
                    + loadedClassMeta.getClassName() + "\"");
        }
        return urlClassLoader.loadClass(loadedClassMeta.getPluginName()+ "." + loadedClassMeta.getClassName());
    }

    public List<ClassMeta> getClassLoaderHistory() {
        return new ArrayList<>(classLoaderHistory);
    }
}
