package demo.captcha.model;

public class LoginOperation extends Operation{

	private String url;
	public LoginOperation(){ this.setType("LOGIN"); }
	
	public void setUrl(String url){ this.url = url; }
	public String getUrl() { return this.url; }
	
	@Override
	public String getTips() {
		
		return String.format("@%s", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getStartTime()));
	}

	@Override
	public void update(Operation ops) {
		
		LoginOperation other = (LoginOperation)ops;
		this.setStartTime(other.getStartTime());
		this.setExpireTime(other.getExpireTime());
	}
}
