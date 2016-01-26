package demo.captcha.rs.model;

import java.lang.reflect.Type;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.codehaus.jackson.annotate.JsonSubTypes;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

@JsonTypeInfo(use=Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "category")
@JsonSubTypes({
	@JsonSubTypes.Type(value = OrcConfig.class, name = "OrcConfig"),
	@JsonSubTypes.Type(value = OrcTipConfig.class, name = "OrcTipConfig")
})
public interface IOrcConfig {
	String getCategory();
	
	//Gson Deserializer
	static public class OrcConfigDeserializer implements JsonDeserializer<IOrcConfig>{
		
		@Override
		public IOrcConfig deserialize(JsonElement json, Type arg1, JsonDeserializationContext context)
				throws JsonParseException {
			
			JsonObject jsonObject =  json.getAsJsonObject();
			JsonPrimitive prim = (JsonPrimitive)jsonObject.get("category");
		    String className = prim.getAsString();
		    
		    Class<?> klass = null;
			try {
				if(className.equals("OrcTipConfig"))
					klass = Class.forName("demo.captcha.rs.model.OrcTipConfig");
				
				if(className.equals("OrcConfig"))
			    	klass = Class.forName("demo.captcha.rs.model.OrcConfig");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new JsonParseException(e);
			}
		    
		    return context.deserialize(jsonObject, klass);
		}
		
	}
}
