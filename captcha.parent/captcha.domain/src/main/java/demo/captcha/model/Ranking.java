package demo.captcha.model;

import java.util.Date;

public class Ranking {
	
	private int id;
	private int rank;
	private CaptchaExamClient owner;
	private Date updateTime;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public int getRank() { return rank; }
	public void setRank(int rank) { this.rank = rank; }
	
	public CaptchaExamClient getOwner() { return owner; }
	public void setOwner(CaptchaExamClient owner) { this.owner = owner; }
	
	public Date getUpdateTime() { return updateTime; }
	public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
	
	private String type;
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
}
