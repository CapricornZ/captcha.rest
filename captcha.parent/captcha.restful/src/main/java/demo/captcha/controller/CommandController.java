package demo.captcha.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.bus.spring.Jsr250BeanPostProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import demo.captcha.model.Warrant;
import demo.captcha.rs.model.GlobalConfig;
import demo.captcha.service.IWarrantService;
import demo.captcha.service.Page;

@RequestMapping(value = "/command")
@Controller
public class CommandController implements ApplicationContextAware {
	
	//private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException { this.context = context; }
	
	private GlobalConfig globalConfigReal;
	public void setGlobalConfigREAL(GlobalConfig resource){ this.globalConfigReal = resource; }
	
	private GlobalConfig globalConfigSimulate;
	public void setGlobalConfigSIMULATE(GlobalConfig resource){ this.globalConfigSimulate = resource; }
	
	private IWarrantService warrantService;
	public void setWarrantService(IWarrantService service){
		this.warrantService = service;
	}
	
	@RequestMapping(value="/prePublish", method={RequestMethod.GET,RequestMethod.POST})
	public String prePublish(Model model, HttpServletRequest request){
		
		String path=request.getSession().getServletContext().getRealPath("/");
		String version = "DEFAULT";
		java.io.FileInputStream fis;
		try {
			
			fis = new java.io.FileInputStream(path + "Release.ver");
			java.io.InputStreamReader isr = new java.io.InputStreamReader(fis);
			java.io.BufferedReader br = new java.io.BufferedReader(isr);
			version = br.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("VERSION", version);
		return "publish";
	}
	@RequestMapping(value = "/publish", method={RequestMethod.POST})
	public String publish(@RequestParam("dataFile")MultipartFile file, @RequestParam("version")String version, Model model, HttpServletRequest request){
				
		String path=request.getSession().getServletContext().getRealPath("/");
		try {
			java.io.FileOutputStream fos = new java.io.FileOutputStream(path + "Release.zip");
			byte[] content = file.getBytes();
			fos.write(content);
			fos.flush();
			fos.close();
			
			fos = new java.io.FileOutputStream(path + "Release.ver");
			java.io.OutputStreamWriter osw = new java.io.OutputStreamWriter(fos);
			osw.write(version);
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "forward:prePublish";
	}
	
	@RequestMapping(value = "/warrant/preview", method={RequestMethod.GET})
	public String previewWarrant(Model model, @RequestParam("pageNumber")int pageNumber){
		
		Page<Warrant> page = this.warrantService.findWithPage(pageNumber);
		model.addAttribute("page", page);
		return "warrant/preview";
	}
	
	@RequestMapping(value = "/resource/preview", method={RequestMethod.GET})
	public String previewResource(Model model, @RequestParam("category")String category){

		model.addAttribute("CATEGORY", category);
		if("real".equals(category)){
			
			model.addAttribute("TAG", this.globalConfigReal.getTag());
			model.addAttribute("PRICE", this.context.getBean("PRICE-real"));
			model.addAttribute("TIPS0", this.context.getBean("TIPS0-real"));
			model.addAttribute("TIPS1", this.context.getBean("TIPS1-real"));
			model.addAttribute("LOADING", this.context.getBean("LOADING-real"));
			model.addAttribute("CAPTCHA", this.context.getBean("CAPTCHA-real"));
			model.addAttribute("LOGIN", this.context.getBean("LOGIN-real"));
			model.addAttribute("PRICE-SM", this.context.getBean("PRICE-sm-real"));
			model.addAttribute("ENTRIES", this.globalConfigReal.getEntries());
		} else {
			
			model.addAttribute("TAG", this.globalConfigSimulate.getTag());
			model.addAttribute("PRICE", this.context.getBean("PRICE-simulate"));
			model.addAttribute("TIPS0", this.context.getBean("TIPS0-simulate"));
			model.addAttribute("TIPS1", this.context.getBean("TIPS1-simulate"));
			model.addAttribute("LOADING", this.context.getBean("LOADING-simulate"));
			model.addAttribute("CAPTCHA", this.context.getBean("CAPTCHA-simulate"));
			model.addAttribute("LOGIN", this.context.getBean("LOGIN-simulate"));
			model.addAttribute("PRICE-SM", this.context.getBean("PRICE-sm-simulate"));
			model.addAttribute("ENTRIES", this.globalConfigSimulate.getEntries());
		}
		
		return "resource/preview";
	}
}
