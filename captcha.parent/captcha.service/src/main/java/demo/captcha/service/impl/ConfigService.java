package demo.captcha.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.rs.model.AssignmentV1;
import demo.captcha.rs.model.AssignmentV2;
import demo.captcha.rs.model.AssignmentV3;
import demo.captcha.rs.model.TriggerV2;
import demo.captcha.rs.model.TriggerV3;
import demo.captcha.rs.model.V3Common;
import demo.captcha.service.IConfigService;
import demo.captcha.service.Service;

public class ConfigService extends Service implements IConfigService {
    
	private String storePath;
	public void setStorePath(String storePath){ this.storePath = storePath; }
	
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
			//oClient = pConfig.getClient();
			
			pConfig.setPasswd(config.getPasswd());
			pConfig.setPid(config.getPid());
			pConfig.setPname(config.getPname());
			pConfig.setTags(config.getTags());
			pConfig.setPolicy(config.getPolicy());
			pConfig.setUpdateTime(new Date());
			
			this.getSession().saveOrUpdate(pConfig);
			
			//if( null != oClient && !oClient.equals(client)){
				
				//oClient.setConfig(null);
				//oClient.setTips(null);
				//oClient.setMemo(null);
				//this.getSession().update(oClient);
			//}
		} else {

			//oClient = config.getClient();		
			//if( null != oClient && !oClient.equals(client)){
				
				//oClient.setConfig(null);
				//oClient.setTips(null);
				//oClient.setMemo(null);
				//this.getSession().update(oClient);
			//}
			
			config.setUpdateTime(new Date());
			//config.setClient(client);
			this.getSession().saveOrUpdate(config);
			
			client.setConfig(config);
			//this.getSession().update(client);
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
		//if(null != cfg.getClient()){
			
			//cfg.getClient().setConfig(null);
			//cfg.getClient().setTips(null);
			//cfg.getClient().setMemo(null);
			//this.getSession().update(cfg.getClient());
		//}
		this.getSession().delete(cfg);
	}

	@Override
	public Config queryByNo(String no) {
		
		return (Config) this.getSession().get(Config.class, no);
	}

	@Override
	public Config removeClient(Config config) {
		
		Config pConfig = (Config) this.getSession().get(Config.class, config.getNo());
		//Client oClient = pConfig.getClient();
		
		//pConfig.setClient(null);
		pConfig.setPasswd(config.getPasswd());
		pConfig.setPid(config.getPid());
		pConfig.setPname(config.getPname());
		pConfig.setTags(config.getTags());
		pConfig.setUpdateTime(new Date());
		
		this.getSession().saveOrUpdate(pConfig);
		
		//if( null != oClient ){
			
			//oClient.setConfig(null);
			//oClient.setTips(null);
			//oClient.setMemo(null);
			//this.getSession().update(oClient);
		//}
		return pConfig;
	}

	@Override
	public void assignmentV2(List<AssignmentV2> assignments){
		
		com.google.gson.Gson gson = new com.google.gson.Gson();
		for(AssignmentV2 assign : assignments){
			
			TriggerV2 tV2 = assign.getTrigger();
			String trigger = gson.toJson(assign.getTrigger());
			this.assignment(assign.getConfig(), trigger);
		}
	}
	
	@Override
	public void assignmentV3(List<AssignmentV3> assignments){
		
		V3Common v3Comm = this.getCommonV3();
		com.google.gson.Gson gson = new com.google.gson.Gson();
		for(AssignmentV3 assign : assignments){
			
			TriggerV3 tV3 = assign.getTrigger();
			tV3.setCommon(v3Comm);
			String trigger = gson.toJson(assign.getTrigger());
			this.assignment(assign.getConfig(), trigger);
		}
	}
	
	@Override
	public void assignmentV1(List<AssignmentV1> assignments){
		
		com.google.gson.Gson gson = new com.google.gson.Gson();
		for(AssignmentV1 assign : assignments){
			
			String trigger = gson.toJson(assign.getTrigger());
			this.assignment(assign.getConfig(), trigger);
		}
	}
	
	
	private void assignment(Config config, String trigger) {
		
		Session session = this.getSession();
		//Client objClient = (Client)session.get(Client.class, client);
		//if( null != objClient ){
			//config.setClient(objClient);
			config.setUpdateTime(new Date());
			config.setPolicy(trigger);
			
			//objClient.setTips(trigger);
			//objClient.setConfig(config);
			
			session.save(config);
			//session.update(objClient);
		//}
	}

	
	@Override
	public V3Common getCommonV3(){
		
		String path = this.storePath;
		String v3Common = "{\"checkTime\":\"11:29:48\"}";
		java.io.FileInputStream fis;
		try {
			fis = new java.io.FileInputStream(path + "v3.common");
			v3Common = org.apache.commons.io.IOUtils.toString(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		V3Common v3 = new com.google.gson.Gson().fromJson(v3Common, V3Common.class);
		return v3;
	}

	@Override
	public void setCommonV3(V3Common v3) throws IOException {
		
		String path = this.storePath;
		java.io.FileOutputStream fos = new java.io.FileOutputStream(path + "v3.common");
		org.apache.commons.io.IOUtils.write(new com.google.gson.Gson().toJson(v3), fos);		
	}
}
