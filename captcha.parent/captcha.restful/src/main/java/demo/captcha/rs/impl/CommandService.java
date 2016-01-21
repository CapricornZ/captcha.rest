package demo.captcha.rs.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.captcha.model.Client;
import demo.captcha.model.Warrant;
import demo.captcha.rs.ICommandService;
import demo.captcha.rs.model.GlobalConfig;
import demo.captcha.service.IClientService;
import demo.captcha.service.IOperationService;
import demo.captcha.service.IWarrantService;
import demo.captcha.model.Operation;

public class CommandService implements ICommandService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommandService.class);
	
	private IClientService clientService;
	public void setClientService(IClientService service){ this.clientService = service; }
	
	private IOperationService operationService;
	public void setOperationService(IOperationService service){ this.operationService = service; }
	
	private IWarrantService warrantService;
	public void setWarrantService(IWarrantService service){ this.warrantService = service; }
	
	private GlobalConfig globalConfigREAL;
	public void setGlobalConfigREAL(GlobalConfig resource){ this.globalConfigREAL = resource; }
	
	private GlobalConfig globalConfigSIMULATE;
	public void setGlobalConfigSIMULATE(GlobalConfig resource){ this.globalConfigSIMULATE = resource; }
	

	@Override
	public Client keepAlive(String fromHost, String env) {

		logger.info("KEEPALIVE : accept from 【fromHost:{}, environment:{}】", fromHost, env);
		Client client = new Client(fromHost);
		client = this.clientService.saveOrUpdate(client);
		List<Operation> operations = this.operationService.filterBy(env);
		client.setOperation(operations);
		
		return client;
	}
	
	@Override
	public GlobalConfig queryResource(String fromHost, String category) {
		
		logger.info("CONFIG : init Global({}) from 【{}】", category, fromHost);
		if("simulate".equals(category)){
			
			GlobalConfig gc = this.globalConfigSIMULATE;
			return gc;
		} else {
			
			GlobalConfig gc = this.globalConfigREAL;
			return gc;
		}
	}

	@Override
	public Client register(String host, Warrant warrant) {
		
		logger.info("WARRANT : Register from 【{}】, warrant【id:{}, code:{}】", host, warrant.getId(), warrant.getCode());
		Client client = this.clientService.register(host, warrant);
		return client;
	}

	@Override
	public int generateWarrant() {

		logger.info("WARRANT : Generate(25)");
		this.warrantService.generateWarrant(25);
		return 25;
	}

	@Override
	public void updateWarrant(Warrant warrant) {
		
		logger.info("WARRANT : Modify");
		this.warrantService.update(warrant);
	}
}
