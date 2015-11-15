package demo.captcha.rs.impl;

import demo.captcha.model.Config;
import demo.captcha.rs.IConfigService;
import demo.captcha.model.Client;
import demo.captcha.service.IClientService;

public class ConfigService implements IConfigService {

	private demo.captcha.service.IConfigService configService;
	public void setConfigService(demo.captcha.service.IConfigService configService) {
		this.configService = configService;
	}
	
	private IClientService clientService;
	public void setClientService(IClientService clientServ){
		this.clientService = clientServ;
	}
	
	@Override
	public Config query(String bidNo) {
		
		return this.configService.queryByNo(bidNo);
	}

	@Override
	public void delete(String bidNo) {
		
		this.configService.delete(bidNo);
	}

	@Override
	public void create(Config config, String host, String tips) {
		
		if("".equals(host))
			this.configService.saveOrUpdate(config);
		else{
			Client client = this.clientService.queryByIP(host);
			if(tips != null){
				client.setMemo(tips);
				this.clientService.update(client);
			}
			this.configService.saveOrUpdate(config, client);
		}
	}

	@Override
	public void modify(Config config, String host, String tips) {
		
		if("".equals(host)){
			this.configService.saveOrUpdate(config, null);
		} else {
			Client client = this.clientService.queryByIP(host);
			if(null != tips){
				client.setMemo(tips);
				this.clientService.update(client);
			}
			this.configService.saveOrUpdate(config, client);
		}
	}
}
