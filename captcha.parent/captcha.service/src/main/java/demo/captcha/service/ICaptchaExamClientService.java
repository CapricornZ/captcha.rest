package demo.captcha.service;

import java.util.List;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.ExamRecord;
import demo.captcha.model.Warrant;

public interface ICaptchaExamClientService {

	List<CaptchaExamClient> listAll();
	CaptchaExamClient queryByHost(String host);
	CaptchaExamClient register(String host, Warrant warrant);
	CaptchaExamClient save(CaptchaExamClient client);
	
	CaptchaExamClient update(CaptchaExamClient client);
	
	List<ExamRecord> queryRecordByHost(CaptchaExamClient client);
	Page<ExamRecord> queryRecordByHostWithPage(CaptchaExamClient client, int pageNum);
	ExamRecord updateRecord(CaptchaExamClient client, ExamRecord record);
}
