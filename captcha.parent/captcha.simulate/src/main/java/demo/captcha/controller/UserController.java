package demo.captcha.controller;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.service.ICaptchaExamClientService;

@RequestMapping(value = "/user")
@Controller
public class UserController {
	
	private ICaptchaExamClientService clientService;
	public void setClientService(ICaptchaExamClientService service){ this.clientService = service; }
	
	@RequestMapping(value = "/exam",method=RequestMethod.GET)
	public String listClient(Model model){

		Subject currentUser = SecurityUtils.getSubject();
		model.addAttribute("currentUser", currentUser.getPrincipal());
		return "user/exam";
	}
	
	@RequestMapping(value = "/main",method=RequestMethod.GET)
	public String main(Model model){
		
		Subject currentUser = SecurityUtils.getSubject();
		CaptchaExamClient client = this.clientService.queryByHost((String)currentUser.getPrincipal());
		long count = this.clientService.countRecordByHost(client);
		int target = 6000;
		model.addAttribute("currentUser", currentUser.getPrincipal());
		model.addAttribute("client", client);
		model.addAttribute("finish", count);
		model.addAttribute("target", target);
		int rate = 0;
		if(client.getTotal() != 0)
			rate = client.getCorrect()*100/client.getTotal();
		model.addAttribute("rate", rate);
		return "user/main";
	}
	
	@RequestMapping(value = "/{USER}/sign",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String signToday(Model model, @PathVariable("USER")String user){
		
		String rtn = "签到成功";
		CaptchaExamClient client = this.clientService.queryByHost(user);
		if(null != client){
			
			long now = new Date().getTime();
			long update = client.getUpdateTime().getTime();
			long diff = now - update;
			long day = 24 * 60 * 60 * 1000;
			if(client.getCheckIns() != 0 && diff > day || client.getCheckIns() == 0){
				client.setCheckIns(client.getCheckIns() + 1);
				client.setUpdateTime(new Date());
				this.clientService.update(client);
				rtn += "@" + new Date().toString();
			} else {
				
				rtn = "今天已经签到";
			}
		} else {
			rtn = "无法完成签到，没找到指定用户" + user;
		}
		return rtn;
	}
}
