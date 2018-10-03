package MinecraftPE;

import MXCallback.ITaskCommand;
import MXModule.IServerProvider;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;

public class ServerProvider implements IServerProvider
{

    private ITaskCommand tc = null;
    Tab root_tab = null;

    @Override
    public String showSetting()
    {
        return "";
    }

    @Override
    public void Init(Tab tab, ITaskCommand cb_tc)
    {
        root_tab = tab;
        tc = cb_tc;
    }

    @Override
    public Task<Integer> WhileTask()
    {
        Task<Integer> task = new Task<Integer>()
        {
            @Override
            protected Integer call() throws Exception
            {
                int iterations;
                for (iterations = 0; iterations < 100000; iterations++)
                {
                    if (isCancelled())
                    {
                        break;
                    }
                    System.out.println("Iteration " + iterations);
                }
                return iterations;
            }
        };

        return task;
    }
}
