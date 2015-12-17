package demo.captcha.rs.impl;

import java.util.ArrayList;
import java.util.List;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.rs.IClientService;
import demo.captcha.rs.model.ClientHtml;

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
	public void modify(String[] hosts, String memo){
		
		this.clientService.modifyMemo(hosts, memo);
	}
	
	@Override
	public void removeOperation(String host, int opsID) {
		
		this.clientService.removeOperation(host, opsID);
	}
	
	@Override
	public void removeConfig(String host) {
		
		this.clientService.removeConfig(host);
	}

	@Override
	public List<ClientHtml> listClient() {
		
		List<ClientHtml> rtn = new ArrayList<ClientHtml>();
		List<Client> clients = this.clientService.list();
		for(Client client : clients)
			rtn.add(new ClientHtml(client));
		return rtn;
	}

	@Override
	public List<ClientHtml> listClientFilter(String host) {

		List<ClientHtml> rtn = new ArrayList<ClientHtml>();
		List<Client> clients = this.clientService.listFilterByHost(host);
		for(Client client : clients)
			rtn.add(new ClientHtml(client));
		return rtn;
	}
}
