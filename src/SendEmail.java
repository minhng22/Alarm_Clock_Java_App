import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail
{
      String emailReceiver;
      String emailSender= "alarmSystem@gmail.com";
      String host= "myHost";// assume you already have a host server
      Properties properties;
      //Session session;
      
      public SendEmail(){
    	  /*properties= System.getProperties();
    	  properties.setProperty("mail.smtp.host", host);
    	  session= Session.getDefaultInstance(properties);*/
      }
      
      public void sendAlertEmail(dataHandling newDataHandling){
    	  emailReceiver= newDataHandling.getTrustedEmail();
    	  /*try{
    	         // Create a default MimeMessage object.
    	         MimeMessage message = new MimeMessage(session);

    	         // Set From: header field of the header.
    	         message.setFrom(new InternetAddress(emailSender));

    	         // Set To: header field of the header.
    	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceiver));

    	         // Set Subject: header field
    	         message.setSubject("Sleep Warning!");

    	         // Now set the actual message
    	         message.setText("Someone isn't sleeping well");

    	         // Send message
    	         Transport.send(message);
    	         System.out.println("Sent message successfully....");
    	      }catch (MessagingException mex) {
    	         mex.printStackTrace();
    	      }*/
      }
}
