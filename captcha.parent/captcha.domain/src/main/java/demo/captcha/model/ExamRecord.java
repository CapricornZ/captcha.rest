package demo.captcha.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import demo.captcha.util.CustomDateDeserializer;
import demo.captcha.util.CustomDateSerializer;

public class ExamRecord {
	
	private long id;
	private boolean correct;
	private int avgCost;
	private Date updateTime;
	private CaptchaExamClient client;
	
	public long getId() { return id; }
	public void setId(long id) { this.id = id; }
	
	public boolean getCorrect() { return correct; }
	public void setCorrect(boolean correct) { this.correct = correct; }
	
	public int getCost() { return avgCost; }
	public void setCost(int avgCost) { this.avgCost = avgCost; }

	//@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUpdateTime() { return updateTime; }
	public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
	
	public CaptchaExamClient getClient() { return client; }
	public void setClient(CaptchaExamClient client) { this.client = client; }
}
