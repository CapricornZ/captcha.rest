package demo.captcha.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import demo.captcha.model.Warrant;
import demo.captcha.rs.model.GlobalConfig;
import demo.captcha.service.IWarrantService;
import demo.captcha.service.Page;
import demo.captcha.util.LocalConfig;

@RequestMapping(value = "/command")
@Controller
public class CommandController implements ApplicationContextAware {
	
	//private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException { this.context = context; }
	
	private String storePath;
	public void setStorePath(String storePath){ this.storePath = storePath; }
	
	private LocalConfig localConfig;
	public void setLocalConfig(LocalConfig config){ this.localConfig = config; }
	
	private String absoluteUrl;
	public void setAbsoluteUrl(String value){ this.absoluteUrl = value; }
	
	private GlobalConfig globalConfigReal;
	public void setGlobalConfigREAL(GlobalConfig resource){ this.globalConfigReal = resource; }
	
	private GlobalConfig globalConfigSimulate;
	public void setGlobalConfigSIMULATE(GlobalConfig resource){ this.globalConfigSimulate = resource; }
	
	private IWarrantService warrantService;
	public void setWarrantService(IWarrantService service){ this.warrantService = service; }
	
	@RequestMapping(value="/prePublish", method={RequestMethod.GET,RequestMethod.POST})
	public String prePublish(Model model, HttpServletRequest request){

		String path = this.storePath;
		String version = "DEFAULT";
		java.io.FileInputStream fis;
		try {
			
			fis = new java.io.FileInputStream(path + "Release.ver");
			java.io.InputStreamReader isr = new java.io.InputStreamReader(fis);
			java.io.BufferedReader br = new java.io.BufferedReader(isr);
			version = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("VERSION", version);
		return "publish";
	}
	@RequestMapping(value = "/publish", method={RequestMethod.POST})
	public String publish(@RequestParam("dataFile")MultipartFile file, 
			@RequestParam("version")String version, Model model, HttpServletRequest request){

		String path = this.storePath;
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
			e.printStackTrace();
		} catch (IOException e) {
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
	
	@RequestMapping(value = "/resource/attachment/{VERSION}")
	@ResponseBody
	public String publishResource(@RequestParam("dataFile")MultipartFile file,
			@PathVariable("VERSION")String version) throws IOException{
		
		String rtn = "SUCCESS,";
		String path = this.storePath;
		String url = this.absoluteUrl;
		try {

			url = this.absoluteUrl + file.getOriginalFilename();
			java.io.FileOutputStream fos = new java.io.FileOutputStream(path + file.getOriginalFilename());			
			byte[] content = file.getBytes();
			fos.write(content);
			fos.flush();
			fos.close();

			rtn += url;
			if(version.equals("real")){
				this.globalConfigReal.setRepository(url);
				this.localConfig.setProperty("real.url", url);
			} else if (version.equals("simulate")){
				this.globalConfigSimulate.setRepository(url);
				this.localConfig.setProperty("simulate.url", url);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			rtn = "ERROR," + e;
		} catch (IOException e) {
			e.printStackTrace();
			rtn = "ERROR," + e;
		}
		return rtn;
	}
}
