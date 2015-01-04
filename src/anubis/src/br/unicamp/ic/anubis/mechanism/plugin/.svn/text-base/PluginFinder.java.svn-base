package br.unicamp.ic.anubis.mechanism.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import br.unicamp.ic.anubis.plugin.IPlugin;

class JarFileFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        return (name.endsWith(".jar"));
    }
}

public class PluginFinder {
	
	// Parameters

    private static final String PLUGIN_INTERFACE_NAME = "br.unicamp.ic.anubis.plugin.IPlugin";
	private static final Class[] parameters = new Class[]{URL.class};
    private List<IPlugin> pluginCollection;
    
    public PluginFinder() {
        pluginCollection = new ArrayList<IPlugin>();
    }
    
    public void search(String directory) throws PluginException {
        File dir = new File(directory);
        if (dir.isFile()) {
            return;
        }
        File[] files = dir.listFiles(new JarFileFilter());
        for (File f : files) {
        	
            List<String> classNames = getClassNames(f.getAbsolutePath());
            for (String className : classNames) {
                // Remove the ".class" at the back
                String name = className.substring(0, className.length() - 6);
                Class clazz;
				try {
					clazz = getClass(f, name);
				} catch (Exception ex) {
					throw new PluginException("Unable to create plugin "+className, ex);
				}
                Class[] interfaces = clazz.getInterfaces();
                for (Class c : interfaces) {
                    // Check if this class implements the IPlugin interface
                    if (c.getName().equals(PLUGIN_INTERFACE_NAME)) {
                       try {
                    	   IPlugin pluginInstance = (IPlugin)clazz.newInstance();
						pluginCollection.add(pluginInstance );
					} catch (InstantiationException iex) {
						throw new PluginException("Unable to create instance of plugin "+className,iex);
					} catch (IllegalAccessException iaEx) {
						throw new PluginException("Access violation when creating plugin "+className,iaEx);
					}
                    }
                }
            }
        }
    }
    
    protected List<String> getClassNames(String jarName) throws PluginException{
    	    	
        try {
			ArrayList<String> classes = new ArrayList<String>(10);
			JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
			JarEntry jarEntry;
			while (true) {
			    jarEntry = jarFile.getNextJarEntry();
			    if (jarEntry == null) {
			        break;
			    }
			    if (jarEntry.getName().endsWith(".class")) {
			        classes.add(jarEntry.getName().replaceAll("/", "\\."));
			    }
			}

			return classes;
		} catch (FileNotFoundException fnfEx) {
			throw new PluginException("JAR file "+jarName+" was not found", fnfEx);
		} catch (IOException ex) {
			throw new PluginException("Error opening file "+jarName, ex);
		}
    }
    
    private Class getClass(File file, String name) throws Exception {
        addURL(file.toURI().toURL());

        URLClassLoader clazzLoader;
        Class clazz;
        String filePath = file.getAbsolutePath();
        filePath = "jar:file://" + filePath + "!/";
        URL url = new File(filePath).toURI().toURL();
        clazzLoader = new URLClassLoader(new URL[]{url});
        clazz = clazzLoader.loadClass(name);
        return clazz;

    }

    private void addURL(URL u) throws PluginException {
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL urls[] = sysLoader.getURLs();
        for (int i = 0; i < urls.length; i++) {
            if (urls[i].toString().equalsIgnoreCase(u.toString())) {
                return;
            }
        }
        Class sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysLoader, new Object[]{u});
        } catch (Exception  ex) {
            throw new PluginException("Error, could not add URL to system classloader",ex);
        }
    }
    
    /**
     * Get the list of plugins found
     * 
     * @return
     */
	public List<IPlugin> getPluginCollection() {
		return pluginCollection;
	}


}
