package demo.captcha.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.Base64;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.rs.model.ConfigHtml;
import demo.captcha.rs.model.TriggerV3;
import demo.captcha.rs.model.V3Common;
import demo.captcha.service.IClientService;
import demo.captcha.service.IConfigService;
import demo.captcha.util.BidHelper;
import demo.captcha.util.ReadConfigXlsV1;
import demo.captcha.util.ReadConfigXlsV2;

@RequestMapping(value = "/command/config")
@Controller
public class ConfigController {
	
	private IConfigService configService;
	public void setConfigService(IConfigService configService) { this.configService = configService; }
	
	private IClientService clientService;
	public void setClientService(IClientService service){ this.clientService = service; }
	
	@RequestMapping(value = "/init", method=RequestMethod.GET)
	public String initConfig(Model model){
		
		return "config/initConfig";
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
	
	@RequestMapping(value = "/create", method=RequestMethod.GET)
	public String initCreate(Model model){
		
		return "config/create";
	}
	
	@RequestMapping(value = "/queryResp/{configNO}", method=RequestMethod.GET)
	public String queryResp(Model model, @PathVariable("configNO")String no) throws ClientProtocolException, IOException{
		
		Config config = this.configService.queryByNo(no);
		model.addAttribute("config", config);
		return "config/queryResp";
	}
	
	@RequestMapping(value = "/queryResp/captcha", method=RequestMethod.GET)
	@ResponseBody
	public BidHelper.Captcha changeCaptcha() throws ClientProtocolException, IOException{

		BidHelper.Captcha captcha = BidHelper.changeCaptcha();
		return captcha;
	}
	
	@RequestMapping(value = "/queryResp/{configNO}/response", method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String ajaxResp(@PathVariable("configNO")String no, String pass, String code, String sessionID) throws ClientProtocolException, IOException{
		String rtn = BidHelper.queryResponse(sessionID, no, pass, code);
		return rtn;
	}
	
	@RequestMapping(value = "/detail/{configNO}", method=RequestMethod.GET)
	public String detailConfig(Model model, @PathVariable("configNO")String no){
		
		List<Client> clients = this.clientService.list();
		model.addAttribute("clients", clients);
		Config config = this.configService.queryByNo(no);
		model.addAttribute("config", config);

		//if(config.getClient() != null && (config.getClient().getTips() != null && !"".equals(config.getClient().getTips()))){
		if(config.getPolicy() != null && !"".equals(config.getPolicy())){
			
			com.google.gson.Gson gSon = new com.google.gson.Gson();
			TriggerV3 triggerV3 = gSon.fromJson(config.getPolicy(), TriggerV3.class);
			String v3Common = gSon.toJson(triggerV3.getCommon());
			model.addAttribute("v3Common", v3Common);
		} else {
			
			V3Common v3 = this.configService.getCommonV3();
			String v3Common = new com.google.gson.Gson().toJson(v3);
			model.addAttribute("v3Common", v3Common);
		}
		return "config/detailConfig";
	}
	
	@RequestMapping(value = "/common/v3", method=RequestMethod.GET)
	public String initCommon(Model model){
		
		V3Common v3 = this.configService.getCommonV3();
		String v3Common = new com.google.gson.Gson().toJson(v3);
		
		model.addAttribute("v3Common", v3Common);
		return "config/commonV3";
	}
	
	@RequestMapping(value = "/common/v3", method=RequestMethod.PUT)
	@ResponseBody
	public String saveCommon(Model model, @RequestBody V3Common v3) throws IOException {
		
		this.configService.setCommonV3(v3);
		return "success";
	}
	
	@RequestMapping(value = "/import/{VERSION}", method=RequestMethod.GET)
	public String initImport(Model model, @PathVariable("VERSION")String version){
		return "config/import" + version.toLowerCase();
	}
	
	@RequestMapping(value = "/uploadXls/{VERSION}")
	public String publish(@RequestParam("dataFile")MultipartFile file,
			@PathVariable("VERSION")String version, 
			Model model, HttpServletRequest request) throws IOException{
		
		if("v1".equals(version.toLowerCase())){
			
			ReadConfigXlsV1 readExcel = new ReadConfigXlsV1();
			List<String[]> configs = readExcel.readXlsx(file.getInputStream());
			model.addAttribute("configs", configs);
		}
		if("v3".equals(version.toLowerCase())){
			
			ReadConfigXlsV2 readExcel = new ReadConfigXlsV2();
			List<String[]> configs = readExcel.readXlsx(file.getInputStream());
			model.addAttribute("configs", configs);
		}
		
		return "config/batchConfigs" + version.toLowerCase();
	}
}
