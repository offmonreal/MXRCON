package MXModule;

import MXCallback.ITaskCommand;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;

/**
 *
 * @author maxim
 */
public interface IServerProvider
{

    public void Init(Tab tab, ITaskCommand cb_tc);
    public String showSetting();
    public Task<Integer> WhileTask(); 
}
