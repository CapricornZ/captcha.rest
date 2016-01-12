package demo.captcha.rs.model;

/**
 * 自定义V3通用配置
 * @author martin
 *
 */
public class V3Common {
	
	public static class Submit{
		private String submitTime;
		private int percent;

		public String getSubmitTime() { return submitTime; }
		public void setSubmitTime(String submitTime) {this.submitTime = submitTime; }
		
		public int getPercent() { return percent; }
		public void setPercent(int percent) { this.percent = percent; }
	}
	
	public static class Trigger{
		
		private int delta;
		private Submit[] submits;
		
		public int getDelta() { return delta; }
		public void setDelta(int percent) { this.delta = percent; }
		
		public Submit[] getSubmits() { return submits; }
		public void setSubmits(Submit[] submits) { this.submits = submits; }
	}
	
	private String checkTime;
	private Trigger[] triggers;

	public String getCheckTime() { return checkTime; }
	public void setCheckTime(String checkTime) { this.checkTime = checkTime; }
	
	public Trigger[] getTriggers() { return triggers; }
	public void setTriggers(Trigger[] triggers) { this.triggers = triggers; }	
}
