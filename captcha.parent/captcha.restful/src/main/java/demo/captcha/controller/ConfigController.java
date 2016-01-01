package demo.captcha.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.rs.model.ConfigHtml;
import demo.captcha.service.IClientService;
import demo.captcha.service.IConfigService;
import demo.captcha.util.ReadConfigXls;
import demo.captcha.util.ReadExcel;

@RequestMapping(value = "/command/config")
@Controller
public class ConfigController {

	private IConfigService configService;
	public void setConfigService(IConfigService configService) { this.configService = configService; }
	
	private IClientService clientService;
	public void setClientService(IClientService service){ this.clientService = service; }
	
	@RequestMapping(value = "/init", method=RequestMethod.GET)
	public String initConfig(Model model){
		
		/*List<Config> configs = this.configService.listAll();
		List<ConfigHtml> configHtmls = new ArrayList<ConfigHtml>();
		for(Config config : configs)
			configHtmls.add(new ConfigHtml(config));
		
		model.addAttribute("configs", configHtmls);
		return "preConfig";*/
		
		return "initConfig";
	}
	
	@RequestMapping(value = "/manage", method=RequestMethod.GET)
	public String initManage(Model model){
		
		List<Config> configs = this.configService.listAll();
		List<ConfigHtml> configHtmls = new ArrayList<ConfigHtml>();
		for(Config config : configs)
			configHtmls.add(new ConfigHtml(config));
		
		model.addAttribute("configs", configHtmls);
		return "config/manage";
	}
	
	@RequestMapping(value = "/import", method=RequestMethod.GET)
	public String initImport(Model model){
		return "config/import";
	}
	
	@RequestMapping(value = "/create", method=RequestMethod.GET)
	public String initCreate(Model model){
		return "config/create";
	}
	
	@RequestMapping(value = "/detail/{configNO}", method=RequestMethod.GET)
	public String detailConfig(Model model, @PathVariable("configNO")String no){
		
		List<Client> clients = this.clientService.list();
		model.addAttribute("clients", clients);
		Config config = this.configService.queryByNo(no);
		model.addAttribute("config", config);
		return "detailConfig";
	}
	
	@RequestMapping(value = "/uploadXls")
	public String publish(@RequestParam("dataFile")MultipartFile file, 
			Model model, HttpServletRequest request) throws IOException{
		
		ReadConfigXls readExcel = new ReadConfigXls();
		List<String[]> configs = readExcel.readXlsx(file.getInputStream());
		model.addAttribute("configs", configs);
		return "config/batchConfigs";
	}
}
