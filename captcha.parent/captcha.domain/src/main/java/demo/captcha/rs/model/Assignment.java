package demo.captcha.rs.model;

import demo.captcha.model.Config;

public class Assignment {
	
	private String host;
	private TriggerV2 trigger;
	private Config config;
	
	public String getHost() { return host; }
	public void setHost(String host) { this.host = host; }
	
	public TriggerV2 getTrigger() { return trigger; }
	public void setTrigger(TriggerV2 trigger) { this.trigger = trigger; }
	
	public Config getConfig() { return config; }
	public void setConfig(Config config) { this.config = config; }
}
