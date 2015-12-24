package demo.captcha.controller;

import javax.ws.rs.PathParam;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.RoomInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.security.CodeGen;
import demo.captcha.security.User;
import demo.captcha.service.ICaptchaExamClientService;

@RequestMapping(value = "/admin")
@Controller
public class AdminController {

	private MultiUserChat muc;
	public void setMultiUserChat(MultiUserChat muc){ this.muc = muc; }
	
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
		model.addAttribute("client", host);
		//model.addAttribute("page", this.clientService.queryRecordByHostWithPage(client, 0));
		return "detail";
	}
	
	
	@RequestMapping(value = "/exam/client/{CLIENT}/record/page/{PAGE}",method=RequestMethod.GET)
	public String detailClientPage(Model model, @PathVariable("CLIENT")String host, @PathVariable("PAGE")int page){
		
		CaptchaExamClient client = this.clientService.queryByHost(host);
		model.addAttribute("client", host);
		model.addAttribute("page", this.clientService.queryRecordByHostWithPage(client, page));
		return "detail";
	}
	
	@RequestMapping(value = "/exam/newclient",method=RequestMethod.GET)
	public String createUserDialog(Model model){
		
		model.addAttribute("CODE", CodeGen.genRandomNum(6));
		return "createUserDialog";
	}
	
	@RequestMapping(value = "/exam/client/msg/{msg}",method=RequestMethod.POST)
	public String sendMessage(@PathParam("msg")String message){
		return "SUCCESS";
	}
}
