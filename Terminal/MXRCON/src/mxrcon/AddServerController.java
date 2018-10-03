package mxrcon;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;
import mxrcon.Code.DefineValues;
import mxrcon.Code.SettingGeneral;
import mxrcon.Utilites.XmlWriter;


public class AddServerController implements Initializable
{
      //Перевод интерфейса
    private ResourceBundle tr;
    //Основные натройки приложения в которых определены плагины которые активны
    SettingGeneral setting = null;
    
    @FXML
    private Button B_Ok; //Надо для получения доступа к окну которое потом будем закрывать
    
    @FXML
    private ListView<String> listPlugin;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
         tr = rb;
        // TODO
    }    
    
    
     public void CloseWindows()
    {
        //Обязательно в XML назначить ид
        Window window = B_Ok.getScene().getWindow();

        //Закрываем окошко
        if (window instanceof Stage)
        {
            ((Stage) window).close();
        }
    }

    public void SetWindowsProperties(Stage stage)
    {
        //Заголовок
        stage.setTitle(tr.getString("WH_AddServer"));

        //Ограничения окна
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setMaxWidth(800);
        stage.setMaxHeight(600);

        stage.setFullScreen(false);//Запретить на весь экран
        //stage.setMaximized(true);//сделать размер таким же, как мы делаем размер, нажав на строку заголовка

    }

    public void setSetting(SettingGeneral value)
    {
        setting = value;
    }
    
}
