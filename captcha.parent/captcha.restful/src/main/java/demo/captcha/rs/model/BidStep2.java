package demo.captcha.rs.model;

import demo.captcha.rs.model.position.GivePrice2;
import demo.captcha.rs.model.position.Point;
import demo.captcha.rs.model.position.SubmitPrice;

/***
 * BID SCREEN CONFIG
 * @author martin
 *
 */
public class BidStep2 {

	private GivePrice2 give;
	private SubmitPrice submit;
	private Point title;
	private Point okButton;
	
	public GivePrice2 getGive() { return give; }
	public void setGive(GivePrice2 give) { this.give = give; }
	
	public SubmitPrice getSubmit() { return submit; }
	public void setSubmit(SubmitPrice submit) { this.submit = submit; }
	
	public Point getTitle() { return title; }
	public void setTitle(Point title) { this.title = title; }
	
	public Point getOkButton() { return okButton; }
	public void setOkButton(Point okButton) { this.okButton = okButton; }
}
