import com.qyk.Jupyter.utils.ConfigurationFactory;
import com.qyk.Jupyter.utils.RunRemoteCommand;
import org.junit.Test;

public class UtilsTest {
    @Test
    public void testRunRemoteCommand(){
        String ip = ConfigurationFactory.getInstance().getValue("remote_host_ip");
        String userName = ConfigurationFactory.getInstance().getValue("remote_username");
        String password = ConfigurationFactory.getInstance().getValue("remote_password");

//        String cmd = "LANG=zh_CN nohup jupyter notebook --port=11762 --NotebookApp.token=53dfd5g2id6d66d58g2ef388248f69e31ca634u52o7n863d --NotebookApp.notebook_dir=/etc/notebooks/admin --allow-root &";
        String cmd = "jupyter notebook list";
        System.out.println(RunRemoteCommand.execute(ip, userName, password, cmd, true));
//        String result = RunRemoteCommand.execute(ip, userName, password, cmd, true);

//        System.out.println(result);
    }
}
