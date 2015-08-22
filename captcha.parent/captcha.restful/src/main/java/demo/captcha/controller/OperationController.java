package demo.captcha.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.captcha.model.ScreenConfig;
import demo.captcha.service.IOperationService;
import demo.captcha.service.IScreenConfigService;
import demo.captcha.model.Operation;

@RequestMapping(value = "/command/operation")
@Controller
public class OperationController {

	private IScreenConfigService screenService;
	public void setScreenConfigService(IScreenConfigService service){
		this.screenService = service;
	}
	
	private IOperationService operationService;
	public void setOperationService(IOperationService operationService) {
		this.operationService = operationService;
	}
	
	@RequestMapping(value = "/list.do",method=RequestMethod.GET)
	public String listOperation(Model model){
		
		model.addAttribute("operations", this.operationService.listAll());
		List<ScreenConfig> screens = this.screenService.listAll();
		model.addAttribute("screens", screens);
		return "operation/list";
	}
	
	@RequestMapping(value = "/{operationID}",method=RequestMethod.GET)
	public String modifyDialog(Model model, @PathVariable("operationID") int opsID){
		
		Operation ops = this.operationService.queryByID(opsID);
		model.addAttribute("operation", ops);
		
		String rtn = String.format("operation/modify%sDialog", ops.getType());
		return rtn;
	}
	
	@RequestMapping(value = "/screenconfig/showAll.do",method=RequestMethod.GET)
	public String listScreenConfig(Model model){
		
		List<ScreenConfig> screens = this.screenService.listAll();
		model.addAttribute("screens", screens);
		return "screen/list";
	}
	
	@RequestMapping(value = "/screenconfig/bidStep2Form.do",method=RequestMethod.GET)
	public String newBidStep2Form(){
		return "screen/newBidStep2Form";
	}
	
	@RequestMapping(value = "/screenconfig/bidStep1Form.do",method=RequestMethod.GET)
	public String newBidStep1Form(){
		return "screen/newBidStep1Form";
	}
	
	@RequestMapping(value = "/screenconfig/loginForm.do",method=RequestMethod.GET)
	public String newLoginForm(){
		return "screen/newLoginForm";
	}
}
