package demo.captcha.model;

public class Warrant {
	
	private int id;
	private String code;
	private int validate;
	private Status status;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }
	
	public int getValidate() { return validate; }
	public void setValidate(int validate) { this.validate = validate; }
	
	public Status getStatus() { return status; }
	public void setStatus(Status status) { this.status = status; }
	
}
