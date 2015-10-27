package demo.captcha.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import demo.captcha.util.CustomDateDeserializer;
import demo.captcha.util.CustomDateSerializer;

public class Config {

	private String no;
	private String passwd;
	private String pid;
	private String pname;
	private Date updateTime;
	@JsonIgnore
	private Client client;
	
	public String getRenderClient(){
		if( null != client )
			return client.getIp();
		else
			return "";
	}
	
	public Client getClient() { return client; }
	public void setClient(Client client) { this.client = client; }
	
	public String getPname() { return pname; }
	public void setPname(String pname) { this.pname = pname; }
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUpdateTime() { return updateTime; }
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
	
	public String getNo() { return no; }
	public void setNo(String no) { this.no = no; }
	public String getPasswd() { return passwd; }
	public void setPasswd(String passwd) { this.passwd = passwd; }
	public String getPid() { return pid; }
	public void setPid(String pid) { this.pid = pid; }
	
	@Override
	public boolean equals(Object obj) {

		Config other = (Config)obj;
		boolean equal = false;
		equal = equal && (other.no != null && other.no.equals(this.no));		
		return equal;
	}
}
