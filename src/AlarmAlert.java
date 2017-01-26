import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public interface AlarmAlert {
	public void setUpTime(GregorianCalendar time);
	public String checkAlertAction(GregorianCalendar time);
	public void changeFunctionStt(Boolean stt);
	public Boolean checkFunctionRunning();
	public void changeAlertStt(Boolean stt);
	public Boolean checkAlertEnable();
}

class WakeUpAlert implements AlarmAlert{
	GregorianCalendar setUpTime= new GregorianCalendar();
	boolean functionWorking= false;
	
	public WakeUpAlert(){}
	
	public void setUpTime(GregorianCalendar setUpTime){
		this.setUpTime= setUpTime;
		functionWorking= true;
	}
	
	public String checkAlertAction(GregorianCalendar time){
		if(functionWorking){
			DateFormat df = new SimpleDateFormat("HH:mm");
			String currentTime= df.format(time.getTime());
			currentTime= currentTime.replaceAll("\\D+","");
			
			String timeSU= df.format(setUpTime.getTime());
			timeSU= timeSU.replaceAll("\\D+","");
			
			int diff= Integer.parseInt(timeSU)- Integer.parseInt(currentTime);
			
			if(diff ==0){return "y";}
			else return "n";
		}
		return "";
	};
	
	public void changeFunctionStt(Boolean stt){functionWorking= stt;}
	
	public Boolean checkFunctionRunning(){return functionWorking;}
	
	public void changeAlertStt(Boolean stt){}
	public Boolean checkAlertEnable(){return false;}
}

class sleepAlert implements AlarmAlert{
	GregorianCalendar setUpTime= new GregorianCalendar();
	boolean functionWorking= false;
	boolean yellowAlert= false;
	
	public sleepAlert(){}
	
	public void setUpTime(GregorianCalendar setUpTime){
		this.setUpTime= setUpTime;
		functionWorking= true;
		yellowAlert= true;
	}
	
	public String checkAlertAction(GregorianCalendar time){
		if(functionWorking){
			DateFormat df = new SimpleDateFormat("HH:mm");
			String currentTime= df.format(time.getTime());
			currentTime= currentTime.replaceAll("\\D+","");
			
			String timeSU= df.format(setUpTime.getTime());
			timeSU= timeSU.replaceAll("\\D+","");
			
			int diff= Integer.parseInt(timeSU)- Integer.parseInt(currentTime);
			if( diff== 0){return "red";}
			else if (diff < 15){return "yellow";}
			else return "";
		}
		return "";
	};
	
	public void changeFunctionStt(Boolean stt){functionWorking= stt;}
	
	public Boolean checkFunctionRunning(){return functionWorking;}
	
	public void changeAlertStt(Boolean stt){
		yellowAlert= stt;
	}
	public Boolean checkAlertEnable(){
		return yellowAlert;
	}
}
