package plugins;

import java.util.Objects;

public class ClassMeta {
    private final String className;
    private final String pluginName;
    private final String classPath;

    public ClassMeta(String className, String pluginName, String classPath) {
        this.className = className;
        this.pluginName = pluginName;
        this.classPath = classPath;
    }

    public String getClassName() {
        return className;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getClassPath() {
        return classPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassMeta classMeta = (ClassMeta) o;
        return className.equals(classMeta.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className);
    }
}
