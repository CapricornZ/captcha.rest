package demo.captcha.rs.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.Warrant;
import demo.captcha.rs.ISimulator;
import demo.captcha.rs.model.Captcha;
import demo.captcha.rs.model.HeartBeat;
import demo.captcha.service.ICaptchaExamClientService;
import demo.captcha.simulate.CaptchaReq;
import demo.captcha.simulate.Simulator;

public class SimulateService implements ISimulator {

	static public class PolicyGenerator{
		
		private int warnPrice;
		public void setWarnPrice(int value){ this.warnPrice = value; }
		
		private int[] change;		
		public void setChange(int[] change){ 
			this.change = change; 
		}
		
		public List<HeartBeat> create(){
			
			List<HeartBeat> rtn = new ArrayList<HeartBeat>();
			Calendar rightNow = Calendar.getInstance();
			int min = rightNow.get(Calendar.MINUTE);
			int sec = rightNow.get(Calendar.SECOND);
			int price = this.warnPrice;
			java.util.Random random = new java.util.Random();
			
			for(int i=sec; i<60; i++){
				
				rightNow.add(Calendar.SECOND, +1);
				String time = DateFormator.format(rightNow.getTime());
				price = price + (random.nextBoolean() ? 100 : 0);
				HeartBeat hb = new HeartBeat();
				hb.setFinish(false);
				hb.setPrice(price);
				hb.setTime(time);
				rtn.add(hb);
			}
			
			for(int i=0; i<60; i++){
				
				rightNow.add(Calendar.SECOND, +1);
				String time = DateFormator.format(rightNow.getTime());
				price += this.change[i];
				HeartBeat hb = new HeartBeat();
				hb.setFinish(false);
				hb.setPrice(price);
				hb.setTime(time);
				rtn.add(hb);
			}

			rightNow.add(Calendar.SECOND, +1);
			HeartBeat hb = new HeartBeat();
			hb.setFinish(true);
			hb.setPrice(price);
			hb.setTime(DateFormator.format(rightNow.getTime()));
			rtn.add(hb);
			
			return rtn;
		}
	}
	
	static public class RepositoryCreator {
		
		private String url = "classpath:repository.json";
		public void setUrl(String url){ this.url = url; }

		public List<Captcha> create() throws IOException {

			List<Captcha> rtn = null;
			//System.out.println(this.getClass().getResource("/"));
			//System.out.println(RepositoryCreator.class.getResource("/"));
			java.io.InputStream is = this.getClass().getResourceAsStream("/repository.json");
			//java.io.FileInputStream fis = new java.io.FileInputStream(url);
			String str = convertStreamToString(is);
			rtn = new com.google.gson.Gson().fromJson(str, new TypeToken<List<Captcha>>() {}.getType());
			
			//if(null != rtn){
			//	for(Captcha captcha : rtn){
			//		String sub = captcha.getUrl();
			//		captcha.setUrl(String.format("%s/%s", url, sub));
			//	}
			//}
			return rtn;
		}

		public static String convertStreamToString(InputStream is) {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
	}

	private static java.text.DateFormat DateFormator = new java.text.SimpleDateFormat("HH:mm:ss");
	private java.util.Random random = new java.util.Random();
	
	private List<PolicyGenerator> policyGenerators;
	public void setPolicyGenerators(List<PolicyGenerator> generators){ this.policyGenerators = generators; }
	
	private List<Captcha> Repository;
	public void setRepository(RepositoryCreator creator) throws IOException{
		this.Repository = creator.create();
	}
	
	private ICaptchaExamClientService clientService;
	public void setCaptchaExamClientService(ICaptchaExamClientService client){
		this.clientService = client;
	}
	
	@Override
	public List<HeartBeat> initial() {

		return this.policyGenerators.get(random.nextInt(policyGenerators.size())).create();
	}

	@Override
	public Captcha randomCaptcha() {

		return this.Repository.get(random.nextInt(Repository.size()));
	}

	@Override
	public String receiveBidReq() {

		return null;
	}

	@Override
	public CaptchaExamClient register(String host, Warrant warrant) {
		
		return this.clientService.register(host, warrant);
	}

	@Override
	public CaptchaExamClient keepAlive(String host) {
		
		CaptchaExamClient client = this.clientService.queryByHost(host);
		client.setUpdateTime(new Date());
		return this.clientService.update(client);
	}
	
	private Simulator simulator;
	public void setSimulator(Simulator sim) { this.simulator = sim; }
	
	@Override
	public CaptchaReq random() {
		
		System.out.println("Random CaptchaREQ");
		try {
			return this.simulator.generateCaptcha();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
