package utils.plugin;

import iface.Plugable;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by apryakhin on 20.05.2016.
 */
public class PluginFactory {

    public static ArrayList<Plugable> getPlugins() throws MalformedURLException {
        ArrayList<Plugable> rez = new ArrayList<Plugable>();
        File pluginDir = new File("plugins");
        File[] jars = pluginDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".jar");
            }
        });
        for (int i = 0; i < jars.length; i++) {
            try {
                URL jarURL = jars[i].toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
                JarFile jf = new JarFile(jars[i]);
                Enumeration<JarEntry> entries = jf.entries();
                while (entries.hasMoreElements()) {
                    String e = entries.nextElement().getName();
                    if (!e.endsWith(".class")) continue;
                    e = e.replaceAll("/", ".");
                    e = e.replaceAll(".class", "");
                    Class<?> plugCan = classLoader.loadClass(e);
                    Class<?>[] interfaces = plugCan.getInterfaces();
                    for (Class interf : interfaces) {
                        if (interf.getName().endsWith(".Plugable")) {
                            Class c = classLoader.loadClass(plugCan.getName());

                            int mods = c.getModifiers();

                            if(Modifier.isPublic(mods) && !Modifier.isAbstract(mods)){
                                System.out.println(c.getName());
                                Object inst = c.newInstance();
                                rez.add((Plugable)inst);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        return rez;
    }
}
