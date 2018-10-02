package mxrcon.Code;

import MxModule.ProviderGames;
import MxModule.ModuleProperties;
import java.net.URLClassLoader;
import java.util.Vector;
import mxrcon.Utilites.Inline;

/**
 *
 * @author maxim
 */
public class Worker
{

    SettingGeneral setting = null;
    
    //
    ModuleProperties mp_providers_quotes = null;
    ProviderGames  m_providers_quotes = null;
    
    public Worker(SettingGeneral set)
    {
        setting = set;
    }

    //Загрузка модулей
    public void loadModules()
    {
        LibraryLoader ll = new LibraryLoader();
        //Список активных провайдеров котировк
        Vector<SettingValueString> modul_p_q = setting.getEnabledProvidersQuotes();

        if (modul_p_q != null)
        {
            for (SettingValueString v : modul_p_q)
            {
               URLClassLoader uc = ll.getModuleFile(DefineValues.PATH_FOLDER_MODULES_GAMES,"quotes_provider",v.key,Inline.toInt(v.value));
               
               if(uc != null)
               {
                   mp_providers_quotes = ll.findServiceProvider(ModuleProperties.class, uc);
                   m_providers_quotes = ll.findServiceProvider(ProviderGames.class, uc);
                   
                   if(mp_providers_quotes != null && m_providers_quotes != null)
                       break;
                   
               }
               
            }

        }

    }
}
