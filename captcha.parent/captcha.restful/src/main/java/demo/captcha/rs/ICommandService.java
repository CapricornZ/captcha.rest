package demo.captcha.rs;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.model.Warrant;
import demo.captcha.rs.model.GlobalConfig;

@WebService(endpointInterface="demo.captcha.restful.CommandService", serviceName="CommandService")
@Path("/command")
public interface ICommandService {
	
	@POST
	@Path("/keepAlive")
	@Produces({MediaType.APPLICATION_JSON})
	Client keepAlive(@QueryParam("ip")String fromHost,
			@QueryParam("ENV")String env);
	
	@GET
	@Path("/global/{CATEGORY}")
	@Produces({MediaType.APPLICATION_JSON})
	GlobalConfig queryResource(@QueryParam("fromHost")String fromHost, 
			@PathParam("CATEGORY")String category, @QueryParam("env")String env) throws Exception;
	
	@GET
	@Path("/bid/{BIDNO}")
	@Produces({MediaType.APPLICATION_JSON})
	Config query(@PathParam("BIDNO")String bidNo);
	
	@POST
	@Path("/register/{HOST}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	Client register(@PathParam("HOST")String host, Warrant warrant);
	
	@POST
	@Path("/warrant")
	int generateWarrant();
	
	@PUT
	@Path("/warrant")
	@Consumes({MediaType.APPLICATION_JSON})
	void updateWarrant(Warrant warrant);
}
