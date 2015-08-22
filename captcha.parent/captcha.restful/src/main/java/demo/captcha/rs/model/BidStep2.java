package demo.captcha.rs.model;

import demo.captcha.rs.model.position.GivePrice2;
import demo.captcha.rs.model.position.SubmitPrice;

/***
 * BID SCREEN CONFIG
 * @author martin
 *
 */
public class BidStep2 {

	private GivePrice2 give;
	private SubmitPrice submit;
	
	public GivePrice2 getGive() { return give; }
	public void setGive(GivePrice2 give) { this.give = give; }
	
	public SubmitPrice getSubmit() { return submit; }
	public void setSubmit(SubmitPrice submit) { this.submit = submit; }
}
