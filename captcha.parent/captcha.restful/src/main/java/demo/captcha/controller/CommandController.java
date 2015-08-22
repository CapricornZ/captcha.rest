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

import demo.captcha.rs.model.GlobalConfig;

@RequestMapping(value = "/command")
@Controller
public class CommandController implements ApplicationContextAware {
	
	private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException { this.context = context; }
	
	private GlobalConfig globalConfig;
	public void setGlobalConfig(GlobalConfig resource){ this.globalConfig = resource; }
	
	@RequestMapping(value = "/resource/preview", method={RequestMethod.GET})
	public String previewResource(Model model){

		model.addAttribute("TAG", this.globalConfig.getTag());
		model.addAttribute("PRICE", this.context.getBean("PRICE"));
		model.addAttribute("TIPS0", this.context.getBean("TIPS0"));
		model.addAttribute("TIPS1", this.context.getBean("TIPS1"));
		model.addAttribute("LOADING", this.context.getBean("LOADING"));
		model.addAttribute("CAPTCHA", this.context.getBean("CAPTCHA"));
		model.addAttribute("LOGIN", this.context.getBean("LOGIN"));
		
		return "resource/preview";
	}
}
