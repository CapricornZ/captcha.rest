package demo.captcha.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import demo.captcha.model.Client;
import demo.captcha.model.Operation;
import demo.captcha.service.IOperationService;
import demo.captcha.service.Service;

public class OperationService extends Service implements IOperationService {

	@Override
	public Operation saveOrUpdate(Operation operation) {
		
		String hql = "from Operation where id=:id";
		Query query = this.getSession().createQuery(hql);
    	query.setParameter("id", operation.getId());
    	@SuppressWarnings("unchecked")
		List<Operation> list = query.list();
    	operation.setUpdateTime(new Date());
    	if(list.size()>0){
    		
    		Operation dbOperation = list.get(0);
    		dbOperation.update(operation);
    		dbOperation.setUpdateTime(new Date());
    		this.getSession().merge(dbOperation);
    		return operation;
    	} else {
    		
    		this.getSession().saveOrUpdate(operation);
    		return operation;
    	}
	}

	@Override
	public Operation saveOrUpdate(Operation operation, Client client) {
		
		operation = this.saveOrUpdate(operation);
		//client.setUpdateTime(new Date());
		this.getSession().update(client);
		return operation;
	}

	@Override
	public void updateClientOperation(Operation operation, List<Client> clients) {
		
		operation = this.saveOrUpdate(operation);
		for(Client client : clients){
			client.getOperation().clear();
			client.getOperation().add(operation);
			//client.setUpdateTime(new Date());
			this.getSession().update(client);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> listAll() {
		Query query = this.getSession().createQuery("from Operation order by id desc");
		return query.list();
	}

	@Override
	public Operation queryByID(int opsID) {
		Query query = this.getSession().createQuery("from Operation where id=:id");
		query.setParameter("id", opsID);
		@SuppressWarnings("rawtypes")
		List result = query.list();
		
		return (Operation)(result.size()>0?result.get(0):null);
	}

	@Override
	public void deleteByID(int opsID) {
		
		String hql="delete Operation where id=:id";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("id", opsID);
		query.executeUpdate();
	}

	@Override
	public void assign2Clients(int operationID, List<Client> hosts) {
		
		Session session = this.getSession();
		Operation operation = (Operation) session.get(Operation.class, operationID);
		
		if( null != operation){

			for(Client client : hosts){
				if(!operation.getClients().contains(client)){
					//client.getOperation().add(operation);
					//session.merge(client);
					operation.getClients().add(client);
					client.getOperation().add(operation);
					session.merge(client);
				}
			}
			//session.update(operation);
			//session.saveOrUpdate(operation);
			session.merge(operation);
		}
		//this.getSessionFactory().getCache().evictCollection("Client.operation", operationID);
	}
}
