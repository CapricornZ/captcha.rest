package demo.captcha.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.model.Operation;
import demo.captcha.model.Warrant;
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
			for (Iterator<Client> iter = operation.getClients().iterator(); !bFound && iter.hasNext();) {
				
				Client client = iter.next();
				if(client.getIp().equals(host)){
					operation.getClients().remove(client);
					bFound = true;
				}
			}
			this.getSession().update(operation);
		}
	}
	
	@Override
	public void removeConfig(String host) {
		
		Client client = (Client) this.getSession().get(Client.class, host);
		if(null != client){
			
			Config config = client.getConfig();
			if(null != config){
				config.setClient(null);
				this.getSession().update(config);
				
				client.setConfig(null);
				this.getSession().update(client);
			}
		}
	}

	@Override
	public void modifyConfig(String host, Config config) {
		
		String hql = "from Client where ip=:ip";
    	Query query = this.getSession().createQuery(hql);
    	query.setParameter("ip", host);
    	
    	@SuppressWarnings("unchecked")
		List<Client> clients = query.list();
    	if(clients.size()>0){
    		
    		Client tmpClient = clients.get(0);
    		//tmpClient.setUpdateTime(new Date());
    		
    		if(null == tmpClient.getConfig() || !tmpClient.getConfig().equals(config)){
    			
    			config.setClient(tmpClient);
    			config.setUpdateTime(new Date());
    			this.getSession().merge(config);
    			
    			if(tmpClient.getConfig() != null){
    				tmpClient.getConfig().setClient(null);
    				this.getSession().update(tmpClient.getConfig());
    			}
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

	@Override
	public Client register(String host, Warrant warrant) {
		
		String hql = "from Warrant where code=:code";
		Query query = this.getSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Warrant> warrants = query.setParameter("code", warrant.getCode()).list();
		if(null != warrants && warrants.size() > 0){
			if(!warrants.get(0).getCode().equals(warrant.getCode()))
				return null;
		} else 
			return null;
		
		Warrant pWarrant = warrants.get(0);
		//Warrant pWarrant = (Warrant) this.getSession().get(Warrant.class, warrant.getId());
		//if(warrant.getCode() == null || !pWarrant.getCode().equals(warrant.getCode()))
		//	return null;

		Client pClient = (Client) this.getSession().get(Client.class, host);
		if( null != pWarrant ){
			
			if( null == pClient ){
				
				Client client = new Client();
				client.setCode(pWarrant.getCode());
				client.setIp(host);
				Date now = new Date();
				//client.setUpdateTime(now);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(now);
				calendar.add(Calendar.MONTH, pWarrant.getValidate());
				client.setExpireTime(calendar.getTime());
	
				Operation ops1 = (Operation) this.getSession().get(Operation.class, 317);
				if( null != ops1)
					ops1.getClients().add(client);
				Operation ops2 = (Operation) this.getSession().get(Operation.class, 318);
				if(null != ops2)
					ops2.getClients().add(client);
				
				this.getSession().delete(pWarrant);
				this.getSession().save(client);
				this.getSession().merge(ops1);
				this.getSession().merge(ops2);
				return client;
			} else {
				
				pClient.setCode(pWarrant.getCode());
				pClient.setUpdateTime(new Date());
				Calendar calendar = new GregorianCalendar();
				if( null != pClient.getExpireTime())
					calendar.setTime(pClient.getExpireTime());
				else
					calendar.setTime(new Date());
				calendar.add(Calendar.MONTH, pWarrant.getValidate());
				pClient.setExpireTime(calendar.getTime());
				
				this.getSession().delete(pWarrant);
				this.getSession().update(pClient);
				return pClient;
			}
		} else
			return null;
	}

	@Override
	public void modifyMemo(String[] hosts, String memo) {
		
		List<Client> clients = this.listByIPs(hosts);
		for(Client client : clients){
			client.setMemo(memo);
			this.getSession().update(client);
		}
	}

}
