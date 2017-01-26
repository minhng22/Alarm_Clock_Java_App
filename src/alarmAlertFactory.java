
public class alarmAlertFactory {
	public AlarmAlert getAlarmAlert(String alertType){
		if(alertType == null){
	         return null;
	      }		
	      if(alertType.equalsIgnoreCase("wake up")){
	         return new WakeUpAlert();
	         
	      } else if(alertType.equalsIgnoreCase("sleep")){
	         return new sleepAlert();
	      }
	      
	      return null;
	}
}
