package demo.captcha.rs.model;

import java.util.List;

import demo.captcha.model.Operation;

public class GlobalConfig {

	private List<Operation> operations;
	private List<IOrcConfig> orcConfigs;
	private String url;
	private String tag;
	private Boolean dynamic;
	private List<Entry> entries;
	
	public List<Operation> getOperations() { return operations; }
	public void setOperations(List<Operation> operations) { this.operations = operations; }
	
	public void setDynamic(Boolean value){ this.dynamic = value; }
	public Boolean getDynamic(){return this.dynamic;}
	
	public void setTag(String val){ this.tag = val; }
	public String getTag(){ return this.tag; }
	
	public List<IOrcConfig> getOrcConfigs() { return orcConfigs; }
	public void setOrcConfigs(List<IOrcConfig> orcConfigs) { this.orcConfigs = orcConfigs; }
	
	/***
	 * resource.zip的URL
	 */
	public String getRepository() { return url; }
	public void setRepository(String url) { this.url = url; }
	
	/***
	 * 拍的网页地址
	 */
	public List<Entry> getEntries() { return entries; }
	public void setEntries(List<Entry> entries) { this.entries = entries; }
}
