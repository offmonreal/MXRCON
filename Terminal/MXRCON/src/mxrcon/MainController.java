/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mxrcon;

import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.Platform.exit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import mxrcon.Code.DefineValues;
import mxrcon.Code.IMain;
import mxrcon.Code.SettingGeneral;
import mxrcon.Code.Worker;
import mxrcon.Utilites.XmlWriter;

/**
 *
 * @author maxim
 */
public class MainController implements Initializable, IMain
{

    //Перевод интерфейса
    private ResourceBundle tr;

    //Основные натройки приложения в которых определены плагины которые активны
    SettingGeneral setting = null;

    //Сылка на окно список плагинов
    PluginServersController PluginList = null;

    //Сылка на окно добавить сервер
    AddServerController AddServer = null;

    //Основной класс работы с приложением
    Worker w = null;
    
    @FXML
    TabPane Tabs;

    @FXML //Показать окно включения отключения плагинов для серверов
    private void ActionPluginServers(ActionEvent event)
    {
        try
        {
            //Закроем если юзер потерял окно
            if (PluginList != null)
            {
                PluginList.CloseWindows();
            }
            //Контроллер назначен в XML так как  возвращает NULL
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PluginServers.fxml"), tr);
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);

            //fxmlLoader.getController() НАДО ВЫЗЫВАТЬ ПОСЛЕ  fxmlLoader.load() ИНАЧЕ КОНТРОЛЛЕР ЕЩЕ НЕ СОЗДАН
            PluginList = fxmlLoader.<PluginServersController>getController();
            PluginList.SetWindowsProperties(stage);
            PluginList.setSetting(setting);

            //Теперь можно показать
            stage.show();

        } catch (Exception e)
        {
            LogApp("Failed to create new Window.");
            //e.printStackTrace();
        }
    }

    @FXML //Показать диалог подключения к возможным серверам
    private void ActionAddServer(ActionEvent event)
    {
        try
        {
            //Закроем если юзер потерял окно
            if (AddServer != null)
            {
                AddServer.CloseWindows();
            }
            //Контроллер назначен в XML так как  возвращает NULL
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddServer.fxml"), tr);
            Scene scene = new Scene(fxmlLoader.load(), 600, 300);
            Stage stage = new Stage();
            stage.setScene(scene);

            //fxmlLoader.getController() НАДО ВЫЗЫВАТЬ ПОСЛЕ  fxmlLoader.load() ИНАЧЕ КОНТРОЛЛЕР ЕЩЕ НЕ СОЗДАН
            AddServer = fxmlLoader.<AddServerController>getController();
            AddServer.SetWindowsProperties(stage);

            //Теперь можно показать
            stage.show();

            AddServer.setWorker(w);

        } catch (Exception e)
        {
            LogApp("Failed to create new Window.");
            //e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        LogApp("MxRCON started...");
        tr = rb;

        //Настройки приложения
        setting = XmlWriter.Load(SettingGeneral.class, DefineValues.PATH_FILE_SETTING);

        if (setting == null)
        {
            setting = new SettingGeneral();
            XmlWriter.Write(setting, DefineValues.PATH_FILE_SETTING);
        }

        w = new Worker(setting, this);
    }

    @Override
    public TabPane getTabPane()
    {
        return Tabs;
    }

    @FXML
    private void ActionExit(ActionEvent event)
    {
        exit();
    }

    private void LogApp(String str)
    {
        System.out.println(str);
    }

}
