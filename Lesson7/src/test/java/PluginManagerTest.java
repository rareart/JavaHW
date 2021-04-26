import org.junit.Test;
import plugins.Plugin;
import plugins.PluginLoadException;
import plugins.PluginManager;

import static org.junit.Assert.*;

public class PluginManagerTest {
    @Test
    public void pluginManagerTest() throws PluginLoadException {
        PluginManager pluginManager = new PluginManager("pluginRoot", true);
        Plugin plugin1A = pluginManager.pluginLoad("plugin1", "PluginA");
        System.out.println();
        Plugin plugin1A2 = pluginManager.pluginLoad("plugin1", "PluginA");
        System.out.println();
        Plugin plugin1B = pluginManager.pluginLoad("plugin1", "PluginB");
        System.out.println();
        Plugin plugin2C = pluginManager.pluginLoad("plugin2", "PluginC");
        System.out.println();
        Plugin plugin2A = pluginManager.pluginLoad("plugin2", "PluginA");
        System.out.println();
        Plugin plugin2A2 = pluginManager.pluginLoad("plugin2", "PluginA");
        System.out.println();
        System.out.println("------------------------------");
        plugin1A.doUseful();
        plugin1A2.doUseful();
        plugin1B.doUseful();
        plugin2C.doUseful();
        plugin2A.doUseful();
        plugin2A2.doUseful();
        assertEquals(plugin1A.getClass(), plugin1A2.getClass());
        assertNotEquals(plugin1A, plugin2A);
        assertNotEquals(plugin2A, plugin2A2); //Ограничения кеширования в связи с использованием готовых реализаций ClassLoader,
        //посчитал, что по условию задания так будет правильнее, т.к. кастом класслоадер идет уже вторым заданием.
        //В кеше лежит класс, первым занявший уникальное имя класса. Все его дубликаты всегда грузятся заново и "not equals".
        System.out.println("------------------------------");
    }
}
