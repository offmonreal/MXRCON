package mxrcon.Code;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.util.Pair;

//Файл гланых настроек приложения
@SuppressWarnings("serial") //или в классе обьявить ИД  private static final long serialVersionUID = 546546546556
public class ServerList implements Serializable
{

    private ArrayList<ISRV> items = null;

    public ServerList()
    {
        items = new ArrayList<ISRV>();
    }

    //Список серверов которые включены
    public void setEnabledSRV(ArrayList<ISRV> value)
    {
        if (items != null)
        {
            for (ISRV vx : value)
            {
                boolean add = true;

                for (ISRV vy : items)
                {
                    
                    if (vx.Host.equals(vy.Host) && vx.Port == vy.Port)
                    {
                        add = false;
                        continue;
                    }
                }

                if (add)
                {
                    items.add(vx);
                }

            }
        } else
        {
            items = value;
        }
    }
    //Список провайдеров которые включены

    public ArrayList<ISRV> getEnabledSRV()
    {
        return items;
    }
}
