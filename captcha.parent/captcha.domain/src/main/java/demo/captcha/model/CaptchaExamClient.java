package demo.captcha.model;

import java.util.Date;

public class CaptchaExamClient {
	
	private String host;//登录名
	private String code;//密码
	private String userName;//姓名
	private String mailAddress;//email
	private String phoneNo;//手机
	private int checkIns;//本期签到次数
	private int correct;
	private int total;
	private float rate;//成功率
	private Date expireTime;
	private Date updateTime;
	
	public String getHost() { return host; }
	public void setHost(String host) { this.host = host; }
	
	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }
	
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
	
	public String getMailAddress() { return mailAddress; }
	public void setMailAddress(String mailAddress) { this.mailAddress = mailAddress; }
	
	public String getPhoneNo() { return phoneNo; }
	public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
	
	public int getCheckIns() { return checkIns; }
	public void setCheckIns(int checkIns) { this.checkIns = checkIns; }
	
	public Date getExpireTime() { return expireTime; }
	public void setExpireTime(Date expireTime) { this.expireTime = expireTime; }
	
	public Date getUpdateTime() { return updateTime; }
	public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
	
	public int getCorrect() { return correct; }
	public void setCorrect(int correct) { this.correct = correct; }
	public void addCorrect(boolean correct){
		
		if(correct)
			this.correct += 1;
		if(this.total != 0)
			this.rate = (float)this.correct * 100 / (float)this.total;
	}
	
	public int getTotal() { return total; }
	public void setTotal(int total) { this.total = total; }
	
	public float getCorrectRate() { return rate; }
	public void setCorrectRate(float rate) { this.rate = rate; }
}
