package demo.captcha.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.security.User;
import demo.captcha.service.ICaptchaExamClientService;

@RequestMapping(value = "/")
@Controller
public class RootController {
	
	private ICaptchaExamClientService clientService;
	public void setClientService(ICaptchaExamClientService service){ this.clientService = service; }

	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, 
    		HttpServletRequest request) {
  
        Subject currentUser = SecurityUtils.getSubject();
        String result = "login";
        if (!currentUser.isAuthenticated()) {
            result = login(currentUser,username,password);  
        }else{//重复登录
            //User shiroUser = (User)currentUser.getPrincipal();
            //if(!shiroUser.getPrincipal().equalsIgnoreCase(username)){//如果登录名不同
            //    currentUser.logout();
            //    result = login(currentUser,username,password);
            //}
        }
        return "redirect:user/main";
        //SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        // 获取保存的URL
        //if (savedRequest == null || savedRequest.getRequestUrl() == null)
        //    return "redirect:/index";
        //String savedUrl = savedRequest.getRequestUrl();
        //return "redirect:" + savedUrl;
    }
	
	public static SavedRequest getSavedRequest(ServletRequest request) {
		
        SavedRequest savedRequest = null;
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);
        if (session != null) {
            savedRequest = (SavedRequest) session.getAttribute("shiroSavedRequest");
        }
        return savedRequest;
    }

	private String login(Subject currentUser,String username,String password){
		
        String result = "login";  
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);  
        token.setRememberMe(false);  
        try {  
            currentUser.login(token);  
            result = "success";  
        } catch (UnknownAccountException uae) {  
            result = "failure";  
        } catch (IncorrectCredentialsException ice) {  
            result = "failure";  
        } catch (LockedAccountException lae) {  
            result = "failure";  
        } catch (AuthenticationException ae) {  
            result = "failure";  
        }  
        return result;  
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)  
    public String logout() {  
  
        Subject currentUser = SecurityUtils.getSubject();  
        String result = "logout";  
        currentUser.logout();
        return "redirect:user/main";
    }  
}
