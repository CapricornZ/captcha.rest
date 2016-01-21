package demo.captcha.rs.impl;

import demo.captcha.model.Config;
import demo.captcha.rs.IConfigService;
import demo.captcha.rs.model.AssignmentV1;
import demo.captcha.rs.model.AssignmentV2;
import demo.captcha.rs.model.AssignmentV3;
import demo.captcha.rs.model.Trigger;
import demo.captcha.rs.model.TriggerV2;
import demo.captcha.rs.model.TriggerV3;
import demo.captcha.rs.model.V3Common;

import java.util.List;

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
	public void create(Config config, String host) {
		
		if(null == host || "".equals(host))
			this.configService.saveOrUpdate(config);
		else{
			Client client = this.clientService.queryByIP(host);
			this.configService.saveOrUpdate(config, client);
		}
	}

	@Override
	public void modify(Config config) {
		
		this.configService.saveOrUpdate(config, null);
	}

	@Override
	public void assign(Trigger trigger, String bidNo) {

		Config config = this.configService.queryByNo(bidNo);
		String tips = new com.google.gson.Gson().toJson(trigger);
		config.setPolicy(tips);
		this.configService.saveOrUpdate(config, null);
	}
	
	@Override
	public void assign(TriggerV2 trigger, String bidNo) {
		
		Config config = this.configService.queryByNo(bidNo);
		String tips = new com.google.gson.Gson().toJson(trigger);
		config.setPolicy(tips);
		this.configService.saveOrUpdate(config, null);
	}
	
	@Override
	public void assign(TriggerV3 trigger, String bidNo) {
		
		Config config = this.configService.queryByNo(bidNo);
		if(null == trigger.getCommon())
			trigger.setCommon(this.configService.getCommonV3());
		String tips = new com.google.gson.Gson().toJson(trigger);
		config.setPolicy(tips);
		this.configService.saveOrUpdate(config, null);
	}
	
	@Override
	public void unAssign(String bidNo) {
		
		Config config = this.configService.queryByNo(bidNo);
		config.setPolicy(null);
		this.configService.saveOrUpdate(config, null);
	}
	
	private String format(Trigger trigger){
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s,价格:%s", trigger.getPriceTime(), trigger.getDeltaPrice()));
		if(null != trigger.getCaptchaTime())
			sb.append(";\r\n验证码触发:" + trigger.getCaptchaTime());
		if(null != trigger.getSubmitTime())
			sb.append(";\r\n提交触发:" + trigger.getSubmitTime());
		
		return sb.toString();
	}
	
	private String format(TriggerV2 trigger){
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s,价格:%s", trigger.getTriggers()[0].getPriceTime(), trigger.getTriggers()[0].getDeltaPrice()));
		if(null != trigger.getTriggers()[0].getCaptchaTime())
			sb.append(";\r\n验证码触发:" + trigger.getTriggers()[0].getCaptchaTime());
		if(null != trigger.getTriggers()[0].getSubmitTime())
			sb.append(";\r\n提交触发:" + trigger.getTriggers()[0].getSubmitTime());
		
		return sb.toString();
	}

	
	@Override
	public void newAssignmentV1(List<AssignmentV1> assigns) {
		this.configService.assignmentV1(assigns);
	}
	
	@Override
	public void newAssignmentV2(List<AssignmentV2> assigns) {
		this.configService.assignmentV2(assigns);
	}
	
	@Override
	public void newAssignmentV3(List<AssignmentV3> assignments) {
		this.configService.assignmentV3(assignments);
	}

	@Override
	public V3Common queryV3Common() {
		
		return this.configService.getCommonV3();
	}
}
