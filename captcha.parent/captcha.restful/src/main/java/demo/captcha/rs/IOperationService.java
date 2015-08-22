package demo.captcha.rs;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import demo.captcha.model.BidStep1Operation;
import demo.captcha.model.BidStep2Operation;
import demo.captcha.model.LoginOperation;

@WebService(endpointInterface="demo.captcha.restful.OperationService", serviceName="OperationService")
@Path("/command/operation")
public interface IOperationService {
	
	@DELETE
	@Path("/{operationID}")
	void deleteOperation(@PathParam("operationID")int opsID);
	
	@POST
	@Path("/BIDStep1")
	void acceptBidStep1(BidStep1Operation bid);
	@PUT
	@Path("/BIDStep1/{operationID}")
	@Consumes({MediaType.APPLICATION_JSON})
	void modifyBidStep1(@PathParam("operationID")int opsID, BidStep1Operation bid);
	
	@POST
	@Path("/BIDStep2")
	@Consumes({MediaType.APPLICATION_JSON})
	void acceptBidStep2(BidStep2Operation bid);
	@PUT
	@Path("/BIDStep2/{operationID}")
	@Consumes({MediaType.APPLICATION_JSON})
	void modifyBidStep2(@PathParam("operationID")int opsID, BidStep2Operation bid);
	
	@PUT
	@Path("/Login/{operationID}")
	@Consumes({MediaType.APPLICATION_JSON})
	void modifyLogin(@PathParam("operationID")int opsID, LoginOperation login);
	
	@POST
	@Path("/{operationID}/hosts")
	void assignOperation(@PathParam("operationID")int opsID, String[] hosts);
}
