package demo.captcha.service;

import java.util.List;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.Ranking;

public interface IRankService {
	void reset(List<Ranking> rankings);
	
	/**
	 * Top 验证码次数
	 * @param num
	 * @return
	 */
	List<Ranking> topCaptcha(int num);
	
	/**
	 * Top 验证码准确率
	 * @param num
	 * @return
	 */
	List<Ranking> topRate(int num);
	
	/**
	 * Top 总分
	 * @param num
	 * @return
	 */
	List<Ranking> topScore(int num);
	List<Ranking> getRankByOwner(CaptchaExamClient owner);
}
