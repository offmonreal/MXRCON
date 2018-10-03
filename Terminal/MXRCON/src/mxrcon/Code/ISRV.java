package mxrcon.Code;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.util.Pair;

public class ISRV implements Serializable
{

    String Provider = "";
    int VersionProvider = -1;
    String Host = "";
    int Port = -1;
    String Password = "";
    ArrayList<Pair<String, Integer>> IncludePluginsList = null;

    //
    public ISRV()
    {
        IncludePluginsList = new ArrayList<Pair<String, Integer>>();
    }

    //
    public void setProvider(String value)
    {
        Provider = value;
    }

    public String getProvider()
    {
        return Provider;
    }

    //
    public void setVersionProvider(int value)
    {
        VersionProvider = value;
    }

    public int getVersionProvider()
    {
        return VersionProvider;
    }

    //
    public void setHost(String value)
    {
        Host = value;
    }

    public String getHost()
    {
        return Host;
    }

    //
    public void setPort(int value)
    {
        Port = value;
    }

    public int getPort()
    {
        return Port;
    }

    //
    public void setPassword(String value)
    {
        Password = value;
    }

    public String getPassword()
    {
        return Password;
    }

    //
    public void setIncludePluginsList(ArrayList<Pair<String, Integer>> value)
    {
        IncludePluginsList = value;
    }

    public ArrayList<Pair<String, Integer>> getIncludePluginsList()
    {
        return IncludePluginsList;
    }
}
