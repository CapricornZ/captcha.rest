package demo.captcha.service;

import java.util.List;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.Ranking;

public interface IRankService {
	void reset(List<Ranking> rankings);
	
	List<Ranking> topCaptcha(int num);
	List<Ranking> topRate(int num);
	List<Ranking> getRankByOwner(CaptchaExamClient owner);
}
