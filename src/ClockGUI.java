import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class ClockGUI extends JPanel {
    
    /// Instance variables
	String [] 	 timeStamp = new String[]{"AM", "PM"};
    JLabel	 	 wakeUpText = new JLabel("Enter Wake-Up Alarm", SwingConstants.CENTER),
    			 sleepText  = new JLabel("Enter Sleep Alarm", SwingConstants.CENTER);
    JFileChooser fileChooser = new JFileChooser();
    
    JTextField	 wakeUp, sleep, clock;
    
    JSplitPane 	 splitPaneWakeUp, 
    			 splitPaneSleep;
    
    JButton	     sleepButton = new JButton("Sleep"), 
    			 wakeButton  = new JButton("Awake"),
    			 reportButton = new JButton("Report");
    
    JComboBox sleepAmPm, wakeAmPm;
    
    /// constructor
	
    ClockGUI() {
        JPanel wakeUpTime = new JPanel(new FlowLayout(5));
        JPanel sleepTime = new JPanel(new FlowLayout(5));
        JPanel reportPanel = new JPanel();
        
        wakeUpText.setMaximumSize(new Dimension(2,4));
        sleepText.setMaximumSize(new Dimension(2,4));
        
        wakeUpTime.setBorder(new EmptyBorder(2, 3, 2, 3));
        sleepTime.setBorder(new EmptyBorder(2, 3, 2, 3));
        
        //Setting up the Wake-up Pane
        wakeAmPm = new JComboBox(timeStamp);
        wakeAmPm.setSelectedIndex(0);
        
        wakeUp = new JTextField("08:00 ");
        Font wakeFont = wakeUp.getFont().deriveFont(Font.PLAIN, 24f);
        wakeUp.setFont(wakeFont);
        wakeUpTime.add(wakeUp);
        wakeUpTime.add(wakeAmPm);
        
        //Setting up the Sleep Pane
        sleepAmPm = new JComboBox(timeStamp);
        sleepAmPm.setSelectedIndex(1);
        
        sleep = new JTextField("08:00 ", 3);
        Font sleepFont = sleep.getFont().deriveFont(Font.PLAIN, 24f);
        sleep.setFont(sleepFont);
        sleepTime.add(sleep);
        sleepTime.add(sleepAmPm);
        
        //Creating the split panes for the wake-up and sleep panes
        splitPaneWakeUp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, wakeUpText, wakeUpTime);
        splitPaneSleep 	= new JSplitPane(JSplitPane.VERTICAL_SPLIT, sleepText, sleepTime);
        
        splitPaneWakeUp.setDividerSize(2);
        splitPaneSleep.setDividerSize(2);
        
        splitPaneWakeUp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneWakeUp, wakeButton);
        splitPaneSleep = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneSleep, sleepButton);
        
        splitPaneWakeUp.setDividerSize(2);
        splitPaneSleep.setDividerSize(2);
        
        //setting up the report button to be centered
        reportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        reportPanel.add(reportButton, BorderLayout.EAST);
        
        //combining the two split panes to form one
        splitPaneWakeUp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPaneWakeUp, splitPaneSleep);
        splitPaneWakeUp.setResizeWeight(.5d);
        splitPaneWakeUp.setOneTouchExpandable(false);
        splitPaneWakeUp.setDividerSize(2);
        
        //Setting the clock text field
        clock = new JTextField();
        Font clockFont = clock.getFont().deriveFont(Font.PLAIN, 128f);
        clock.setFont(clockFont);
        clock.setEditable(false);
        clock.setBackground(null);      
        
        //adding components to the window
        this.setLayout(new BorderLayout());
        this.add(reportPanel, BorderLayout.NORTH);
        this.add(clock, BorderLayout.CENTER);        
        this.add(splitPaneWakeUp, BorderLayout.SOUTH);
        
    }
    
    public String getWakeUpAlarm() {
    	return wakeUp.getText();
    }
    
    public String getSleepAlarm() {
    	return sleep.getText();
    }
    
    public int getWakeAmPm() {
    	return wakeAmPm.getSelectedIndex();
    }
    
    public int getSleepAmPm() {
    	return sleepAmPm.getSelectedIndex();
    }
}

