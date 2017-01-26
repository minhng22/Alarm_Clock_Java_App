import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.event.DocumentListener;

public class AlarmClockController {
	public GregorianCalendar currentTime, date;
	Date wakeUpTime, sleepTime;
	public AlarmAlert wakeUpFunction, sleepAlertFunction;
	public alarmAlertFactory alarmAlertFact;
	public ClockGUI newClockGUI;
	public Timer timer;
	public dataHandling newDataHandling;
	public SendEmail newSendEmail;
	public boolean sleepDataSaved= false, wakeUpDataSaved= false;
	
	public AlarmClockController() throws IOException{
		currentTime= new GregorianCalendar();
		date= new GregorianCalendar();
		
		newDataHandling= new dataHandling();
		alarmAlertFact= new alarmAlertFactory();
		wakeUpFunction= alarmAlertFact.getAlarmAlert("wake up");
		sleepAlertFunction= alarmAlertFact.getAlarmAlert("sleep");
		newSendEmail= new SendEmail();
	}
	
	public void activate(){
		checkUserTrustedEmail();
		checkNumberOfRedAlert();
		setUpGUI();
		updateTime();
	}
	
	//Sees if there are more than 3 red alerts if so sends email
	private void checkNumberOfRedAlert(){
		if(newDataHandling.getTimesInRA() >= 3){newSendEmail.sendAlertEmail(newDataHandling);}
	}
	
	private void checkUserTrustedEmail(){
		String title= "Trusted Email Address";
		String[] options = new String[3];
		options[0] = new String("Continue");
		options[1] = new String("Cancel");
		options[2] = new String("Skip");
		
		GetContactInfo newGet= new GetContactInfo();
		newGet.createPopUp();
		int option= JOptionPane.showOptionDialog(new JFrame(), new JSplitPane(JSplitPane.VERTICAL_SPLIT, newGet.message, newGet.email),
				title, 0, 0, null, options, null);
		if(option ==0){
			newDataHandling.setTrustedEmail(newGet.email.getText());
		}
		else if(option ==1){
			newDataHandling.setTrustedEmail(newGet.email.getText());
			System.exit(0);
		}
	}
	
	//Initializes the GUI components
	private void setUpGUI(){
		newClockGUI= new ClockGUI();
		
		JFrame frame  = new JFrame("Alarm Clock");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
        frame.pack();
        frame.setContentPane(newClockGUI);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        newClockGUI.wakeUp.addActionListener(new WakeUpAlarmSetAction());
        newClockGUI.sleep.addActionListener(new SleepAlarmSetAction());
        newClockGUI.wakeButton.addActionListener(new WakeUpPopUpAction());
        newClockGUI.sleepButton.addActionListener(new SleepPopUpAction());
        newClockGUI.reportButton.addActionListener(new ReportButtonPressed());
	}
	
