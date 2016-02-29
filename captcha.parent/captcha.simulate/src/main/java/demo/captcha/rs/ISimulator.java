package demo.captcha.rs;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.Warrant;
import demo.captcha.rs.model.Captcha;
import demo.captcha.rs.model.HeartBeat;
import demo.captcha.simulate.CaptchaReq;

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
	
	@GET
	@Path("/captcha/random")
	@Produces({MediaType.APPLICATION_JSON})
	CaptchaReq random();
	
	@POST
	@Path("/captcha")
	@Consumes({MediaType.APPLICATION_JSON})
	String receiveBidReq();
	
	@POST
	@Path("/register/{HOST}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	CaptchaExamClient register(@PathParam("HOST")String host, Warrant warrant);
	
	@PUT
	@Path("/keepAlive/{HOST}")
	@Produces({MediaType.APPLICATION_JSON})
	CaptchaExamClient keepAlive(@PathParam("HOST")String host);
}
