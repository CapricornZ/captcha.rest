package demo.captcha.service;

import java.util.List;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.model.Warrant;

public interface IClientService {
	
	Client saveOrUpdate(Client client);
	void delete(Client client);
	void update(Client record);
	Client queryByIP(String ip);
	List<Client> list();
	List<Client> listFilterByHost(String host);
	List<Client> listByFilter(String tag);
	List<Client> listByIPs(String[] hosts);
	
	Client register(String host, Warrant warrant);
	
	void removeOperation(String host, int OperationID);
	void modifyConfig(String host, Config config);
}
