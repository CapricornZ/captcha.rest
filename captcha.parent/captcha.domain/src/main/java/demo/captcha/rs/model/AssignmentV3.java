package demo.captcha.rs.model;

import demo.captcha.model.Config;

public class AssignmentV3 {

	private String host;
	private TriggerV3 trigger;
	private Config config;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public TriggerV3 getTrigger() {
		return trigger;
	}
	public void setTrigger(TriggerV3 trigger) {
		this.trigger = trigger;
	}
	public Config getConfig() {
		return config;
	}
	public void setConfig(Config config) {
		this.config = config;
	}
}
