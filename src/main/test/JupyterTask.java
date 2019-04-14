import com.qyk.Jupyter.service.JupyterService;
import com.qyk.Jupyter.service.impl.JupyterServiceImpl;

import java.util.TimerTask;

public class JupyterTask extends TimerTask {
    private String port;

    private JupyterService jupyterService;

    public JupyterTask() {
    }

    public JupyterTask(String port) {
        this.port = port;
        this.jupyterService = new JupyterServiceImpl();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public JupyterService getJupyterService() {
        return jupyterService;
    }

    public void setJupyterService(JupyterService jupyterService) {
        this.jupyterService = jupyterService;
    }

    @Override
    public void run() {
        jupyterService.stopJupyterServer(this.port);
    }
}
