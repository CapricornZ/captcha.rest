package demo.captcha.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import demo.captcha.model.Warrant;
import demo.captcha.rs.model.GlobalConfig;
import demo.captcha.service.IWarrantService;
import demo.captcha.service.Page;

@RequestMapping(value = "/command")
@Controller
public class CommandController implements ApplicationContextAware {
	
	private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException { this.context = context; }
	
	private GlobalConfig globalConfigReal;
	public void setGlobalConfigREAL(GlobalConfig resource){ this.globalConfigReal = resource; }
	
	private GlobalConfig globalConfigSimulate;
	public void setGlobalConfigSIMULATE(GlobalConfig resource){ this.globalConfigSimulate = resource; }
	
	private IWarrantService warrantService;
	public void setWarrantService(IWarrantService service){
		this.warrantService = service;
	}
	
	@RequestMapping(value = "/warrant/preview", method={RequestMethod.GET})
	public String previewWarrant(Model model, @RequestParam("pageNumber")int pageNumber){
		
		Page<Warrant> page = this.warrantService.findWithPage(pageNumber);
		model.addAttribute("page", page);
		return "warrant/preview";
	}
	
	@RequestMapping(value = "/resource/preview", method={RequestMethod.GET})
	public String previewResource(Model model, @RequestParam("category")String category){

		model.addAttribute("CATEGORY", category);
		if("real".equals(category)){
			
			model.addAttribute("TAG", this.globalConfigReal.getTag());
			model.addAttribute("PRICE", this.context.getBean("PRICE-real"));
			model.addAttribute("TIPS0", this.context.getBean("TIPS0-real"));
			model.addAttribute("TIPS1", this.context.getBean("TIPS1-real"));
			model.addAttribute("LOADING", this.context.getBean("LOADING-real"));
			model.addAttribute("CAPTCHA", this.context.getBean("CAPTCHA-real"));
			model.addAttribute("LOGIN", this.context.getBean("LOGIN-real"));
		} else {
			
			model.addAttribute("TAG", this.globalConfigSimulate.getTag());
			model.addAttribute("PRICE", this.context.getBean("PRICE-simulate"));
			model.addAttribute("TIPS0", this.context.getBean("TIPS0-simulate"));
			model.addAttribute("TIPS1", this.context.getBean("TIPS1-simulate"));
			model.addAttribute("LOADING", this.context.getBean("LOADING-simulate"));
			model.addAttribute("CAPTCHA", this.context.getBean("CAPTCHA-simulate"));
			model.addAttribute("LOGIN", this.context.getBean("LOGIN-simulate"));
		}
		
		return "resource/preview";
	}
}
