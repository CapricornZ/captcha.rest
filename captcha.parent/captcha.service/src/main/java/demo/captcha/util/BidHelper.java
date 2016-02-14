package demo.captcha.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

public class BidHelper {
	
	
	public static class Captcha{
		private String sessionID;
		private byte[] captcha;
		public String getSessionID() { return sessionID; }
		public void setSessionID(String sessionID) { this.sessionID = sessionID; }
		
		public byte[] getCaptcha() { return captcha; }
		public void setCaptcha(byte[] captcha) { this.captcha = captcha; }
		
		public Captcha(String session, byte[] captcha){
			this.sessionID = session;
			this.captcha = captcha;
		}
	}
	
	public static Captcha changeCaptcha() throws ClientProtocolException, IOException{
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		
		HttpGet getCaptcha = new HttpGet("http://www.alltobid.com/GPCarQuery.Web/Image/ValiCode?r=0.15642416917324087");
		HttpResponse response = HttpClients.custom().setDefaultCookieStore(cookieStore).build().execute(getCaptcha);
		byte[] content = toByteArray(response.getEntity().getContent());
		String setCookie = response.getFirstHeader("Set-Cookie").getValue();
		String JSESSIONID = setCookie.substring("ASP.NET_SessionId=".length(), setCookie.indexOf(";"));
		
		return new Captcha(JSESSIONID, content);
	}
	
	public static String queryResponse(String session, String bid, String pass, String code) throws ClientProtocolException, IOException{
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie cookie = new BasicClientCookie("ASP.NET_SessionId", session);
		cookie.setDomain("www.alltobid.com");
		cookieStore.addCookie(cookie);

		String uri = String.format("http://www.alltobid.com/GPCarQuery.Web/Home/Query?idcard=%s&number=%s&type=2&nc=0.07437237841077149&code=%s", pass, bid, code);
		HttpGet getAjax = new HttpGet(uri);
		HttpResponse response = HttpClients.custom().setDefaultCookieStore(cookieStore).build().execute(getAjax);
		
		byte[] content = toByteArray(response.getEntity().getContent());
		String rtn = new String(content,"UTF-8");
		System.out.println(rtn);
		return rtn;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException{
		{
			Captcha captcha = changeCaptcha();
			File f = new File("/home/martin/tmp.jpeg");
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(captcha.getCaptcha(), 0, captcha.getCaptcha().length);
			fos.close();
			
			System.out.println("INPUT CODE:");
			InputStreamReader stdin = new InputStreamReader(System.in);//键盘输入 
			BufferedReader bufin = new BufferedReader(stdin);
			
			String code = bufin.readLine();
			String resp = queryResponse(captcha.getSessionID(), "53093961", "4019", code);
			System.out.println(resp);	
		}
	}
	
	public static byte[] toByteArray(InputStream input) throws IOException {
		
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return output.toByteArray();
	}
}
