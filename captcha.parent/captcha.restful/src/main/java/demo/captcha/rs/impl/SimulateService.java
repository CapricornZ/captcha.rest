package demo.captcha.rs.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.reflect.TypeToken;

import demo.captcha.rs.ISimulator;
import demo.captcha.rs.model.Captcha;
import demo.captcha.rs.model.HeartBeat;

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
		
		private String url = "http://139.196.24.58/captcha.server";
		public void setUrl(String url){ this.url = url; }

		public List<Captcha> create() throws ClientProtocolException, IOException {

			List<Captcha> rtn = null;
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpgets = new HttpGet(String.format("%s/repository.json", url));
			HttpResponse response = httpclient.execute(httpgets);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String str = convertStreamToString(instreams);
				rtn = new com.google.gson.Gson().fromJson(str, new TypeToken<List<Captcha>>() {}.getType());
				httpgets.abort();
			}
			
			if(null != rtn){
				for(Captcha captcha : rtn){
					String sub = captcha.getUrl();
					captcha.setUrl(String.format("%s/%s", url, sub));
				}
			}
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
	public void setRepository(RepositoryCreator creator) throws ClientProtocolException, IOException{
		this.Repository = creator.create();
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
}
