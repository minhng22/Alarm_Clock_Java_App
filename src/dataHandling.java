import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class dataHandling {
	int timesinYA, timesInRA, timesIgnoreRA, timesIgnoreYA;
	int wokeUpEarly, wokeUpOnTime, wokeUpLate;
	double increaseFactor;
	String fileName= "sleepPattern.txt", trustedEmail= "";
	private ArrayList<String> fileInString= new ArrayList<String>();
	private ArrayList<TimeWakeUpAndSleep> timeList= new ArrayList<>();
	String sleepTime,wakeUpTime, date;


	public String getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(String sleepTime) {
		this.sleepTime = sleepTime;
	}

	public String getWakeUpTime() {
		return wakeUpTime;
	}

	public void setWakeUpTime(String wakeUpTime) {
		this.wakeUpTime = wakeUpTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public dataHandling() throws IOException{
		readFile();
	}

	//Creating the string for the report pop-up
	public String toString(){
		String str= "";

		str += "Yellow alert appeared "+timesinYA+" times"+"\n";
		str += "Yellow alert was ignored "+timesIgnoreYA+" times" +"\n";
		str += "Red alert appeared "+timesInRA+" times" +"\n";
		str += "Red alert was ignored "+timesIgnoreRA+" times" +"\n";
		str += "You woke up "+(getIncreaseFactor()*100)+" % of the time either early or due to the alert" +"\n";
		str += "Your trusted email: "+ trustedEmail+ "\n";
		str += ".." +"\n"+"Last 7 records:"+"\n";

		if(timeList.size() <= 7){
			for (int i= 0; i< timeList.size(); i++){
				str += ".." +"\n";
				str +="Date:"+timeList.get(i).getDate() +"\n";
				str +="Sleep time:"+timeList.get(i).getTimeSleep() +"\n";
				str +="Wake-up time:"+timeList.get(i).getTimeWakeUp() +"\n";
			}
		}
		else{
			for (int i= (timeList.size()-7); i< timeList.size(); i++){
				str += ".." +"\n";
				str +="Date:"+timeList.get(i).getDate() +"\n";
				str +="Sleep time:"+timeList.get(i).getTimeSleep() +"\n";
				str +="Wake-up time:"+timeList.get(i).getTimeWakeUp() +"\n";
			}
		}
		return str;
	}

	//Reading the file to get the values within it.
	private void readFile() throws IOException{
		try {
            File f = new File(fileName);
            Scanner sc;
            sc = new Scanner(f);

            while(sc.hasNext()) {
            	String line= sc.nextLine();
            	fileInString.add(line);
            }

            timesinYA= Integer.parseInt(fileInString.get(0).replaceAll("[^0-9]", ""));
            timesIgnoreYA= Integer.parseInt(fileInString.get(1).replaceAll("[^0-9]", ""));
            timesInRA= Integer.parseInt(fileInString.get(2).replaceAll("[^0-9]", ""));
            timesIgnoreRA= Integer.parseInt(fileInString.get(3).replaceAll("[^0-9]", ""));
            increaseFactor= Double.parseDouble(fileInString.get(4).replaceAll("[^0-9]", ""));
            trustedEmail= fileInString.get(5).substring(fileInString.get(5).indexOf(":")+1, fileInString.get(5).length()-1);
            increaseFactor /= 10;

            int i=0;

            while((9+ i*4) < fileInString.size()){
            	TimeWakeUpAndSleep newTimeList= new TimeWakeUpAndSleep();
            	newTimeList.setDate(fileInString.get(7+ i*4).substring(fileInString.get(7+ i*4).indexOf(":")+1, fileInString.get(7+ i*4).length()));
            	newTimeList.setTimeWakeUp(fileInString.get(8+ i*4).substring(fileInString.get(8+ i*4).indexOf(":")+1, fileInString.get(8+ i*4).length()));
            	newTimeList.setTimeSleep(fileInString.get(9+ i*4).substring(fileInString.get(9+ i*4).indexOf(":")+1, fileInString.get(9+ i*4).length()));

            	timeList.add(newTimeList);
            	i++;
            }
		} catch(IOException e) {
            System.out.println("No data in file.");
		}
	}

	public void saveData(){
		String fileContent=  "Yellow alert appeared "+timesinYA+" times"+"\n";
		String fileContent1= "Yellow alert was ignored "+timesIgnoreYA+" times" +"\n";
		String fileContent2= "Red alert appeared "+timesInRA+" times" +"\n";
		String fileContent3= "Red alert was ignored "+timesIgnoreRA+" times" +"\n";
		String fileContent4= "You woke up "+(getIncreaseFactor()*100)+" % of the time either early or due to the alert" +"\n";
		String fileContent5= "Your trusted email: "+ trustedEmail;
		FileWriter fileWriter;
		BufferedWriter writter;

		try {
			fileWriter = new FileWriter(fileName);
			writter = new BufferedWriter(fileWriter);

			writter.write(fileContent); writter.newLine();
			writter.write(fileContent1); writter.newLine();
			writter.write(fileContent2); writter.newLine();
			writter.write(fileContent3); writter.newLine();
			writter.write(fileContent4); writter.newLine();
			writter.write(fileContent5);writter.newLine();

			for (int i= 0; i< timeList.size(); i++){
				writter.write(".."); writter.newLine();
				writter.write("Date:"+timeList.get(i).getDate()); writter.newLine();
				writter.write("Wake-up time:"+timeList.get(i).getTimeWakeUp()); writter.newLine();
				writter.write("Sleep time:"+timeList.get(i).getTimeSleep()); writter.newLine();
			}
			writter.close();
        }
        catch(IOException ex) {
            System.out.println("Error writing to file");
            ex.printStackTrace();
        }
	}

	/*////////////////////////////
	//			Getters		  //
	*///////////////////////////
	public String getTrustedEmail() { return trustedEmail; }
	public int getTimesinYA() 		{ return timesinYA; }
	public int getTimesInRA() 		{ return timesInRA; }
	public int getTimesIgnoreRA() 	{ return timesIgnoreRA; }
	public int getTimesIgnoreYA() 	{ return timesIgnoreYA; }
	public int getWokeUpEarly() 	{ return wokeUpEarly; }
	public int getWokeUpOnTime() 	{ return wokeUpOnTime; }
	public int getTimesWakeUpLate() { return wokeUpLate; }

	public double getIncreaseFactor() {
		if ((wokeUpOnTime+ wokeUpEarly) != 0){
			increaseFactor= (wokeUpOnTime+ wokeUpEarly)/ wokeUpEarly;
		}
		else {
			increaseFactor= 0;
		}
		return increaseFactor;
	}

	/*////////////////////////////
	//			Setters		  //
	*///////////////////////////
	public void setTrustedEmail(String trustedEmail) {
		this.trustedEmail = trustedEmail;
		saveData();
	}

	public void setTimesinYA(int timesinYA) {
		this.timesinYA = timesinYA;
		saveData();
	}

	public void setTimesInRA(int timesInRA) {
		this.timesInRA = timesInRA;
		saveData();
	}

	public void setTimesIgnoreRA(int timesIgnoreRA) {
		this.timesIgnoreRA = timesIgnoreRA;
		saveData();
	}

	public void setTimesIgnoreYA(int timesIgnoreYA) {
		this.timesIgnoreYA = timesIgnoreYA;
		saveData();
	}

	public void setWokeUpEarly(int wokeUpEarly) {
		this.wokeUpEarly = wokeUpEarly;
		saveData();
	}

	public void setWokeUpOnTime(int WokeUpOnTime) {
		this.wokeUpOnTime = WokeUpOnTime;
		saveData();
	}

	public void setTimesWokeUpLate(int wokeUpLate) {
		this.wokeUpLate = wokeUpLate;
		saveData();
	}

	public void newTimeWakeUpAndSleepData(String wakeUpTime, String sleepTime,String date){
		timeList.add(new TimeWakeUpAndSleep(wakeUpTime, sleepTime, date));
		saveData();
	}
}
class TimeWakeUpAndSleep{
	String timeWakeUp, timeSleep, date;

	public TimeWakeUpAndSleep(String timeWakeUp, String timeSleep, String date){
		this.timeWakeUp = timeWakeUp;
		this.timeSleep = timeSleep;
		this.date = date;
	}
	public TimeWakeUpAndSleep(){}

	public String getDate() { return date; }
	public String getTimeWakeUp() {	return timeWakeUp; }
	public String getTimeSleep() { return timeSleep; }

	public void setDate(String date) {
		this.date = date;
	}

	public void setTimeWakeUp(String timeWakeUp) {
		this.timeWakeUp = timeWakeUp;
	}

	public void setTimeSleep(String timeSleep) {
		this.timeSleep = timeSleep;
	}
}
