package mxrcon.Utilites;

import java.io.FileOutputStream;
import java.net.URLClassLoader;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class XmlWriter
{

    public static boolean Write(Object obj, String path)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(path);
            XMLEncoder xe = new XMLEncoder(fos);
            xe.writeObject(obj);
            xe.close();
            return true;

        } catch (Exception e)
        {
            return false;
        }
    }

    @SuppressWarnings (value="unchecked") //Отключаем варинг преобразования типов которые неможет отследить Java
    public static <T> T Load(Class<T> type, String path)
    {
        try
        {
            FileInputStream fis = new FileInputStream(path);
            BufferedInputStream bis = new BufferedInputStream(fis);
            XMLDecoder xmlDecoder = new XMLDecoder(bis);
            Object obj = xmlDecoder.readObject();

            if (type.isAssignableFrom(obj.getClass()))
            {
                return (T) obj;
            }
            return null;

        } catch (Exception e)
        {
            return null;
        }
    }

}
