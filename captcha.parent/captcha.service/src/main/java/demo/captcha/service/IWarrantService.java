package demo.captcha.service;

import demo.captcha.model.Warrant;

public interface IWarrantService {
	
	Warrant queryByID(int id);
	Warrant update(Warrant warrant);
	void deleteByID(int id);
	void generateWarrant(int count);
	Page<Warrant> findWithPage(int pageNumber);
}
