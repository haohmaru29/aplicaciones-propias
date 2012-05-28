package cl.acgp.commons.library.mail;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cl.acgp.commons.helper.PropertiesUtils;

public class Mail {

	private MimeMessage message;
	private Transport transport;
	
	public Mail() {
		Properties properties = PropertiesUtils.loadProperties("mail.properties");
		Session mailSession = Session.getDefaultInstance(properties, null);
		mailSession.setDebug(true);
		
		try {
			transport = mailSession.getTransport();
			message = new MimeMessage(mailSession);
	        
		} catch (NoSuchProviderException ex) {
			Logger.getLogger( this.getClass() ).error( "NoSuchProviderException [Mail]: " + ex.getMessage() );
		} 
	}
	
	public void setContent(String contentMessage, String multipart){
		try{
			message.setContent( contentMessage, multipart);
		} catch(MessagingException ex){
			Logger.getLogger( this.getClass() ).error( "MessagingException [setContent]: " + ex.getMessage() );
		}
	}
	
	public void setHTMLContent(InputStream inputStream){
		try{
			message.setContent( new Scanner( inputStream ).useDelimiter("</html>").next(), "text/html");
		} catch(MessagingException ex){
			Logger.getLogger( this.getClass() ).error( "MessagingException [setHTMLContent]: " + ex.getMessage() );
		}
	}
	
	public void setHTMLContent(HttpServletRequest request, String templatePath){
		try {
			StringBuffer uri = new StringBuffer();
			uri.append( "http://" );
			uri.append( request.getServerName() );
			uri.append( (":").concat( String.valueOf(request.getServerPort()) ) );
			uri.append( request.getContextPath().concat("/") );
			
			message.setContent( new Scanner( 
					new URL(uri.append( templatePath ).toString()).openStream() )
						.useDelimiter("</html>").next(), "text/html");
		} catch (MalformedURLException ex) {
			Logger.getLogger( this.getClass() ).error( "MalformedURLException [Mail]: " + ex.getMessage() );
		} catch (IOException ex) {
			Logger.getLogger( this.getClass() ).error( "IOException [Mail]: " + ex.getMessage() );
		} catch (MessagingException ex) {
			Logger.getLogger( this.getClass() ).error( "MessagingException [Mail]: " + ex.getMessage() );
		}
	}
	
	public void setSubject(String subject){
		try{
			message.setSubject( subject );
		} catch(MessagingException ex){
			Logger.getLogger( this.getClass() ).error( "MessagingException [setSubject]: " + ex.getMessage() );
		}
		
	}
	
	public void from(String from){
		
		try{
			message.setFrom(new InternetAddress(from));
		} catch(AddressException ex) {
			Logger.getLogger( this.getClass() ).error( "AddressExceptionn [from]: " + ex.getMessage() ); 
		} catch(MessagingException ex) {
			Logger.getLogger( this.getClass() ).error( "MessagingException [from]: " + ex.getMessage() );
		}
		
	}
	
	public void to(String to){
		
		try{
			message.addRecipient(Message.RecipientType.TO, new InternetAddress( to ));
		} catch(AddressException ex) {
			Logger.getLogger( this.getClass() ).error( "AddressException [to]: " + ex.getMessage() ); 
		} catch(MessagingException ex) {
			Logger.getLogger( this.getClass() ).error( "MessagingException [to]: " + ex.getMessage() );
		}
		
	}
	
	public void send() throws MessagingException{
		transport.connect();
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
	}
	
	public String getContent(){
		String msg = null;
		
		try{
			msg = (String) message.getContent();
		} catch(IOException ex) {
			Logger.getLogger( this.getClass() ).error( "IOException [getContent]: " + ex.getMessage() ); 
		} catch(MessagingException ex) {
			Logger.getLogger( this.getClass() ).error( "MessagingException [getContent]: " + ex.getMessage() );
		}
		
		return msg;
		
	}

}
