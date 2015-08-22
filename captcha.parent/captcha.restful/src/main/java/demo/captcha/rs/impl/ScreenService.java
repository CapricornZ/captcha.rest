package demo.captcha.rs.impl;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import demo.captcha.model.ScreenConfig;
import demo.captcha.rs.IScreenService;
import demo.captcha.rs.model.BidStep1;
import demo.captcha.rs.model.BidStep2;
import demo.captcha.service.IScreenConfigService;

public class ScreenService implements IScreenService {

	private ObjectMapper objMapper = new ObjectMapper();
	private IScreenConfigService screenService;
	public void setScreenConfigService(IScreenConfigService service){ this.screenService = service; }
	
	@Override
	public String acceptStep1(String fromHost, BidStep1 bid) {
		
		ScreenConfig config = new ScreenConfig();
		try {
			config.setFromHost(fromHost);
			config.setCategory("BIDStep1");
			config.setJsonContent(objMapper.writeValueAsString(bid));
			this.screenService.save(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}

	@Override
	public String acceptStep2(String fromHost, BidStep2 bid) {
		
		ScreenConfig config = new ScreenConfig();
		try {
			config.setFromHost(fromHost);
			config.setCategory("BIDStep2");
			config.setJsonContent(objMapper.writeValueAsString(bid));
			this.screenService.save(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}

	@Override
	public List<ScreenConfig> listScreenConfig() {
		
		return this.screenService.listAll();
	}
}
