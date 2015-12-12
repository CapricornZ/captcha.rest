package demo.captcha.rs.impl;

import java.util.List;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.ExamRecord;
import demo.captcha.rs.IExam;
import demo.captcha.service.ICaptchaExamClientService;

public class ExamService implements IExam {

	private ICaptchaExamClientService clientService;
	public void setClientService(ICaptchaExamClientService service){ this.clientService = service; }
	
	@Override
	public List<ExamRecord> queryRecord(String host) {
		
		CaptchaExamClient client = this.clientService.queryByHost(host);
		return this.clientService.queryRecordByHost(client);
	}

	@Override
	public ExamRecord uploadRecord(String host, ExamRecord record) {
		
		CaptchaExamClient client = this.clientService.queryByHost(host);
		return this.clientService.updateRecord(client, record);
	}

	@Override
	public CaptchaExamClient createUser(CaptchaExamClient client) {
		
		return this.clientService.save(client);
	}

}
