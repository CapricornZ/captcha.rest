package demo.captcha.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.service.IClientService;
import demo.captcha.service.IConfigService;

@RequestMapping(value = "/command/config")
@Controller
public class ConfigController {

	private IConfigService configService;
	public void setConfigService(IConfigService configService) { this.configService = configService; }
	
	private IClientService clientService;
	public void setClientService(IClientService service){ this.clientService = service; }
	
	@RequestMapping(value = "/init", method=RequestMethod.GET)
	public String initConfig(Model model){
		
		model.addAttribute("configs", this.configService.listAll());
		return "preConfig";
	}
	
	@RequestMapping(value = "/detail/{configNO}", method=RequestMethod.GET)
	public String detailConfig(Model model, @PathVariable("configNO")String no){
		
		List<Client> clients = this.clientService.list();
		model.addAttribute("clients", clients);
		Config config = this.configService.queryByNo(no);
		model.addAttribute("config", config);
		return "detailConfig";
	}
}
