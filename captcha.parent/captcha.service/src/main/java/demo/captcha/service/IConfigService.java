package demo.captcha.service;

import java.util.List;

import demo.captcha.model.Client;
import demo.captcha.model.Config;

public interface IConfigService {
	
	Config saveOrUpdate(Config config);
	Config saveOrUpdate(Config config, Client client);

	List<Config> listAll();
	void delete(String config);
	Config queryByNo(String no);
}
