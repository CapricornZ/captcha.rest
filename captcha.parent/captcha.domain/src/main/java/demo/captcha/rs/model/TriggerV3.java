package demo.captcha.rs.model;

public class TriggerV3 implements ITrigger{

	private String category;
	private int deltaPrice;
	private String priceTime;
	private String submitTime;
	private V3Common common;
	
	@Override
	public String getCategory() { return this.category; }
	@Override
	public void setCategory(String value) { this.category = value; }
	
	public int getDeltaPrice() { return deltaPrice; }
	public void setDeltaPrice(int deltaPrice) { this.deltaPrice = deltaPrice; }
	
	public String getPriceTime() { return priceTime; }
	public void setPriceTime(String priceTime) { this.priceTime = priceTime; }
	
	public String getSubmitTime() { return submitTime; }
	public void setSubmitTime(String submitTime) { this.submitTime = submitTime; }
	
	public V3Common getCommon() { return common; }
	public void setCommon(V3Common common) { this.common = common; }

}
