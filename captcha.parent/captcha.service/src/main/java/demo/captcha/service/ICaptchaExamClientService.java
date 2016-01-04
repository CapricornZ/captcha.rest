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
	void save(List<CaptchaExamClient> client);
	
	CaptchaExamClient update(CaptchaExamClient client);
	
	long countRecordByHost(CaptchaExamClient client);
	List<ExamRecord> queryRecordByHost(CaptchaExamClient client);
	List<ExamRecord> queryRecordByHostDate(CaptchaExamClient client, String yyyyMM);
	Page<ExamRecord> queryRecordByHostWithPage(CaptchaExamClient client, int pageNum);
	ExamRecord updateRecord(CaptchaExamClient client, ExamRecord record);
	List groupByDate(CaptchaExamClient client);
	
	//Page<CaptchaExamClient> rankingByQuality();
	//Page<CaptchaExamClient> rankingByRate();
	List<CaptchaExamClient> rankingQuality();
	List<CaptchaExamClient> rankingRate();
	List<CaptchaExamClient> rankingScore();
}
