import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

public class ClockDriver {
    public static void main(String[] args) throws IOException {
    	AlarmClockController newAlarm= new AlarmClockController();
    	newAlarm.activate();
    }
}
