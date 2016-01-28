package demo.captcha.service;

import java.util.List;

import demo.captcha.model.Resource;

public interface IResourceService {
	
	List<Resource> listAll();
	Resource queryByID(int id);
	void update(Resource resource);
	
	List<Resource> filterByTagAndEnv(String tag, String env);
}
