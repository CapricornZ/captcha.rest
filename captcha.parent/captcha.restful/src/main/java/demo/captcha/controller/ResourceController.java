package demo.captcha.controller;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import demo.captcha.model.Resource;
import demo.captcha.rs.model.GlobalConfig;
import demo.captcha.service.IResourceService;

@RequestMapping(value = "/command/resource")
@Controller
public class ResourceController implements ApplicationContextAware{
	
	private IResourceService resourceService;
	public void setResourceService(IResourceService service){ this.resourceService = service; }
	
	private GlobalConfig globalConfigReal;
	public void setGlobalConfigREAL(GlobalConfig resource){ this.globalConfigReal = resource; }
	
	private GlobalConfig globalConfigSimulate;
	public void setGlobalConfigSIMULATE(GlobalConfig resource){ this.globalConfigSimulate = resource; }
	
	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException { this.context = context; }
	
	@RequestMapping(value = "/preview", method={RequestMethod.GET})
	public String previewResource(Model model, @RequestParam("category")String category){

		model.addAttribute("CATEGORY", category);
		if("real".equals(category)){
			
			model.addAttribute("TAG", this.globalConfigReal.getTag());
			model.addAttribute("URL", this.globalConfigReal.getRepository());
			model.addAttribute("PRICE", this.context.getBean("PRICE-real"));
			model.addAttribute("TIPS0", this.context.getBean("TIPS0-real"));
			model.addAttribute("TIPS1", this.context.getBean("TIPS1-real"));
			model.addAttribute("LOADING", this.context.getBean("LOADING-real"));
			model.addAttribute("CAPTCHA", this.context.getBean("CAPTCHA-real"));
			model.addAttribute("LOGIN", this.context.getBean("LOGIN-real"));
			model.addAttribute("PRICE-SM", this.context.getBean("PRICE-sm-real"));
			model.addAttribute("TIME", this.context.getBean("TIME-real"));
			model.addAttribute("ENTRIES", this.globalConfigReal.getEntries());
		} else {
			
			model.addAttribute("TAG", this.globalConfigSimulate.getTag());
			model.addAttribute("URL", this.globalConfigSimulate.getRepository());
			model.addAttribute("PRICE", this.context.getBean("PRICE-simulate"));
			model.addAttribute("TIPS0", this.context.getBean("TIPS0-simulate"));
			model.addAttribute("TIPS1", this.context.getBean("TIPS1-simulate"));
			model.addAttribute("LOADING", this.context.getBean("LOADING-simulate"));
			model.addAttribute("CAPTCHA", this.context.getBean("CAPTCHA-simulate"));
			model.addAttribute("LOGIN", this.context.getBean("LOGIN-simulate"));
			model.addAttribute("TIME", this.context.getBean("TIME-simulate"));
			model.addAttribute("PRICE-SM", this.context.getBean("PRICE-sm-simulate"));
			model.addAttribute("ENTRIES", this.globalConfigSimulate.getEntries());
		}
		
		return "resource/preview";
	}
	
	@RequestMapping(value = "/main.do",method=RequestMethod.GET)
	public String main(Model model){
		
		List<Resource> resources = this.resourceService.listAll();
		model.addAttribute("RESOURCES", resources);
		return "resource/main";
	}
	
	@RequestMapping(value = "/detail.do",method=RequestMethod.GET)
	public String detail(Model model, @RequestParam("id")int id) throws JsonParseException, JsonMappingException, IOException{
		
		Resource resource = this.resourceService.queryByID(id);
		
		ObjectMapper mapper = new ObjectMapper();
		GlobalConfig gc = mapper.readValue(resource.getContent(), GlobalConfig.class);
		
		//GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(IOrcConfig.class, new IOrcConfig.OrcConfigDeserializer());
        //Gson gson = builder.create();
        
		//GlobalConfig gc = gson.fromJson(resource.getContent(), GlobalConfig.class);
		model.addAttribute("CATEGORY", resource.getTag());
		model.addAttribute("ID", id);
			
		model.addAttribute("TAG", gc.getTag());
		model.addAttribute("URL", gc.getRepository());
		model.addAttribute("PRICE", gc.getOrcConfigs().get(0));
		model.addAttribute("TIPS0", gc.getOrcConfigs().get(1));
		model.addAttribute("TIPS1", gc.getOrcConfigs().get(2));
		model.addAttribute("LOADING", gc.getOrcConfigs().get(3));
		model.addAttribute("CAPTCHA", gc.getOrcConfigs().get(4));
		model.addAttribute("LOGIN", gc.getOrcConfigs().get(5));
		model.addAttribute("PRICE-SM", gc.getOrcConfigs().get(6));
		model.addAttribute("TIME", gc.getOrcConfigs().get(7));
		model.addAttribute("ENTRIES", gc.getEntries());
		
		return "resource/detail";
	}
}
