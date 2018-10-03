package mxrcon;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;
import mxrcon.Code.ISRV;
import mxrcon.Code.SettingGeneral;
import mxrcon.Code.PluginInfo;
import mxrcon.Code.Worker;
import mxrcon.Utilites.Inline;
import net.kronos.rkon.core.Rcon;

public class AddServerController implements Initializable
{

    //Перевод интерфейса
    private ResourceBundle tr;

    //Основные натройки приложения в которых определены плагины которые активны
    SettingGeneral setting = null;
    //Основной класс работы с приложением
    Worker w = null;

    @FXML
    private GridPane G_Pane; //Надо для получения доступа к окну которое потом будем закрывать
    @FXML
    private ComboBox provader_list = null;
    @FXML
    private TextField host_name = null;
    @FXML
    private PasswordField passwd = null;
    @FXML
    private CheckBox save_passwd = null;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tr = rb;

    }

    @FXML //Add server butoon OK
    private void ActionAddServer(ActionEvent event)
    {
        if (provader_list.getValue() == null)
        {
            new Alert(Alert.AlertType.ERROR, tr.getString("MSG_ERROR_SELECT_SRV")).showAndWait();
            return;
        }

        Pair<String, Integer> rezult_provider = Inline.spilit(provader_list.getValue().toString(), "_");

        if (rezult_provider == null)
        {
            new Alert(Alert.AlertType.ERROR, tr.getString("MSG_ERROR_SELECT_SRV")).showAndWait();
            return;
        }

        if (host_name.getText().trim().isEmpty())
        {
            new Alert(Alert.AlertType.ERROR, tr.getString("MSG_ERROR_SELECT_HOST")).showAndWait();
            return;
        }

        Pair<String, Integer> rezult_host = Inline.spilit(host_name.getText().trim(), ":");

        if (rezult_host == null)
        {
            new Alert(Alert.AlertType.ERROR, tr.getString("MSG_ERROR_SELECT_HOST_WRONG_FORMAT")).showAndWait();
            return;
        }

        if (passwd.getText().trim().isEmpty())
        {
            new Alert(Alert.AlertType.ERROR, tr.getString("MSG_ERROR_SELECT_PASSWD")).showAndWait();
            return;
        }

        try
        {
            Rcon rcon = new Rcon(rezult_host.getKey(), rezult_host.getValue(), passwd.getText().trim().getBytes());
            rcon.disconnect();
            //Сохраняем данные
            
            ISRV new_item = new ISRV();
            new_item.setHost(rezult_host.getKey());
            new_item.setPort(rezult_host.getValue());
            new_item.setPassword(passwd.getText().trim());
            new_item.setProvider(rezult_provider.getKey());
            new_item.setVersionProvider(rezult_provider.getValue());
            
            int stat_add = w.AddServerConfiguration(new_item);
            
            if(stat_add == 0)
            {
              new Alert(Alert.AlertType.ERROR, tr.getString("MSG_ERROR_ADD_SRV_DOUBLE")).showAndWait();
            }
            else if(stat_add == 1)
            {
                new Alert(Alert.AlertType.INFORMATION, tr.getString("MSG_INFO_ADD_SRV_OK")).showAndWait();
                CloseWindows();
            }

        } catch (Exception ex)
        {
            new Alert(Alert.AlertType.ERROR, tr.getString("MSG_ERROR_CONNECT_TO_SERVER") + ex.getMessage()).showAndWait();
        }

    }

    public void CloseWindows()
    {
        //Обязательно в XML назначить ид
        Window window = G_Pane.getScene().getWindow();

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
        stage.setMinHeight(300);
        stage.setMaxWidth(600);
        stage.setMaxHeight(300);

        stage.setFullScreen(false);//Запретить на весь экран
        //stage.setMaximized(true);//сделать размер таким же, как мы делаем размер, нажав на строку заголовка

    }

    public void setWorker(Worker value)
    {
        w = value;
        setting = value.setting;

        ArrayList<PluginInfo> all_mudules = setting.getEnabledProvidersGameServer();

        if (all_mudules != null)
        {
            ArrayList<String> all_items = new ArrayList<>();

            all_mudules.forEach((v) ->
            {
                all_items.add(v.getKey() + "_" + v.getValue());
            });

            provader_list.getItems().addAll(all_items.toArray(new String[all_items.size()]));
        }
    }

}
