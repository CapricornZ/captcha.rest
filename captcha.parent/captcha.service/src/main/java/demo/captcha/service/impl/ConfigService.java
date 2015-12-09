package demo.captcha.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.service.IConfigService;
import demo.captcha.service.Service;

public class ConfigService extends Service implements IConfigService {
    
	@Override
	public Config saveOrUpdate(Config config) {
		
		String hql = "from Config where no=:no";
    	Query query = this.getSession().createQuery(hql);
    	query.setParameter("no", config.getNo());
    	
    	@SuppressWarnings("unchecked")
		List<Config> list = query.list();
    	if(list.size()>0){
    		
    		Config tmpConfig = list.get(0);
    		tmpConfig.setUpdateTime(new Date());
    		this.getSession().update(tmpConfig);
    		return tmpConfig;
    	} else {
    		config.setUpdateTime(new Date());
    		this.getSession().saveOrUpdate(config);
    		return config;
    	}
	}

	@Override
	public Config saveOrUpdate(Config config, Client client) {

		Client oClient = null;
		if(null == client){
			
			Config pConfig = (Config) this.getSession().get(Config.class, config.getNo());
			oClient = pConfig.getClient();
			
			pConfig.setPasswd(config.getPasswd());
			pConfig.setPid(config.getPid());
			pConfig.setPname(config.getPname());
			pConfig.setTags(config.getTags());
			pConfig.setUpdateTime(new Date());
			
			this.getSession().saveOrUpdate(pConfig);
			
			if( null != oClient && !oClient.equals(client)){
				
				oClient.setConfig(null);
				oClient.setTips(null);
				oClient.setMemo(null);
				this.getSession().update(oClient);
			}
		} else {

			oClient = config.getClient();		
			if( null != oClient && !oClient.equals(client)){
				
				oClient.setConfig(null);
				oClient.setTips(null);
				oClient.setMemo(null);
				this.getSession().update(oClient);
			}
			
			config.setUpdateTime(new Date());
			config.setClient(client);
			this.getSession().saveOrUpdate(config);
			
			client.setConfig(config);
			this.getSession().update(client);
			
			
		}
				
		return config;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Config> listAll() {
		
		return (List<Config>)this.getSession().createQuery("from Config order by updateTime desc").list();
	}

	@Override
	public void delete(String config) {
		
		Config cfg = (Config) this.getSession().get(Config.class, config);
		if(null != cfg.getClient()){
			
			cfg.getClient().setConfig(null);
			cfg.getClient().setTips(null);
			cfg.getClient().setMemo(null);
			this.getSession().update(cfg.getClient());
		}
		this.getSession().delete(cfg);
	}

	@Override
	public Config queryByNo(String no) {
		
		return (Config) this.getSession().get(Config.class, no);
	}
}
