package MinecraftPE;

import MXModule.IModuleProperties;

public class ModuleInfo implements IModuleProperties
{

    @Override
    public String getName()
    {
        return "MinecraftPE";
    }

    @Override
    public String getDeveloper()
    {
        return "offmonreal.com";
    }

    @Override
    public String getComment()
    {
        return "Free! for Server https://pmmp.io PocketMine-MP 3.2.3";
    }

    @Override
    public String getCategory()
    {
        return "game_provider";//Обязательно
    }

    @Override
    public boolean enabledSetting()
    {
        return false;
    }

    @Override
    public int getVersion()
    {
        return 1;
    }
    
        
    @Override
    public int getModuleApiVersion()
    {
        return 1;
    }
    
    
}
