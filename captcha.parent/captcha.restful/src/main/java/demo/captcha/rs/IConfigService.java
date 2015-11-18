package demo.captcha.rs;

import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import demo.captcha.model.Config;
import demo.captcha.rs.model.Trigger;

@WebService(endpointInterface="demo.captcha.restful.ConfigService", serviceName="ConfigService")
@Path("/command/config")
public interface IConfigService {

	@GET
	@Path("/{BIDNO}")
	@Produces({MediaType.APPLICATION_JSON})
	Config query(@PathParam("BIDNO")String bidNo);
	
	@DELETE
	@Path("/{BIDNO}")
	void delete(@PathParam("BIDNO")String bidNo);
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	void create(Config config, @QueryParam("client")String fromHost);
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	void modify(Config config);
	
	@PUT
	@Path("/{BIDNO}/client/{client}")
	@Consumes(MediaType.APPLICATION_JSON)
	void assign(Trigger trigger, @PathParam("BIDNO")String bidNo, @PathParam("client")String fromHost);
	
	@DELETE
	@Path("/{BIDNO}/client")
	void unAssign(@PathParam("BIDNO")String bidNo);
}
