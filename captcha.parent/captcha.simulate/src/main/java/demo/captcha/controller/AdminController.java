package demo.captcha.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.security.CodeGen;
import demo.captcha.service.ICaptchaExamClientService;

@RequestMapping(value = "/admin")
@Controller
public class AdminController {

	private ICaptchaExamClientService clientService;
	public void setClientService(ICaptchaExamClientService service){ this.clientService = service; }
	
	@RequestMapping(value = "/index",method=RequestMethod.GET)
	public String listClient(Model model){
		
		model.addAttribute("clients", this.clientService.listAll());
		return "index";
	}
	
	@RequestMapping(value = "/exam/client/{CLIENT}",method=RequestMethod.GET)
	public String detailClient(Model model, @PathVariable("CLIENT")String host){
		
		CaptchaExamClient client = this.clientService.queryByHost(host);
		model.addAttribute("records", this.clientService.queryRecordByHost(client));
		return "detail";
	}
	
	@RequestMapping(value = "/exam/newclient",method=RequestMethod.GET)
	public String createUserDialog(Model model){
		
		model.addAttribute("CODE", CodeGen.genRandomNum(8));
		return "createUserDialog";
	}
}
