package demo.captcha.rs.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.captcha.model.Client;
import demo.captcha.rs.ICommandService;
import demo.captcha.rs.model.GlobalConfig;
import demo.captcha.service.IClientService;
import demo.captcha.model.Operation;

public class CommandService implements ICommandService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommandService.class);
	
	private IClientService clientService;
	public void setClientService(IClientService clientServ){ this.clientService = clientServ; }
	
	private GlobalConfig globalConfig;
	public void setGlobalConfig(GlobalConfig resource){ this.globalConfig = resource; }

	@Override
	public Client keepAlive(String fromHost) {

		logger.info("accept keepAlive from 【{}】", fromHost);
		Client client = new Client(fromHost);
		client = this.clientService.saveOrUpdate(client);
		if(client.getOperation() != null){
			for(Operation op : client.getOperation())
				logger.debug("\tOPS : {}", op.getTips());
		}
		return client;
	}

	@Override
	public GlobalConfig queryResource(String fromHost) {
		
		logger.info("init Global Config from 【{}】", fromHost);
		return this.globalConfig;
	}

}
