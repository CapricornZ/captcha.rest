package demo.captcha.rs.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import demo.captcha.model.Client;
import demo.captcha.model.Operation;
import demo.captcha.util.CustomDateSerializer;

public class ClientHtml {

	private Client client;
	public ClientHtml(Client client){
		this.client = client;
	}
	
	@JsonIgnore
	public ConfigHtml getConfig(){
		if(null != this.client.getConfig())
			return new ConfigHtml(this.client.getConfig());
		else
			return null;
	}
	
	public String getIp() { return this.client.getIp(); }

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUpdateTime() { return this.client.getUpdateTime(); }
	
	public List<Operation> getOperation(){ return this.client.getOperation(); }

	public Date getExpireTime() { return this.client.getExpireTime(); }

	public String getMemo() { return this.client.getMemo(); }

	public String getTips() { return this.client.getTips(); }
	
	public boolean getIsOnline(){
		
		Date now = new Date();
		Date update = this.client.getUpdateTime();
		
		long diff = now.getTime() - update.getTime();
		long minutes = diff / (1000 * 60);
		return minutes <= 60;
	}
	
	public String getHover(){
		return String.format("%s", this.client.getMemo());
	}
}
