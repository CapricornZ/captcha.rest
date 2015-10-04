package demo.captcha.rs.impl;

import java.util.List;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.rs.IClientService;

public class ClientService implements IClientService {

	private demo.captcha.service.IClientService clientService;
	public void setClientService(demo.captcha.service.IClientService clientServ){
		this.clientService = clientServ;
	}
	
	@Override
	public void modify(String host, Config config) {
		
		this.clientService.modifyConfig(host, config);
	}

	@Override
	public void removeOperation(String host, int opsID) {
		
		this.clientService.removeOperation(host, opsID);
	}

	@Override
	public List<Client> listClient() {
		
		return this.clientService.list();
	}

	@Override
	public List<Client> listClientFilter(String host) {

		return this.clientService.listFilterByHost(host);
	}

}
