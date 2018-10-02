package mxrcon.Code;

import java.io.Serializable;

//Простой тип данных для хранения в настройках Ключ=Значение
@SuppressWarnings("serial") //или в классе обьявить ИД  private static final long serialVersionUID = 546546546556
public class SettingValueString implements Serializable
{
    String key;
    String value;

    public SettingValueString()
    {

    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String v)
    {
        this.value = v;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String k)
    {
        this.key = k;
    }
}