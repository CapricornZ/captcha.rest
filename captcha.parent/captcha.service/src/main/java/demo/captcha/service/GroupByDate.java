package demo.captcha.service;

public class GroupByDate {

	private long count;
	private String date;
	
	public GroupByDate(long count, String date){
		this.count = count;
		this.date = date;
	}
	
	public long getCount() { return count; }
	public void setCount(long count) { this.count = count; }
	
	public String getDate() { return date; }
	public void setDate(String date) { this.date = date; }
}
