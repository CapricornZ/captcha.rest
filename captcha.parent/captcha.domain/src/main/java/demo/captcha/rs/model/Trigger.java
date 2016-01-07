package demo.captcha.rs.model;

/***
 * 自定义策略配置
 * @author martin
 *
 */
public class Trigger {
	
	private int deltaPrice;
	public String priceTime;
	public String captchaTime;
	public String submitTime;
	public int submitReachPrice;
	
	public int getDeltaPrice() { return deltaPrice; }
	public void setDeltaPrice(int deltaPrice) { this.deltaPrice = deltaPrice; }
	
	public String getPriceTime() { return priceTime; }
	public void setPriceTime(String priceTime) { this.priceTime = priceTime; }
	
	public String getCaptchaTime() { return captchaTime; }
	public void setCaptchaTime(String captchaTime) { this.captchaTime = captchaTime; }
	
	public String getSubmitTime() { return submitTime; }
	public void setSubmitTime(String submitTime) { this.submitTime = submitTime; }
	
	public int isSubmitReachPrice() { return submitReachPrice; }
	public void setSubmitReachPrice(int submitReachPrice) { this.submitReachPrice = submitReachPrice; }
}
