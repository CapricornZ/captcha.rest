package demo.captcha.model;

import java.util.Date;

public class CaptchaExamClient {
	
	private String host;
	private String code;
	private Date expireTime;
	private Date updateTime;
	
	public String getHost() { return host; }
	public void setHost(String host) { this.host = host; }
	
	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }
	
	public Date getExpireTime() { return expireTime; }
	public void setExpireTime(Date expireTime) { this.expireTime = expireTime; }
	
	public Date getUpdateTime() { return updateTime; }
	public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }	
}
