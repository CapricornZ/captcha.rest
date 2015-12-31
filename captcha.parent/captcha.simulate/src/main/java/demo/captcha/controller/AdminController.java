package demo.captcha.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import org.jivesoftware.smackx.muc.MultiUserChat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.security.CodeGen;
import demo.captcha.service.ICaptchaExamClientService;
import demo.captcha.tasks.TaskJob;
import demo.captcha.util.ReadExcel;

@RequestMapping(value = "/admin")
@Controller
public class AdminController {

	private MultiUserChat muc;
	public void setMultiUserChat(MultiUserChat muc){ this.muc = muc; }
	
	private ICaptchaExamClientService clientService;
	public void setClientService(ICaptchaExamClientService service){ this.clientService = service; }
	
	private TaskJob refreshRank;
	public void setTaskJob(TaskJob job){ this.refreshRank = job; }
	
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
	
	@RequestMapping(value = "/main",method=RequestMethod.GET)
	public String main(Model model){
		
		Subject currentUser = SecurityUtils.getSubject();
		CaptchaExamClient client = this.clientService.queryByHost((String)currentUser.getPrincipal());
		List<CaptchaExamClient> clients = this.clientService.listAll();
		
		model.addAttribute("currentUser", currentUser.getPrincipal());
		model.addAttribute("client", client);
		model.addAttribute("clients", clients);
		return "admin/main";
	}
	
	@RequestMapping(value = "/createUser",method=RequestMethod.GET)
	public String createUser(Model model){
		
		Subject currentUser = SecurityUtils.getSubject();
		CaptchaExamClient client = this.clientService.queryByHost((String)currentUser.getPrincipal());
		
		model.addAttribute("currentUser", currentUser.getPrincipal());
		model.addAttribute("client", client);
		return "admin/createUser";
	}
	
	@RequestMapping(value = "/batchUser")
	public String publish(@RequestParam("dataFile")MultipartFile file, 
			Model model, HttpServletRequest request) throws IOException{
		
		ReadExcel readExcel = new ReadExcel();
		List<CaptchaExamClient> list = readExcel.readXlsx(file.getInputStream());
		model.addAttribute("clients", list);
		return "admin/batchUser";
	}
	
	@RequestMapping(value = "/userExists", method = RequestMethod.GET)  
    @ResponseBody
	public String userExists(@RequestParam("userName")String userName){
		
		CaptchaExamClient client = this.clientService.queryByHost(userName);
		return client == null ? "true" : "false";
	}
	
	@RequestMapping(value = "/exam/client",method=RequestMethod.GET)
	@ResponseBody
	public Object listClient(){
		List<CaptchaExamClient> clients = this.clientService.listAll();
		return new JQTable(clients);
	}
	
	@RequestMapping(value = "/exam/refresh",method=RequestMethod.POST)
	@ResponseBody
	public String refreshRank(){
		
		this.refreshRank.execute();
		return "";
	}
	
	public static class JQTable{
		private List<CaptchaExamClient> data;
		public List<CaptchaExamClient> getData(){
			return this.data;
		}
		
		public JQTable(List<CaptchaExamClient> data){
			this.data = data;
		}
	}
}
