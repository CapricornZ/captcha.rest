package demo.captcha.service.impl;

import java.util.List;

import org.hibernate.Query;

import demo.captcha.model.Resource;
import demo.captcha.service.IResourceService;
import demo.captcha.service.Service;

public class ResourceService extends Service implements IResourceService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> listAll() {

		Query query = this.getSession().createQuery("from Resource");
		return query.list();
	}

	@Override
	public Resource queryByID(int id) {
		
		return (Resource) this.getSession().get(Resource.class, id);
	}

	@Override
	public void update(Resource resource) {
		
		this.getSession().update(resource);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> filterByTagAndEnv(String tag, String env) {
		
		Query query = this.getSession().createQuery("from Resource where tag=:tag and env=:env");
		query.setCacheable(true);
		query.setParameter("tag", tag);
		query.setParameter("env", env);
		return query.list();
	}
}
