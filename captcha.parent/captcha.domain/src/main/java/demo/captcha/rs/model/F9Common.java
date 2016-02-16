package demo.captcha.rs.model;

public class F9Common {

	public static class Action{
		
		//private String submit;
		private int delta;
		/*发生的比例*/
		private int percent;

		public int getDelta() { return delta; }
		public void setDelta(int delta) { this.delta = delta; }
		
		//public String getSubmit() { return submit; }
		//public void setSubmit(String submitTime) {this.submit = submitTime; }
		
		public int getPercent() { return percent; }
		public void setPercent(int percent) { this.percent = percent; }
	}
	
	public static class Trigger{
		
		private String fire;
		private Action[] actions;
		
		public String getFire() { return fire; }
		public void setFire(String fireTime) { this.fire = fireTime; }
		
		public Action[] getActions() { return actions; }
		public void setActions(Action[] actions) { this.actions = actions; }
	}
	
	private Trigger[] triggers;
	public Trigger[] getTriggers() { return triggers; }
	public void setTriggers(Trigger[] triggers) { this.triggers = triggers; }
	
}
