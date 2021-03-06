package demo.captcha.rs;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import demo.captcha.model.Config;
import demo.captcha.rs.model.ClientHtml;

@WebService(endpointInterface="demo.captcha.restful.ClientService", serviceName="ClientService")
@Path("/command/client")
public interface IClientService {

	@PUT
	@Path("/{HOST}")
	void modify(@PathParam("HOST")String host, Config config);
	
	@PUT
	@Path("/memo/{memo}")
	@Consumes({MediaType.APPLICATION_JSON})
	void modify(String[] host, @PathParam("memo")String memo);
	
	@DELETE
	@Path("/{HOST}/operation/{OPSID}")
	void removeOperation(@PathParam("HOST")String host, @PathParam("OPSID")int opsID);
	
	@DELETE
	@Path("/{HOST}/config")
	void removeConfig(@PathParam("HOST")String host);
	
	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	public List<ClientHtml> listClient();
	
	@GET
	@Path("/filter/{HOST}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<ClientHtml> listClientFilter(@PathParam("HOST")String host);	
}
