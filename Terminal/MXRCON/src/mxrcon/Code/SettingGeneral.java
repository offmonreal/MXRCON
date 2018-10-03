package mxrcon.Code;

import java.io.Serializable;
import java.util.ArrayList;

//Файл гланых настроек приложения
@SuppressWarnings("serial") //или в классе обьявить ИД  private static final long serialVersionUID = 546546546556
public class SettingGeneral implements Serializable
{

    private ArrayList<PluginInfo> enabled_providers_gs;

    public SettingGeneral()
    {

    }

    //Список провайдеров которые включены
    public void setEnabledProvidersGameServer(ArrayList<PluginInfo> value)
    {
        enabled_providers_gs = value;
    }

    //Список провайдеров которые включены
    public ArrayList<PluginInfo> getEnabledProvidersGameServer()
    {
        return enabled_providers_gs;
    }

}