package demo.captcha.model;

import java.util.Date;

public class ExamRecord {
	
	private long id;
	private String level;
	private int total;
	private int correct;
	private float avgCost;
	private Date updateTime;
	private CaptchaExamClient client;
	
	public long getId() { return id; }
	public void setId(long id) { this.id = id; }
	
	public String getLevel() { return level; }
	public void setLevel(String level) { this.level = level; }
	
	public int getTotal() { return total; }
	public void setTotal(int total) { this.total = total; }
	
	public int getCorrect() { return correct; }
	public void setCorrect(int correct) { this.correct = correct; }
	
	public float getAvgCost() { return avgCost; }
	public void setAvgCost(float avgCost) { this.avgCost = avgCost; }
	
	public Date getUpdateTime() { return updateTime; }
	public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
	
	public CaptchaExamClient getClient() { return client; }
	public void setClient(CaptchaExamClient client) { this.client = client; }
}
