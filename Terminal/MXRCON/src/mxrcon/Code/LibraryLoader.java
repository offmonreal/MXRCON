package mxrcon.Code;

import MxModule.ModuleProperties;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import static java.rmi.server.RMIClassLoader.loadClass;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import javax.xml.datatype.DatatypeConfigurationException;

public class LibraryLoader
{

    //Получить список плагинов ПРОВАЙДЕРА КОТИРОВОК имена файлов
    public String[] getListProvidersQuotes()
    {
        //Место храннения плагинов
        File loc = new File(DefineValues.PATH_FOLDER_MODULES_GAMES);

        //Фильтер по расширению
        File[] flist = loc.listFiles((File file) -> file.getPath().toLowerCase().endsWith(".jar"));

        if (flist != null && flist.length > 0)
        {
            String[] all_items = new String[flist.length];

            for (int x = 0; x < flist.length; x++)
            {
                all_items[x] = flist[x].getName();
            }

            return all_items;

        }

        //Нет ничего
        return new String[]
        {
            "NONE"
        };

    }

    //Модуль из класса
    void XXtest(File[] flist)
    {
        try
        {
            URL[] urls = new URL[flist.length];

            for (int i = 0; i < flist.length; i++)
            {
                urls[i] = flist[i].toURI().toURL();
                System.out.println("XSTEP 1");
            }

            URLClassLoader ucl = new URLClassLoader(urls);

            ServiceLoader<ModuleProperties> sl = ServiceLoader.load(ModuleProperties.class, ucl);
            Iterator<ModuleProperties> apit = sl.iterator();
            while (apit.hasNext())
            {
                System.out.println("XSTEP X " + apit.next().getName());
            }

            System.out.println("XSTEP 000");

        } catch (Exception e)
        {
            System.out.println("XSTEP Exception");
        }

    }

    //Свойсва плагина по пути имени файла плагина 
    public URLClassLoader getModuleFile(String path, String Category, String Name, int version)
    {
        try
        {
            //Место храннения плагинов
            File loc = new File(path);
            File[] flist = loc.listFiles((File file) -> file.getPath().toLowerCase().endsWith(".jar"));

            for (File f : flist)
            {
                URL[] urls = new URL[1];
                urls[0] = f.toURI().toURL();
                URLClassLoader ucl = new URLClassLoader(urls);
                ModuleProperties m = findServiceProvider(ModuleProperties.class, ucl);

                if (m!= null && m.getCategory().equals(Category) && m.getName().equals(Name) && m.getVersion() == version && m.getModuleApiVersion() == DefineValues.MODULE_VERSION_SUPPORT)
                    return ucl;

            }
            
            return null;

        } catch (Exception e)
        {
            return null;
        }
    }

    //Свойсва плагина по имени файла плагина ПРОВАЙДЕРА КОТИРОВОК
    public ModuleProperties getPluginPropertiesProvidersQuotes(String name)
    {
        try
        {
            //Место храннения плагинов
            File loc = new File(DefineValues.PATH_FOLDER_MODULES_PROVIDERS_QUOTES);

            //Фильтер по расширению
            File[] flist = loc.listFiles((File file) -> file.getPath().toLowerCase().endsWith(".jar"));

            //XXtest(flist);
            for (File f : flist)
            {
                if (f.getName().equals(name))
                {
                    URL[] urls = new URL[1];
                    urls[0] = f.toURI().toURL();
                    URLClassLoader ucl = new URLClassLoader(urls);
                    return findServiceProvider(ModuleProperties.class, ucl);
                }
            }

            return null;

        } catch (Exception e)
        {
            return null;
        }

    }

    public <T> T findServiceProvider(final Class<T> type, URLClassLoader file_module)
    {
        try
        {
            //System.out.println("STEP 0");

            return AccessController.doPrivileged(new PrivilegedAction<T>()
            {
                public T run()
                {
                    //System.out.println("STEP 1");
                    ServiceLoader<T> serviceLoader = ServiceLoader.load(type, file_module);
                    Iterator<T> iterator = serviceLoader.iterator();
                    if (iterator.hasNext())
                    {
                        //System.out.println("STEP XXXXXXXXXXXXX");
                        return iterator.next();
                    } else
                    {
                        //System.out.println("STEP null");
                        return null;
                    }
                }
            });

        } catch (Exception e)
        {
            //System.out.println("STEP Exception");
            return null;
        }
    }

    //Загрузка .DLL или .SO
    public static boolean LoadLibrary()
    {
        String arhitecture = System.getProperty("os.arch");
        String osname = System.getProperty("os.name");
        String path_to_file = "modules/lib/" + arhitecture.toLowerCase() + File.separator + osname.toLowerCase();
        String lib_file_name = "";
        switch (osname.toLowerCase())
        {
            case "linux":
            {
                lib_file_name = "/libcore.so";
                break;
            }

            case "windows":
            {
                lib_file_name = "/libcore.dll";
                break;
            }

            default:
            {
                return false;
            }
        }

        path_to_file += lib_file_name;

        File file = new File(path_to_file);

        if (file.exists())
        {
            String folder_lib = file.getAbsolutePath();
            int idx = folder_lib.indexOf(lib_file_name);
            folder_lib = folder_lib.substring(0, idx);

            try
            {

                Field usrPathsField;
                usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
                usrPathsField.setAccessible(true);

                // Получаем массив текущик путей
                final String[] paths = (String[]) usrPathsField.get(null);

                // Проверяем есть ли уже путь 
                for (String path : paths)
                {
                    if (path.equals(folder_lib))
                    {
                        return true;
                    }
                }

                //добавляем новый путь
                String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
                newPaths[newPaths.length - 1] = folder_lib;
                usrPathsField.set(null, newPaths);

                return true;

            } catch (Exception ex)
            {
                ex.printStackTrace();
                return false;
            }
        } else
        {
            System.out.println("LIBCORE NOT EXISTS!");
            System.out.println(file.getAbsolutePath());

            return false;
        }

        //String oldpath = System.getProperty("java.library.path");
        //oldpath += ":" + "/home/maxim/MxChat/MxChat/PC/MxChatFX/JavaFXApplication2/modules/lib/";
        //System.setProperty("java.library.path", oldpath);
    }

}
