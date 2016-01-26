package demo.captcha.rs.model;

import org.codehaus.jackson.annotate.JsonIgnore;

public class OrcTipConfig implements IOrcConfig{

	private OrcConfig configTip;
	private OrcConfig configNo;
	
	public OrcConfig getConfigTip() { return configTip; }
	public void setConfigTip(OrcConfig config0) { this.configTip = config0; }

	public OrcConfig getConfigNo() { return configNo; }
	public void setConfigNo(OrcConfig config1) { this.configNo = config1; }

	@Override
	@JsonIgnore
	public String getCategory() {
		return "OrcTipConfig";
	}
}