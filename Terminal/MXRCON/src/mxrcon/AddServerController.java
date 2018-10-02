/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mxrcon;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import mxrcon.Code.DefineValues;
import mxrcon.Code.SettingGeneral;
import mxrcon.Utilites.XmlWriter;

/**
 * FXML Controller class
 *
 * @author maxim
 */
public class AddServerController implements Initializable
{
     //Перевод интерфейса
    private ResourceBundle tr;
    //Основные натройки приложения в которых определены плагины которые активны
    SettingGeneral setting = null;
    
     //Измениляли ли настройки
    private boolean setting_reload_message = false;

     @FXML
    private Button B_Setting;
      @FXML
    private Button B_Cancel; //Надо для получения доступа к окну которое потом будем закрывать
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tr = rb;
        // TODO
    }  
    
    public void CloseWindows()
    {
        //Обязательно в XML назначить ид
        Window window = B_Cancel.getScene().getWindow();

        if (setting_reload_message)
        {
            XmlWriter.Write(setting, DefineValues.PATH_FILE_SETTING);
            new Alert(Alert.AlertType.WARNING, tr.getString("MSG_RESTART_TERMINAL")).showAndWait();
            setting_reload_message = false;
        }

        //Закрываем окошко
        if (window instanceof Stage)
        {
            ((Stage) window).close();
        }
    }
    
     public void SetWindowsProperties(Stage stage)
    {
        //Заголовок
        stage.setTitle(tr.getString("WH_ProvidersGAMES"));

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
