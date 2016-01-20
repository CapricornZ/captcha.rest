package demo.captcha.rs;

import java.util.List;

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
import demo.captcha.rs.model.AssignmentV1;
import demo.captcha.rs.model.AssignmentV2;
import demo.captcha.rs.model.AssignmentV3;
import demo.captcha.rs.model.Trigger;
import demo.captcha.rs.model.TriggerV2;
import demo.captcha.rs.model.TriggerV3;

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
	@Path("/{BIDNO}/triggerV1")
	@Consumes(MediaType.APPLICATION_JSON)
	void assign(Trigger trigger, @PathParam("BIDNO")String bidNo);
	
	@PUT
	@Path("/{BIDNO}/triggerV2")
	@Consumes(MediaType.APPLICATION_JSON)
	void assign(TriggerV2 trigger, @PathParam("BIDNO")String bidNo);
	
	@PUT
	@Path("/{BIDNO}/triggerV3")
	@Consumes(MediaType.APPLICATION_JSON)
	void assign(TriggerV3 trigger, @PathParam("BIDNO")String bidNo);
	
	@DELETE
	@Path("/{BIDNO}/client")
	void unAssign(@PathParam("BIDNO")String bidNo);
	
	@POST
	@Path("/assignments/v3")
	@Consumes(MediaType.APPLICATION_JSON)
	void newAssignmentV3(List<AssignmentV3> assignments);
	
	@POST
	@Path("/assignments/v2")
	@Consumes(MediaType.APPLICATION_JSON)
	void newAssignmentV2(List<AssignmentV2> assignments);
	
	@POST
	@Path("/assignments/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	void newAssignmentV1(List<AssignmentV1> assignments);
}
