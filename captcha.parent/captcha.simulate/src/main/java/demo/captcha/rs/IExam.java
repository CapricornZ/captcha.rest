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
import demo.captcha.model.ExamRecord;
import demo.captcha.model.Warrant;
import demo.captcha.rs.model.Captcha;
import demo.captcha.rs.model.HeartBeat;

@WebService(endpointInterface="demo.captcha.restful.ISimulator", serviceName="ISimulator")
@Path("/exam")
public interface IExam {
	
	@GET
	@Path("/client/{HOST}/record")
	@Produces({MediaType.APPLICATION_JSON})
	List<ExamRecord> queryRecord(@PathParam("HOST")String host);
	
	@POST
	@Path("/client/{HOST}/record")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	ExamRecord uploadRecord(@PathParam("HOST")String host, ExamRecord record);
	
	@POST
	@Path("/client")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	CaptchaExamClient createUser(CaptchaExamClient client);
	
}
