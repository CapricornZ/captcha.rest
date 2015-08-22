package demo.captcha.rs;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import demo.captcha.model.Config;
import demo.captcha.model.Client;

@WebService(endpointInterface="demo.captcha.restful.ClientService", serviceName="ClientService")
@Path("/command/client")
public interface IClientService {

	@PUT
	@Path("/{HOST}")
	void modify(@PathParam("HOST")String host, Config config);
	
	@DELETE
	@Path("/{HOST}/operation/{OPSID}")
	void removeOperation(@PathParam("HOST")String host, @PathParam("OPSID")int opsID);
	
	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Client> listClient();
}
