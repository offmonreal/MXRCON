package mxrcon;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import javafx.stage.Window;
import mxrcon.Code.DefineValues;
import mxrcon.Code.LibraryLoader;
import mxrcon.Code.SettingGeneral;
import mxrcon.Code.PluginInfo;
import mxrcon.Utilites.ListViewHandler;
import mxrcon.Utilites.XmlWriter;
import MXModule.IModuleProperties;
import MXModule.IServerProvider;
import java.util.ArrayList;

public class PluginServersController implements Initializable
{
    //Перевод интерфейса
    private ResourceBundle tr;
    //Основные натройки приложения в которых определены плагины которые активны
    SettingGeneral setting = null;

    private String selectLast = "-";
    
    IModuleProperties properties_plugin = null;
    IServerProvider server_provider = null;

    //Измениляли ли настройки
    private boolean setting_reload_message = false;

    @FXML
    private Label L_Name;
    @FXML
    private Label L_Developer;
    @FXML
    private Label L_Comment;
    @FXML
    private Label L_Category;
    @FXML
    private Label L_Version;
    @FXML
    private ToggleButton TB_EnablePlugin;
    @FXML
    private Button B_Setting;
    @FXML
    private Button B_Cancel; //Надо для получения доступа к окну которое потом будем закрывать
    @FXML
    private ListView<String> listPlugin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tr = rb;
        LibraryLoader ll = new LibraryLoader();
        String[] list_plugin = ll.getPluginListsByCategory("game_provider");
        for (String plugin_name : list_plugin)
        {
            listPlugin.getItems().add(plugin_name);
        }

        //Событие клика мышаком
        listPlugin.setOnMouseClicked(new ListViewHandler()
        {
            @Override
            public void handle(javafx.scene.input.MouseEvent event)
            {
                //System.out.print(listPlugin.getSelectionModel().getSelectedIndex());

                //Если обьект который выделен новый выделенный обьект
                if (listPlugin.getSelectionModel().getSelectedItem() != null && !selectLast.equals(listPlugin.getSelectionModel().getSelectedItem()))
                {
                    selectLast = listPlugin.getSelectionModel().getSelectedItem();

                    //Свойства последнего выделеного плагина
                    properties_plugin = ll.getPluginProperties(selectLast);

                    if (properties_plugin != null)
                    {
                        L_Name.setText(properties_plugin.getName());
                        L_Developer.setText(properties_plugin.getDeveloper());
                        L_Comment.setText(properties_plugin.getComment());
                        L_Category.setText(properties_plugin.getCategory());
                        L_Version.setText(Integer.toString(properties_plugin.getVersion()));
                        if (properties_plugin.enabledSetting())
                        {
                            B_Setting.setDisable(false);
                        } else
                        {
                            B_Setting.setDisable(true);
                        }
                        if (FindeEnabledPlugin(properties_plugin.getName(), Integer.toString(properties_plugin.getVersion()),listPlugin.getSelectionModel().getSelectedItems().get(0).toString()))
                        {
                            TB_EnablePlugin.setSelected(true);

                        } else
                        {
                            TB_EnablePlugin.setSelected(false);
                        }

                        handleEnablePlugin(null);

                    } else
                    {
                        L_Name.setText("-");
                        L_Developer.setText("-");
                        L_Comment.setText("-");
                        L_Category.setText("-");
                        L_Version.setText("-");
                        B_Setting.setDisable(true);
                        TB_EnablePlugin.setSelected(false);
                        TB_EnablePlugin.setText("-");

                    }

                }

            }
        });
    }
    
      @FXML
    private void handleButtonClose(ActionEvent event)
    {
        //System.out.println("You clicked CLOSE");
        CloseWindows();
    }

    @FXML
    private void handleButtonSave(ActionEvent event)
    {
        //System.out.println("You clicked SAVE");
        CloseWindows();
    }
    
    @FXML //Выполнить функцию показать настройки плагина из самого плагина
    private void handlePluginSetting(ActionEvent event)
    {
        if (server_provider != null)
        {
            server_provider.showSetting();
        }
    }

    @FXML //Выполнить функцию показать настройки плагина из самого плагина
    private void handleEnablePlugin(ActionEvent event)
    {
        if (properties_plugin != null)
        {
            if (TB_EnablePlugin.isSelected())
            {
                TB_EnablePlugin.setText(tr.getString("ON"));

                if (event != null)
                {
                    addPlugin(properties_plugin.getName(), Integer.toString(properties_plugin.getVersion()),listPlugin.getSelectionModel().getSelectedItems().get(0).toString());
                }

            } else
            {
                TB_EnablePlugin.setText(tr.getString("OFF"));
                if (event != null)
                {
                    removePlugin(properties_plugin.getName(), Integer.toString(properties_plugin.getVersion()),listPlugin.getSelectionModel().getSelectedItems().get(0).toString());
                }
            }

        } else
        {
            TB_EnablePlugin.setText("-");
        }
    }

    //Поиск ПЛАГИНА в настройках если ли он среди активных плагинов
    private boolean FindeEnabledPlugin(String PluginName, String PluginVersion, String FileName)
    {
        ArrayList<PluginInfo> all_mudules = setting.getEnabledProvidersGameServer();

        if (all_mudules != null)
        {
            for (PluginInfo v : all_mudules)
            {
                if (v.getKey().equals(PluginName) && v.getValue().equals(PluginVersion) && v.getFileName().equals(FileName))
                {
                    return true;
                }
            }

            return false;

        } else
        {
            return false;
        }
    }

    //Добавить плагин в глобальные настройки и сохранить
    private void addPlugin(String PluginName, String PluginVersion, String FileName)
    {
        ArrayList<PluginInfo> all_mudules = setting.getEnabledProvidersGameServer();

        if (all_mudules == null)
            all_mudules = new ArrayList<PluginInfo>();

        for (PluginInfo v : all_mudules)
        {
            if (v.getKey().equals(PluginName) && v.getValue().equals(PluginVersion)&& v.getValue().equals(FileName))
            {
                return;
            }
        }

        PluginInfo v = new PluginInfo();
        v.setKey(PluginName);
        v.setValue(PluginVersion);
        v.setFileName(FileName);
        all_mudules.add(v);
        setting.setEnabledProvidersGameServer(all_mudules);
        setting_reload_message = true;

    }

    private void removePlugin(String PluginName, String PluginVersion, String FileName)
    {
        ArrayList<PluginInfo> all_mudules = setting.getEnabledProvidersGameServer();

        if (all_mudules != null)
        {
            for (PluginInfo v : all_mudules)
            {
                if (v.getKey().equals(PluginName) && v.getValue().equals(PluginVersion)&& v.getFileName().equals(FileName))
                {
                    all_mudules.remove(v);
                    setting_reload_message = true;
                    return;
                }
            }
        }
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
