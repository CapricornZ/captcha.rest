package demo.captcha.rs;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import demo.captcha.model.Client;
import demo.captcha.rs.model.GlobalConfig;

@WebService(endpointInterface="demo.captcha.restful.CommandService", serviceName="CommandService")
@Path("/command")
public interface ICommandService {
	
	@POST
	@Path("/keepAlive")
	@Produces({MediaType.APPLICATION_JSON})
	Client keepAlive(@QueryParam("ip")String fromHost);
	
	@GET
	@Path("/global")
	@Produces({MediaType.APPLICATION_JSON})
	GlobalConfig queryResource(@QueryParam("fromHost")String fromHost);
}
