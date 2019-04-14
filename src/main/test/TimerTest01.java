import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest01 {
//    Timer timer;
//    public TimerTest01(int time){
//        timer = new Timer();
//        timer.schedule(new TimerTaskTest01(), time * 1000);
//    }

    public static void main(String[] args) {
        System.out.println(new Date() + ": timer begin....");

        Timer timer = new Timer();
        TimerTask task = new JupyterTask("12333");
        timer.schedule(task, 3*1000);
//        task.cancel();

    }
}

