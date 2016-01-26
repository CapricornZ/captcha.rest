package demo.captcha.rs.impl;

import java.util.List;

import demo.captcha.model.BidStep1Operation;
import demo.captcha.model.BidStep2Operation;
import demo.captcha.model.Client;
import demo.captcha.model.LoginOperation;
import demo.captcha.model.Operation;
import demo.captcha.rs.IOperationService;
import demo.captcha.service.IClientService;

public class OperationService implements IOperationService {

	private demo.captcha.service.IOperationService operationService;
	public void setOperationService(demo.captcha.service.IOperationService operationService) {
		this.operationService = operationService;
	}
	
	private IClientService clientService;
	public void setClientService(IClientService clientServ){
		this.clientService = clientServ;
	}
	
	@Override
	public List<Operation> queryOperation(String env) {

		return this.operationService.filterByEnv(env);
	}

	@Override
	public void acceptBidStep1(BidStep1Operation bid) {
		
		this.operationService.saveOrUpdate(bid);
	}

	@Override
	public void modifyBidStep1(int opsID, BidStep1Operation bid) {
		
		this.operationService.saveOrUpdate(bid);
	}

	@Override
	public void acceptBidStep2(BidStep2Operation bid) {
		
		this.operationService.saveOrUpdate(bid);
	}

	@Override
	public void modifyBidStep2(int opsID, BidStep2Operation bid) {
		
		this.operationService.saveOrUpdate(bid);
	}

	@Override
	public void modifyLogin(int opsID, LoginOperation login) {

		this.operationService.saveOrUpdate(login);
	}

	@Override
	public void deleteOperation(int opsID) {
		
		this.operationService.deleteByID(opsID);
	}
	
	@Override
	public void assignOperation(int opsID, String[] hosts) {
		
		List<Client> clients = this.clientService.listByIPs(hosts);
		this.operationService.assign2Clients(opsID, clients);
	}
}
