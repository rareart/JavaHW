package plugins;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private final List<ClassMeta> classLoaderHistory;
    private final URLClassLoader urlClassLoader;
    private final String pluginRootDirectory;
    private boolean log;

    public PluginManager(String pluginRootDirectory, boolean logs) throws PluginLoadException {
        this.pluginRootDirectory = "/" + pluginRootDirectory + "/";
        this.classLoaderHistory = new ArrayList<>();
        this.logs(logs);
        URL path = PluginManager.class.getResource(this.pluginRootDirectory);
        if(path==null){
            throw new PluginLoadException("Error: wrong path to the plugin root directory");
        }
        urlClassLoader = new URLClassLoader(new URL[]{path});
    }

    public Plugin pluginLoad(String pluginName, String pluginClassName) throws PluginLoadException{
        PluginClassloader pluginClassloader = new PluginClassloader(classLoaderHistory, urlClassLoader, log);
        Class<?> loadedPluginClass;
        ClassMeta loadedClassMeta = new ClassMeta(pluginClassName, pluginName, pluginRootDirectory);
        try {
            loadedPluginClass = pluginClassloader.loadClass(loadedClassMeta);
        } catch (IOException e) {
            throw new PluginLoadException("Error: class reading error", e);
        } catch (ClassNotFoundException e) {
            throw new PluginLoadException("Error: required class not found", e);
        } catch (URISyntaxException e) {
            throw new PluginLoadException("Error: wrong class path if PluginClassloader direct loader", e);
        }
        if(loadedPluginClass!=null){
            try {
                return (Plugin) loadedPluginClass.getConstructor().newInstance();
            } catch (InstantiationException e) {
                throw new PluginLoadException("Error: plugin's new instance instantiation error", e);
            } catch (IllegalAccessException e) {
                throw new PluginLoadException("Error: constructor access error", e);
            } catch (InvocationTargetException e) {
                throw new PluginLoadException("Error: internal plugin's constructor threw out his own exception", e);
            } catch (NoSuchMethodException e) {
                throw new PluginLoadException("Error: unsupported constructor with not-a-null params", e);
            }
        } else {
            throw new PluginLoadException("Error: loaded class is a null");
        }
    }

    public void logs(boolean isActive){
        this.log = isActive;
    }

    public void closePluginManager() throws PluginLoadException {
        try {
            urlClassLoader.close();
            if(log){
                System.out.println("Closed");
            }
        } catch (IOException e) {
            throw new PluginLoadException("Error: file opened by this class loader resulted in an IOException", e);
        }
    }
}
