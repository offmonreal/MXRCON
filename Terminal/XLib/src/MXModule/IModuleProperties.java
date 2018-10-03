package MXModule;


//Минимальные свойсва любого модуля

public interface IModuleProperties
{

    public String getName();

    public String getDeveloper();

    public String getComment();

    public String getCategory();//"quotes_provider",

    public boolean enabledSetting();

    public int getVersion();
    
    public int getModuleApiVersion(); //Версия поддерживаемая приложением
}

