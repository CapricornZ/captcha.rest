package demo.captcha.rs.impl;

import java.util.List;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.ExamRecord;
import demo.captcha.rs.IExam;
import demo.captcha.service.ICaptchaExamClientService;
import demo.captcha.service.Page;

public class ExamService implements IExam {

	private ICaptchaExamClientService clientService;
	public void setClientService(ICaptchaExamClientService service){ this.clientService = service; }
	
	@Override
	public List<ExamRecord> queryRecord(String host) {
		
		CaptchaExamClient client = this.clientService.queryByHost(host);
		return this.clientService.queryRecordByHost(client);
	}
	
	@Override
	public List<ExamRecord> queryRecord(String host, String yyyyMM) {

		CaptchaExamClient client = this.clientService.queryByHost(host);
		return this.clientService.queryRecordByHostDate(client, yyyyMM);
	}

	@Override
	public Page<ExamRecord> queryRecordByPage(String host, int pageNumber) {
		
		CaptchaExamClient client = this.clientService.queryByHost(host);
		return this.clientService.queryRecordByHostWithPage(client, pageNumber);
	}
	
	@Override
	public ExamRecord uploadRecord(String host, ExamRecord record) {
		
		CaptchaExamClient client = this.clientService.queryByHost(host);
		return this.clientService.updateRecord(client, record);
	}

	@Override
	public CaptchaExamClient createUser(CaptchaExamClient client) {
		
		CaptchaExamClient dbClient = this.clientService.save(client);
		ConnectionConfiguration config = new ConnectionConfiguration("139.196.24.58",5222);
		Connection connection = new XMPPConnection(config);
        try {
			connection.connect();
			AccountManager amgr = connection.getAccountManager();
	        amgr.createAccount(dbClient.getHost(), dbClient.getCode());
		} catch (XMPPException e) {
			e.printStackTrace();
		}
        return dbClient;
	}
}
