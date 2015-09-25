package demo.captcha.rs;

import javax.jws.WebService;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import demo.captcha.rs.model.OrcConfig;
import demo.captcha.rs.model.OrcTipConfig;

@WebService(endpointInterface="demo.captcha.restful.ResourceService", serviceName="ResourceService")
@Path("/command/resource")
public interface IResourceService {

	@PUT
	@Path("/{CATEGORY}/orc/tip/{ID}")
	void modifyTipConfig(OrcTipConfig config, @PathParam("CATEGORY")String category, @PathParam("ID")String id);
	
	@PUT
	@Path("/{CATEGORY}/orc/{ID}")
	void modifyConfig(OrcConfig config, @PathParam("CATEGORY")String category, @PathParam("ID")String id);
}
