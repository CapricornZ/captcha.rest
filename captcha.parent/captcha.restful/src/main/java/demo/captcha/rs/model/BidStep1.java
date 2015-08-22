package demo.captcha.rs.model;

import demo.captcha.rs.model.position.GivePrice1;
import demo.captcha.rs.model.position.SubmitPrice;

public class BidStep1 {

	private GivePrice1 give;
	private SubmitPrice submit;
	
	public GivePrice1 getGive() { return give; }
	public void setGive(GivePrice1 give) { this.give = give; }
	
	public SubmitPrice getSubmit() { return submit; }
	public void setSubmit(SubmitPrice submit) { this.submit = submit; }
}
