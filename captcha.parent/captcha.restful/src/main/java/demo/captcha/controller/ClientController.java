package demo.captcha.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.captcha.model.Client;
import demo.captcha.rs.model.ClientHtml;
import demo.captcha.service.IClientService;

@RequestMapping(value = "/command/client")
@Controller
public class ClientController {

	private IClientService clientService;
	public void setClientService(IClientService clientServ){
		this.clientService = clientServ;
	}
	
	@RequestMapping(value = "/stastic.do",method=RequestMethod.GET)
	public String stastic(Model model){
		
		List<Client> clients = this.clientService.list();
		List<ClientHtml> html = new ArrayList<ClientHtml>();
		for(Client client : clients)
			html.add(new ClientHtml(client));
		 model.addAttribute("CLIENTS", html);
		 return "client/stastic";
	}
	
	@RequestMapping(value = "/{HOST}/detail.do",method=RequestMethod.GET)
	public String detailClient(@PathVariable("HOST")String host, Model model){
		
		Client client = this.clientService.queryByIP(host);
		model.addAttribute("client", client);
		return "client/detail";
	}
	
	@RequestMapping(value = "/{HOST}/operation.do",method=RequestMethod.GET)
	public String clientOperation(@PathVariable("HOST")String host, Model model){
		
		Client client = this.clientService.queryByIP(host);
		model.addAttribute("client", client);
		return "operationOfClient";
	}
}
