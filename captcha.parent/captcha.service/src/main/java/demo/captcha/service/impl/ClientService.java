package demo.captcha.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.model.Operation;
import demo.captcha.service.IClientService;
import demo.captcha.service.Service;

public class ClientService extends Service implements IClientService {
    
    public Client saveOrUpdate(Client client){
    	
    	String hql = "from Client where ip=:ip";
    	Query query = this.getSession().createQuery(hql);
    	query.setParameter("ip", client.getIp());
    	
    	@SuppressWarnings("unchecked")
		List<Client> list = query.list();
    	if(list.size()>0){
    		
    		Client tmpClient = list.get(0);
    		tmpClient.setUpdateTime(new Date());
    		this.getSession().update(tmpClient);
    		return tmpClient;
    	} else {
    		client.setUpdateTime(new Date());
    		this.getSession().saveOrUpdate(client);
    		return client;
    	}
    }
    
    public void delete(Client client){
    	
    	this.getSession().delete(client);
    }
    
    public void update(Client record){
    	
    	Client oldClient = (Client) this.getSession().get(Client.class, record.getIp());
    	if(null != oldClient){
    		oldClient.setUpdateTime(new Date());
    		this.getSession().update(oldClient);
    	}
    }
    
    public Client queryByIP(String ip){
    	
    	String hql = "from Client where ip=:ip";
    	Query query = this.getSession().createQuery(hql);
    	query.setParameter("ip", ip);
    	
    	@SuppressWarnings("unchecked")
    	List<Client> list = query.list();
    	return list.size()>0 ? list.get(0) : null;
    }

	@Override
	public List<Client> list() {
		
		String hql = "from Client order by updateTime desc";
		Session _session = this.getSession();
		_session.disableFilter("tagFilter");
    	Query query = this.getSession().createQuery(hql);
    	
    	@SuppressWarnings("unchecked")
    	List<Client> list = query.list();
		return list;
	}
	
	@Override
	public List<Client> listByFilter(String tag){
		
		String hql = "from Client";
		Session _session = this.getSession();
		_session.enableFilter("tagFilter").setParameter("tagParam", tag);
    	Query query = _session.createQuery(hql);
    	
    	@SuppressWarnings("unchecked")
    	List<Client> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> listByIPs(String[] hosts) {

		String hql = "from Client where ip in (:ips)";
    	Query query = this.getSession().createQuery(hql);
    	query.setParameterList("ips", hosts);

    	return query.list();
	}

	@Override
	public void removeOperation(String host, int operationID) {
		
		Query query = this.getSession().createQuery("from Operation where id=:id");
		@SuppressWarnings("unchecked")
		List<Operation> ops = query.setParameter("id", operationID).list();
		if(ops.size() > 0){
			
			Operation operation = ops.get(0);
			boolean bFound = false;
			for (Iterator iter = operation.getClients().iterator(); !bFound && iter.hasNext();) {
				
				Client client = (Client)iter.next();
				if(client.getIp().equals(host)){
					operation.getClients().remove(client);
					bFound = true;
				}
			}
			this.getSession().update(operation);
		}
	}

	@Override
	public void modifyConfig(String host, Config config) {
		
		String hql = "from Client where ip=:ip";
    	Query query = this.getSession().createQuery(hql);
    	query.setParameter("ip", host);
    	
    	@SuppressWarnings("unchecked")
		List<Client> list = query.list();
    	if(list.size()>0){
    		
    		Client tmpClient = list.get(0);
    		tmpClient.setUpdateTime(new Date());
    		
    		if(null == tmpClient.getConfig() || !tmpClient.getConfig().equals(config)){
    			
    			config.setUpdateTime(new Date());
    			this.getSession().merge(config);
    			tmpClient.setConfig(config);
    			this.getSession().update(tmpClient);
    		}
    	}
	}

	@Override
	public List<Client> listFilterByHost(String host) {
		
		String hql = "from Client where ip like :host order by updateTime desc";
		Session _session = this.getSession();
		_session.disableFilter("tagFilter");
    	Query query = this.getSession().createQuery(hql);
    	query.setParameter("host", "%"+host+"%");
    	
    	@SuppressWarnings("unchecked")
    	List<Client> list = query.list();
		return list;
	}

}
