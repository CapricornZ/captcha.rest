package demo.captcha.rs.model;

import demo.captcha.model.Config;

public class Assignment {
	
	private String host;
	private Trigger trigger;
	private Config config;
	
	public String getHost() { return host; }
	public void setHost(String host) { this.host = host; }
	
	public Trigger getTrigger() { return trigger; }
	public void setTrigger(Trigger trigger) { this.trigger = trigger; }
	
	public Config getConfig() { return config; }
	public void setConfig(Config config) { this.config = config; }
}
