package demo.captcha.service;

import java.io.IOException;
import java.util.List;

import demo.captcha.model.Client;
import demo.captcha.model.Config;
import demo.captcha.rs.model.AssignmentV1;
import demo.captcha.rs.model.AssignmentV2;
import demo.captcha.rs.model.AssignmentV3;
import demo.captcha.rs.model.V3Common;

public interface IConfigService {
	
	Config saveOrUpdate(Config config);
	Config saveOrUpdate(Config config, Client client);
	Config removeClient(Config config);

	List<Config> listAll();
	void delete(String config);
	Config queryByNo(String no);
	
	void assignmentV1(List<AssignmentV1> assignments);
	void assignmentV2(List<AssignmentV2> assignments);
	void assignmentV3(List<AssignmentV3> assignments);
	
	V3Common getCommonV3();
	void setCommonV3(V3Common v3)  throws IOException;
}
