package demo.captcha.rs.model;

public class Trigger {
	
	private int deltaPrice;
	public String priceTime;
	public String captchaTime;
	public String submitTime;
	
	public int getDeltaPrice() { return deltaPrice; }
	public void setDeltaPrice(int deltaPrice) { this.deltaPrice = deltaPrice; }
	
	public String getPriceTime() { return priceTime; }
	public void setPriceTime(String priceTime) { this.priceTime = priceTime; }
	
	public String getCaptchaTime() { return captchaTime; }
	public void setCaptchaTime(String captchaTime) { this.captchaTime = captchaTime; }
	
	public String getSubmitTime() { return submitTime; }
	public void setSubmitTime(String submitTime) { this.submitTime = submitTime; }
}
