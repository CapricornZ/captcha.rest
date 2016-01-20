package demo.captcha.rs.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import demo.captcha.model.Config;
import demo.captcha.util.CustomDateSerializer;

public class ConfigHtml {

	private Config config;
	public ConfigHtml(Config config){
		this.config = config;
	}
	
	//public ClientHtml getClient(){
		
	//	if( null != this.config.getClient() )
	//		return new ClientHtml(this.config.getClient());
	//	else
	//		return null;
	//}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUpdateTime() { return this.config.getUpdateTime(); }
	
	public String getNo() { return this.config.getNo(); }
	
	public String getPasswd() { return this.config.getPasswd(); }
	
	public String getPname() { return this.config.getPname(); }
	
	public String getPid() { return this.config.getPid(); }
	
	public String getTags() { return this.config.getTags(); }
	
	public String getPolicy() { return this.config.getPolicy(); }
	
}
