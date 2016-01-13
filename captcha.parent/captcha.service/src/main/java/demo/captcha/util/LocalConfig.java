package demo.captcha.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LocalConfig {
	
	private java.util.Properties prop = new java.util.Properties();
	private File config;
	private Map<String, String> defaults = new HashMap<String, String>();
	public void setDefaults(Map<String, String> value){ this.defaults = value; }
	
	public LocalConfig(String storePath) throws IOException{
		
		config = new java.io.File(storePath + "repository.prop");
		if(!config.exists())
			config.createNewFile();
		FileInputStream inStream = new FileInputStream(config);
		prop.load(inStream);
	}
	
	
	public String getProperty(String key){
		
		String rtn = prop.getProperty(key);
		if(rtn == null || rtn.equals(""))
			rtn = this.defaults.get(key);
		return rtn;
	}
	
	public void setProperty(String key, String value) throws IOException{
		
		prop.setProperty(key, value);
		FileOutputStream outStream = new FileOutputStream(this.config);
		prop.store(outStream, "update AT");
	}
}
