package demo.captcha.service;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.Warrant;

public interface ICaptchaExamClientService {

	CaptchaExamClient queryByHost(String host);
	CaptchaExamClient register(String host, Warrant warrant);
	
	CaptchaExamClient update(CaptchaExamClient client);
}
