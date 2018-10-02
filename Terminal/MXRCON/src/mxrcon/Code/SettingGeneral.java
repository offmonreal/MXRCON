package mxrcon.Code;

import java.io.Serializable;
import java.util.Vector;

//Файл гланых настроек приложения
@SuppressWarnings("serial") //или в классе обьявить ИД  private static final long serialVersionUID = 546546546556
public class SettingGeneral implements Serializable
{

    private Vector<SettingValueString> enabled_providers_quotes;

    public SettingGeneral()
    {

    }

    //Список провайдеров котировок которые включены
    public void setEnabledProvidersQuotes(Vector<SettingValueString> value)
    {
        enabled_providers_quotes = value;
    }

    //Список провайдеров котировок которые включены
    public Vector<SettingValueString> getEnabledProvidersQuotes()
    {
        return enabled_providers_quotes;
    }

}