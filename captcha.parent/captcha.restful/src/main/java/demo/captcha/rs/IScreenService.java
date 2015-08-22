package demo.captcha.rs;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import demo.captcha.model.ScreenConfig;
import demo.captcha.rs.model.BidStep1;
import demo.captcha.rs.model.BidStep2;

@WebService(endpointInterface="demo.captcha.restful.ScreenService", serviceName="ScreenService")
@Path("/command/operation/screenconfig")
public interface IScreenService {

	@POST
	@Path("/BidStep1")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public String acceptStep1(@QueryParam("fromHost")String fromHost, BidStep1 bid);
	
	@POST
	@Path("/BidStep2")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public String acceptStep2(@QueryParam("fromHost")String fromHost, BidStep2 bid);
	
	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public List<ScreenConfig> listScreenConfig();
}
