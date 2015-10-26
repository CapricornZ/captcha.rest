package demo.captcha.rs.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import demo.captcha.rs.IResourceService;
import demo.captcha.rs.model.OrcConfig;
import demo.captcha.rs.model.OrcTipConfig;

public class ResourceService implements IResourceService, ApplicationContextAware {

	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException { this.context = context; }
	
	@Override
	public void modifyTipConfig(OrcTipConfig config, String category, String id) {

		String beanID = String.format("%s-%s", id, category);
		OrcTipConfig obj = (OrcTipConfig)this.context.getBean(beanID);
		
		obj.getConfigTip().setIndex(config.getConfigTip().getIndex());
		obj.getConfigTip().setOffsetX(config.getConfigTip().getOffsetX());
		obj.getConfigTip().setOffsetY(config.getConfigTip().getOffsetY());
		obj.getConfigTip().setWidth(config.getConfigTip().getWidth());
		obj.getConfigTip().setHeight(config.getConfigTip().getHeight());
		obj.getConfigTip().setMinNearSpots(config.getConfigTip().getMinNearSpots());
		
		obj.getConfigNo().setIndex(config.getConfigNo().getIndex());
		obj.getConfigNo().setOffsetX(config.getConfigNo().getOffsetX());
		obj.getConfigNo().setOffsetY(config.getConfigNo().getOffsetY());
		obj.getConfigNo().setWidth(config.getConfigNo().getWidth());
		obj.getConfigNo().setHeight(config.getConfigNo().getHeight());
		obj.getConfigNo().setMinNearSpots(config.getConfigNo().getMinNearSpots());
	}

	@Override
	public void modifyConfig(OrcConfig config, String category, String id) {

		String beanID = String.format("%s-%s", id, category);
		OrcConfig obj = (OrcConfig)this.context.getBean(beanID);
		
		obj.setIndex(config.getIndex());
		obj.setOffsetX(config.getOffsetX());
		obj.setOffsetY(config.getOffsetY());
		obj.setWidth(config.getWidth());
		obj.setHeight(config.getHeight());
		obj.setMinNearSpots(config.getMinNearSpots());
	}

}
