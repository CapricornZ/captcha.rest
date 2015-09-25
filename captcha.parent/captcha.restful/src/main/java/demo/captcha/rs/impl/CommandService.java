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
	
	private GlobalConfig globalConfigREAL;
	public void setGlobalConfigREAL(GlobalConfig resource){ this.globalConfigREAL = resource; }
	
	private GlobalConfig globalConfigSIMULATE;
	public void setGlobalConfigSIMULATE(GlobalConfig resource){ this.globalConfigSIMULATE = resource; }

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
	public GlobalConfig queryResource(String fromHost, String category) {
		
		logger.info("init Global Config({}) from 【{}】", category, fromHost);
		if("simulate".equals(category))
			return this.globalConfigSIMULATE;
		else
			return this.globalConfigREAL;
	}

}
