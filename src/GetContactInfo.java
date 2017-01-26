import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class GetContactInfo {
	JTextArea message;
	JTextArea email;
	public String createPopUp() {
		message = new JTextArea("Enter an email of someone who cares about you: ");
		Font font = new Font("Verdana", Font.BOLD, 12);
		message.setFont(font);
		message.setBackground(null);
		message.setEditable(false);
		email= new JTextArea();
		
		return null;		
	}

}
