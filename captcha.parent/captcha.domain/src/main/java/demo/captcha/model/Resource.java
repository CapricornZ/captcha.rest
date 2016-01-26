package demo.captcha.model;

import java.util.Date;

public class Resource {

	private int id;
	private String tag;
	private String env;
	private String content;
	private Date updateTime;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getTag() { return tag; }
	public void setTag(String tag) { this.tag = tag; }
	
	public String getEnv() { return env; }
	public void setEnv(String env) { this.env = env; }
	
	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	
	public Date getUpdateTime() { return updateTime;}
	public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}
