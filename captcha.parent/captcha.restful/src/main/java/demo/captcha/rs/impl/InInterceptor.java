package demo.captcha.rs.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.captcha.security.CertificateCoder;
import demo.captcha.security.Licence;

public class InInterceptor extends AbstractPhaseInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(InInterceptor.class);
	
	private ObjectMapper objMapper = new ObjectMapper();
	private Licence licence;
	private byte[] certificate;
	
	public void setCertificatePath(String certPath) throws IOException{ 
		
		FileInputStream fis = new FileInputStream(new File(certPath));
		certificate = new byte[fis.available()];//新建一个字节数组
		fis.read(certificate);//将文件中的内容读取到字节数组中
		fis.close();
	}
	
	public void setLicencePath(String licencePath) throws Exception {
		
		FileInputStream fis = new FileInputStream(new File(licencePath));
		byte[] content=new byte[fis.available()];//新建一个字节数组
		fis.read(content);//将文件中的内容读取到字节数组中
		fis.close();
		this.licence = this.objMapper.readValue(content, Licence.class);
	}
	
	public InInterceptor(String phase) {
		super(phase);
	}
	
	public InInterceptor(){
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		
		logger.debug("handleMessage");
		byte[] period = null;
		try{
			period = this.objMapper.writeValueAsBytes(licence.getPeriod());
		}catch(Exception ex){
			logger.error("拒绝服务：Licence文件格式错误！");
			throw new Fault(new Exception("拒绝服务：Licence文件格式错误！"));
		}
		try {
			if(!CertificateCoder.verify(period, licence.getSignature(), this.certificate)){
				logger.error("拒绝服务：没有有效Licence！");
				throw new Fault(new Exception("拒绝服务：没有有效Licence！"));
			}
		} catch (Exception e) {
			throw new Fault(e);
		}
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(now.before(licence.getPeriod().getFrom()) || now.after(licence.getPeriod().getTo())){
			String error = String.format("拒绝服务：不在有效期，当前日期:%s. {From:%s - To:%s}", format.format(now), 
					format.format(licence.getPeriod().getFrom()), format.format(licence.getPeriod().getTo()));
			logger.error(error);
			throw new Fault(new Exception(error));
		}
		logger.debug("通过验证");
	}
}
