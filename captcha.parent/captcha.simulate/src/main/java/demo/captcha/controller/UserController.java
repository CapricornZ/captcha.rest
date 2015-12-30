package demo.captcha.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.Ranking;
import demo.captcha.model.rank.CaptchaRank;
import demo.captcha.model.rank.RateRank;
import demo.captcha.service.ICaptchaExamClientService;
import demo.captcha.service.IRankService;

@RequestMapping(value = "/user")
@Controller
public class UserController {
	
	private ICaptchaExamClientService clientService;
	public void setClientService(ICaptchaExamClientService service){ this.clientService = service; }
	
	private IRankService rankService;
	public void setRankService(IRankService service){ this.rankService = service; }
	
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
		List<Ranking> ranks = this.rankService.getRankByOwner(client);
		
		final int target = 6000;
		model.addAttribute("currentUser", currentUser.getPrincipal());
		model.addAttribute("client", client);
		model.addAttribute("finish", client.getTotal());
		model.addAttribute("target", target);
		for(Ranking rank : ranks){
			if(rank instanceof CaptchaRank)
				model.addAttribute("CAPTCHA-RANK", rank);
			if(rank instanceof RateRank)
				model.addAttribute("RATE-RANK", rank);;
		}
		int rate = 0;
		if(client.getTotal() != 0)
			rate = client.getCorrect()*100/client.getTotal();
		model.addAttribute("rate", rate);
		return "user/main";
	}
	
	@RequestMapping(value = "/rankingList",method=RequestMethod.GET)
	public String rankingList(Model model){
		List<Ranking> rankCaptcha = this.rankService.topCaptcha(5);
		List<Ranking> rankRate = this.rankService.topRate(5);
		model.addAttribute("CAPTCHA", rankCaptcha);
		model.addAttribute("RATE", rankRate);
		
		if(rankCaptcha != null && rankCaptcha.size()>0)
			model.addAttribute("lastUpdate", rankCaptcha.get(0).getUpdateTime());
		return "user/RankingList";
	}
	
	@RequestMapping(value = "/{USER}/sign",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String signToday(Model model, @PathVariable("USER")String user){
		
		String rtn = "签到成功";
		CaptchaExamClient client = this.clientService.queryByHost(user);
		if(null != client){
			
			final int DAY = 24*3600*1000;
			long now = new Date().getTime();
			long last= client.getUpdateTime().getTime();
			long nowDay = now / DAY;
			long lastDay= last / DAY;

			if(client.getCheckIns() != 0 && nowDay > lastDay || client.getCheckIns() == 0){
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
