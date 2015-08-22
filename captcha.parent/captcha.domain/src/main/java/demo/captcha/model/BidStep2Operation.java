package demo.captcha.model;

public class BidStep2Operation extends Operation {

	public BidStep2Operation(){ this.setType("BIDStep2"); }
	
	private int price;
	public void setPrice(int price){ this.price = price; }
	public int getPrice(){ return this.price; }
	
	@Override
	public String getTips() {
		
		return String.format("+%04d @%s", 
				this.price, 
				new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getStartTime()));
	}
	
	@Override
	public void update(Operation ops) {
		
		BidStep2Operation other = (BidStep2Operation)ops;
		this.setStartTime(other.getStartTime());
		this.setExpireTime(other.getExpireTime());
		this.setContent(other.getContent());
		this.setPrice(other.getPrice());
	}
}
