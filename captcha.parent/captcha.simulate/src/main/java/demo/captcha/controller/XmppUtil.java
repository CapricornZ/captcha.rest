package demo.captcha.controller;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class XmppUtil {
	
	private String user;
	private String pass;
	public void setUser(String user){ this.user = user; }
	public void setPassword(String password){ this.pass = password; }
	
	public MultiUserChat jointMultiUserChat(){
		
		String[] array = this.user.split("@");
		MultiUserChat rtn = null;
		ConnectionConfiguration config = new ConnectionConfiguration(array[1],5222);
		Connection connection = new XMPPConnection(config);
		try{
			
			connection.connect();
			connection.login(array[0], this.pass);
			
			rtn = new MultiUserChat(connection, "bid@conference." + array[1]);
			rtn.join("ADMINISTRATOR");
			rtn.sendMessage("HI every body, Admin is online now");
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return rtn;
	}
}