	private void updateTime(){
		timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	currentTime.add(GregorianCalendar.MINUTE, 1);
            	updateClock();
                updateAlertState();
                saveSleepandWakeUpTime();
            }
        }, 0, 5000);
	}
	
	private void saveSleepandWakeUpTime(){
		if(sleepDataSaved){
			if(wakeUpDataSaved){
				SimpleDateFormat wakeUpFormat = new SimpleDateFormat("HH:mm");
				SimpleDateFormat sleepFormat = new SimpleDateFormat("HH:mm");
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				newDataHandling.setWakeUpTime(wakeUpFormat.format(wakeUpTime));
				newDataHandling.setSleepTime(sleepFormat.format(sleepTime));
				newDataHandling.setDate(df.format(date.getTime()));
				newDataHandling.newTimeWakeUpAndSleepData(newDataHandling.getWakeUpTime(), newDataHandling.getSleepTime(), newDataHandling.getDate());
				
				sleepDataSaved= false;
				wakeUpDataSaved= false;
			}
		}
	}
	
	private void updateClock(){
		DateFormat df = new SimpleDateFormat("HH:mm");
		String time= df.format(currentTime.getTime());
		
		newClockGUI.clock.setText(time);
	}
	
	public void updateAlertState(){
		checkWakeUpFunction();
		checkSleepFunction();
	}
	
	private void checkWakeUpFunction(){
		if (wakeUpFunction.checkFunctionRunning()){
			String wakeUpAlert= wakeUpFunction.checkAlertAction(currentTime);
			if (wakeUpAlert.equals("y")){
				saveWakeUpData();
				
				WakeUpAlarm wakeUpAlarm= new WakeUpAlarm();
				wakeUpAlarm.createPopUp();
	        	newClockGUI.wakeButton.setEnabled(false);
	        	newClockGUI.sleepButton.setEnabled(true);
	        	wakeUpFunction.changeFunctionStt(false);
	        	sleepAlertFunction.changeFunctionStt(true);
				
				int wakeUpWithintAlarm= JOptionPane.showConfirmDialog(new JFrame(), wakeUpAlarm.message, "Wake Up Alert", 0);
				
				if(wakeUpWithintAlarm == 0){
					newDataHandling.setWokeUpOnTime(newDataHandling.getWokeUpOnTime() +1);
				}
				else if(wakeUpWithintAlarm == 1){
					newDataHandling.setWokeUpEarly(newDataHandling.getWokeUpEarly() +1);
				}
			}
		}
	}
	
	private void checkSleepFunction(){
		if(sleepAlertFunction.checkFunctionRunning()){
			String sleepAlert= sleepAlertFunction.checkAlertAction(currentTime);
			
			if (sleepAlert.equals("red") && sleepAlertFunction.checkFunctionRunning()){
				RedAlert redAlert= new RedAlert();
				redAlert.createPopUp();
				newDataHandling.setTimesInRA(newDataHandling.getTimesInRA()+1);
				
				if(JOptionPane.showConfirmDialog(new JFrame(), redAlert.message, "Red Alert", 0) == 0){
					saveSleepData();
					sleepAlertFunction.changeFunctionStt(false);
					newClockGUI.sleepButton.setEnabled(false);
				}
				else {
					newDataHandling.setTimesIgnoreRA(newDataHandling.getTimesIgnoreRA()+1);
				}
			}
			
			if(sleepAlert.equals("yellow") && (sleepAlertFunction.checkAlertEnable()) ){
				YellowAlert yellowAlert= new YellowAlert();
				yellowAlert.createPopUp();
				newDataHandling.setTimesinYA(newDataHandling.getTimesIgnoreYA()+ 1);
				
				if(JOptionPane.showConfirmDialog(new JFrame(), yellowAlert.message, "Yellow Alert", 0) == 0){
					sleepAlertFunction.changeAlertStt(false);
					newDataHandling.setTimesIgnoreYA(newDataHandling.getTimesIgnoreYA()+1);
				}
				else {
					saveSleepData();
		        	newClockGUI.sleepButton.setEnabled(false);
		        	newClockGUI.wakeButton.setEnabled(true);
		        	sleepAlertFunction.changeFunctionStt(false);
		        	wakeUpFunction.changeFunctionStt(true);
				}
			}
		}
	}
	
	private void saveSleepData(){
		sleepDataSaved= true;
		date= currentTime;
		sleepTime= currentTime.getTime();
		saveSleepandWakeUpTime();
	}
	
	private void saveWakeUpData(){
		wakeUpDataSaved= true;
		date= currentTime;
		wakeUpTime= currentTime.getTime();
		saveSleepandWakeUpTime();
	}
	
	/*////////////////////////////////
	 * Setting up the Action Listeners
	 *////////////////////////////////
	
	class SleepAlarmSetAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
        	String sleep= newClockGUI.getSleepAlarm();
        	int sleepHour= Integer.parseInt(sleep.substring(0, sleep.indexOf(":")));
        	int sleepMinute= Integer.parseInt(sleep.substring(sleep.indexOf(":")+1, sleep.length()- 1));
        	int amPm= newClockGUI.getSleepAmPm();
        	
        	if(amPm == 1){if(sleepHour !=12){sleepHour += 12;}}
        	if(amPm == 0){if(sleepHour ==12){sleepHour -= 12;}}
        	GregorianCalendar timeSetUp= new GregorianCalendar();
        	System.out.println(String.valueOf(sleepHour)+": ");
        	System.out.println(String.valueOf(sleepMinute));
        	timeSetUp.set(0, 0, 0, sleepHour, sleepMinute);
        	
        	newClockGUI.sleepButton.setEnabled(true);
        	
        	sleepAlertFunction.changeFunctionStt(true);
        	sleepAlertFunction.setUpTime(timeSetUp);
        }
    }
	
	class WakeUpAlarmSetAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
        	String wakeUp= newClockGUI.getWakeUpAlarm();
        	int wakeUpHour= Integer.parseInt(wakeUp.substring(0, wakeUp.indexOf(":")));
        	int wakeUpMinute= Integer.parseInt(wakeUp.substring(wakeUp.indexOf(":")+1, wakeUp.length()- 1));
        	int amPm= newClockGUI.getSleepAmPm();
        	
        	if(amPm == 0){if(wakeUpHour !=12){System.out.println("Here I am"); wakeUpHour += 12;}}
        	if(amPm == 1){if(wakeUpHour ==12){wakeUpHour -= 12;}}
        	GregorianCalendar timeSetUp= new GregorianCalendar();
        	System.out.println(String.valueOf(wakeUpHour)+": ");
        	System.out.println(String.valueOf(wakeUpMinute));
        	timeSetUp.set(0, 0, 0, wakeUpHour, wakeUpMinute);
        	
        	
        	newClockGUI.wakeButton.setEnabled(true);
        	wakeUpFunction.changeFunctionStt(true);
        	wakeUpFunction.setUpTime(timeSetUp);
        }
    }
	
	class WakeUpPopUpAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
        	new WakeUpPopUp().createPopUp();
        	
        	newDataHandling.setWokeUpEarly(newDataHandling.getWokeUpEarly()+1);
        	saveWakeUpData();
        	newClockGUI.wakeButton.setEnabled(false);
        	newClockGUI.sleepButton.setEnabled(true);
        	wakeUpFunction.changeFunctionStt(false);
        	sleepAlertFunction.changeFunctionStt(true);
        }
    }
    
    class SleepPopUpAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
        	new SleepPopUp().createPopUp();
        	
        	saveSleepData();
        	newClockGUI.sleepButton.setEnabled(false);
        	newClockGUI.wakeButton.setEnabled(true);
        	sleepAlertFunction.changeFunctionStt(false);
        	wakeUpFunction.changeFunctionStt(true);
        }
    }
    
	class ReportButtonPressed implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
        	ReportPopUp newReport= new ReportPopUp();
        	newReport.createPopUp();
        	
        	newReport.message.setText(newDataHandling.toString());
        	JOptionPane.showMessageDialog(new JFrame(), newReport.message);
        }
    }

}
