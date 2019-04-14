import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskTest {
    @Test
    public void testTimerTask() throws InterruptedException {
        Timer timer = new Timer();
        TimerTask task = new JupyterTask("12333");

        timer.schedule(task, 5*1000);


//        Thread.sleep(6000);
    }
}
