import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public interface PopUpWindowInterface {
	public void createPopUp();

}
/*///////////////////////////////
//	Creating all the Pop-ups  //
*///////////////////////////////

class RedAlert implements PopUpWindowInterface{
	public JTextArea message;
	@Override
	public void createPopUp() {
		message = new JTextArea("             Red Alert\n\nAre you going to sleep now?");
		Font font = new Font("Verdana", Font.BOLD, 24);
		message.setFont(font);
		message.setForeground(Color.RED);
		message.setBackground(null);
		message.setEditable(false);
	}
}

class SleepPopUp implements PopUpWindowInterface{

	@Override
	public void createPopUp() {
		JTextArea message = new JTextArea("Sleep Well!");
		Font font = new Font("Verdana", Font.PLAIN, 24);
		message.setFont(font);
		message.setBackground(null);
		message.setEditable(false);
		JOptionPane.showMessageDialog(new JFrame(), message);
	}
}

class WakeUpAlarm implements PopUpWindowInterface{
	private int wakingUp = 0;
	JTextArea message;
	@Override
	public void createPopUp() {
		Color custOrange = new Color (249, 50, 0);
		message = new JTextArea("           Wake Up!\n\nDid the alarm wake you up?");
		Font font = new Font("Verdana", Font.BOLD, 24);
		message.setFont(font);
		message.setForeground(custOrange);
		message.setBackground(null);
		message.setEditable(false);
	}
	public int getWakingUp(){ return wakingUp;}
}

class WakeUpPopUp implements PopUpWindowInterface{
	JTextArea message;
	@Override
	public void createPopUp() {
		Color custOrange = new Color (249, 50, 0);
		message = new JTextArea("Have an excellent day!");
		Font font = new Font("Verdana", Font.BOLD, 24);
		message.setFont(font);
		message.setBackground(null);
		message.setForeground(custOrange);
		message.setEditable(false);
		JOptionPane.showMessageDialog(new JFrame(), message);
	}
}

class YellowAlert implements PopUpWindowInterface{
	JTextArea message;
	@Override
	public void createPopUp() {
		message = new JTextArea("           Yellow Alert\n15 minutes till sleep alarm\nare you going to stay awake?");
		Font font = new Font("Verdana", Font.BOLD, 24);
		message.setFont(font);
		message.setForeground(Color.YELLOW.darker());
		message.setBackground(null);
		message.setEditable(false);
	}
}

class ReportPopUp implements PopUpWindowInterface{
	JTextArea message;

	@Override
	public void createPopUp() {
		message = new JTextArea();
		Font font = new Font("Verdana", Font.PLAIN, 12);
		message.setFont(font);
		message.setBackground(null);
		message.setEditable(false);
	}
}
