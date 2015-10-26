package demo.captcha.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import demo.captcha.util.CustomDateDeserializer;
import demo.captcha.util.CustomDateSerializer;

public class Client {

	private String ip;
	private String code;
	private Date expireTime;
	private Date updateTime;
	private Config config;
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Client){
			Client other = (Client)obj;
			return other.ip.equals(this.ip);
		}
		return false;
		
	}
	
	public Client(){
	}
	
	public Client(String ip){
		this.ip = ip;
	}
	
	public Config getConfig() { return config; }
	public void setConfig(Config config) { this.config = config; }
	
	public String getIp() { return ip; }
	public void setIp(String ip) { this.ip = ip; }

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUpdateTime() { return updateTime; }
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
	
	private List<Operation> operation;
	public void setOperation(List<Operation> ops){
		this.operation = ops;
	}
	public List<Operation> getOperation(){
		return this.operation;
	}

	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }

	public Date getExpireTime() { return expireTime; }
	public void setExpireTime(Date expireTime) { this.expireTime = expireTime; }
}
