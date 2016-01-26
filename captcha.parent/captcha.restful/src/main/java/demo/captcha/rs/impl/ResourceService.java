package demo.captcha.rs.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import demo.captcha.model.Resource;
import demo.captcha.rs.IResourceService;
import demo.captcha.rs.model.Entry;
import demo.captcha.rs.model.GlobalConfig;
import demo.captcha.rs.model.OrcConfig;
import demo.captcha.rs.model.OrcTipConfig;

public class ResourceService implements IResourceService, ApplicationContextAware {

	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException { this.context = context; }
	
	private demo.captcha.service.IResourceService resourceService;
	public void setResourceService(demo.captcha.service.IResourceService service){ this.resourceService = service; }
	
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

	@Override
	public void modifyEntries(List<Entry> entries, String category) {

		for(int i=0; i<entries.size(); i++){
			
			String beanID = String.format("ENTRY%d-%s", i, category);
			Entry entry = (Entry)this.context.getBean(beanID);
			entry.setDescription(entries.get(i).getDescription());
			entry.setUrl(entries.get(i).getUrl());
		}
	}

	@Override
	public void uploadAttachment(Attachment attachment) throws IOException {
		
		java.io.File destFile = new java.io.File("/home/martin/attach.bmp");
		attachment.transferTo(destFile);
	}

	@Override
	public void modifyResource(GlobalConfig gc, int id) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();    
		JsonGenerator gen = new JsonFactory().createJsonGenerator(sw); 
		mapper.writeValue(gen, gc);
		gen.close();
		String json = sw.toString(); 
		
		Resource resource = this.resourceService.queryByID(id);
		//String json = new com.google.gson.Gson().toJson(gc);
		resource.setContent(json);
		resource.setUpdateTime(new Date());
		this.resourceService.update(resource);
		System.out.println(gc);
	}

}