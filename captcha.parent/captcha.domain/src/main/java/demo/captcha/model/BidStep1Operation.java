package demo.captcha.model;

public class BidStep1Operation extends Operation {

	public BidStep1Operation(){ this.setType("BIDStep1"); }
	
	private int price;
	public void setPrice(int price){ this.price = price; }
	public int getPrice(){ return this.price; }
	
	@Override
	public String getTips() {
		
		return String.format("%05d @%s", 
				this.price, 
				new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getStartTime()));
	}
	
	@Override
	public void update(Operation ops) {
		
		BidStep1Operation other = (BidStep1Operation)ops;
		//this.setStartTime(other.getStartTime());
		//this.setExpireTime(other.getExpireTime());
		this.setContent(other.getContent());
		this.setEnv(other.getEnv());
		this.setTag(other.getTag());
		//this.setPrice(other.getPrice());
	}
}
