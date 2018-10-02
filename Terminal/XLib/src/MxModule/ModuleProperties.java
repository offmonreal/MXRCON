package MxModule;

//Минимальные свойсва любого модуля

public interface ModuleProperties
{

    public String getName();

    public String getDeveloper();

    public String getComment();

    public String getCategory();//"quotes_provider",

    public boolean enabledSetting();

    public int getVersion();
    
    public void showSetting();
    
    public int getModuleApiVersion(); //Версия поддерживаемая приложением
}

