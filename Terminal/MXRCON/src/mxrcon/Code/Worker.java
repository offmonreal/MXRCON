package mxrcon.Code;

import java.net.URLClassLoader;
import java.util.Vector;
import mxrcon.Utilites.Inline;
import MXModule.IModuleProperties;
import MXModule.IServerProvider;

/**
 *
 * @author maxim
 */
public class Worker
{

    SettingGeneral setting = null;
    
    //
    IModuleProperties mp_providers_quotes = null;
    IServerProvider  m_providers_quotes = null;
    
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
               URLClassLoader uc = ll.getModuleFile(DefineValues.PATH_FOLDER_PLUGIN,"game_provider",v.key,Inline.toInt(v.value));
               
               if(uc != null)
               {
                   mp_providers_quotes = ll.findServiceProvider(IModuleProperties.class, uc);
                   m_providers_quotes = ll.findServiceProvider(IServerProvider.class, uc);
                   
                   if(mp_providers_quotes != null && m_providers_quotes != null)
                       break;
                   
               }
               
            }

        }

    }
}
