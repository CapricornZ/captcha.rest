package demo.captcha.service;

import java.util.List;

import demo.captcha.model.ScreenConfig;

public interface IScreenConfigService {

	ScreenConfig save(ScreenConfig screenConfig);
	ScreenConfig getByID(int id);
	List<ScreenConfig> listByCategory(String category);
	List<ScreenConfig> listAll();
}
