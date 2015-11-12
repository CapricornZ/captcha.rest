package demo.captcha.rs;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import demo.captcha.rs.model.Captcha;
import demo.captcha.rs.model.HeartBeat;

@WebService(endpointInterface="demo.captcha.restful.ISimulator", serviceName="ISimulator")
@Path("/simulate")
public interface ISimulator {
	
	@GET
	@Path("/start")
	@Produces({MediaType.APPLICATION_JSON})
	List<HeartBeat> initial();
	
	@GET
	@Path("/captcha")
	@Produces({MediaType.APPLICATION_JSON})
	Captcha randomCaptcha();
	
	@POST
	@Path("/captcha")
	@Consumes({MediaType.APPLICATION_JSON})
	String receiveBidReq();
}
