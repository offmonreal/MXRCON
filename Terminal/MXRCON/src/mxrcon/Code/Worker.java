package mxrcon.Code;

import java.net.URLClassLoader;
import java.util.Vector;
import mxrcon.Utilites.Inline;
import MXModule.IModuleProperties;
import MXModule.IServerProvider;
import java.util.ArrayList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import mxrcon.Utilites.XmlWriter;

/**
 *
 * @author maxim
 */
public class Worker
{

    public SettingGeneral setting = null;
    public ServerList srv_all = null;
    private IMain root;
    private TabPane tabs;

    //
    IModuleProperties mp_providers_quotes = null;
    IServerProvider m_providers_quotes = null;

    public Worker(SettingGeneral set, IMain root_class)
    {
        setting = set;
        root = root_class;
        tabs = root.getTabPane();

        srv_all = XmlWriter.Load(ServerList.class, DefineValues.PATH_FILE_SERVERS);

        if (srv_all == null)
        {
            srv_all = new ServerList();
            XmlWriter.Write(srv_all, DefineValues.PATH_FILE_SERVERS);
        } else
        {
            LoadAllServer();
        }
    }

    public int AddServerConfiguration(ISRV srv)
    {
        int do_count = srv_all.getEnabledSRV().size();
        srv_all.setEnabledSRV(new ArrayList<ISRV>()
        {
            {
                add(srv);
            }
        });

        //Если такой сервер уже есть в конфигурации
        if (do_count == srv_all.getEnabledSRV().size())
        {
            return 0;
        }

        XmlWriter.Write(srv_all, DefineValues.PATH_FILE_SERVERS);
        addServerToGui(srv);
        return 1;
    }

    private void LoadAllServer()
    {
       ArrayList<ISRV> elements = srv_all.getEnabledSRV();
       
       for(ISRV e : elements)
       {
           addServerToGui(e);
       }
    }

    private void addServerToGui(ISRV srv)
    {
        LibraryLoader ll = new LibraryLoader();
       //Список активных провайдеров
        ArrayList<PluginInfo> all_enable_provider = setting.getEnabledProvidersGameServer();

        
        if(all_enable_provider != null)
        {
            return;
        }
        
        
        
        
        
        
        Tab t = new Tab();
        t.setText(srv.Provider + "-" + srv.Host + ":" + srv.Port);
        tabs.getTabs().add(t);
        
        
    }

    //Загрузка модулей
    public void loadModules()
    {
        LibraryLoader ll = new LibraryLoader();
        //Список активных провайдеров
        ArrayList<PluginInfo> modul = setting.getEnabledProvidersGameServer();

        if (modul != null)
        {
            for (PluginInfo v : modul)
            {
                URLClassLoader uc = ll.getModuleFile(DefineValues.PATH_FOLDER_PLUGIN, "game_provider", v.key, Inline.toInt(v.value));

                if (uc != null)
                {
                    mp_providers_quotes = ll.findServiceProvider(IModuleProperties.class, uc);
                    m_providers_quotes = ll.findServiceProvider(IServerProvider.class, uc);

                    if (mp_providers_quotes != null && m_providers_quotes != null)
                    {
                        break;
                    }

                }

            }

        }

    }
}
