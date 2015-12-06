package demo.captcha.rs.model;

public class Captcha {
	
	public Captcha(){
	}
	
	public Captcha(String value, String url, String tip){
		this.value = value;
		this.url = url;
		this.tip = tip;
	}

	private String value;
	private String tip;
	private String url;
	
	public String getTip() { return tip; }
	public void setTip(String tip) { this.tip = tip; }
	
	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; }
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
}
