package demo.captcha.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.Warrant;
import demo.captcha.service.ICaptchaExamClientService;
import demo.captcha.service.Service;

public class CaptchaExamClientService extends Service implements ICaptchaExamClientService {

	@Override
	public CaptchaExamClient queryByHost(String host) {
		
		String hql = "from CaptchaExamClient where host=:host";
    	Query query = this.getSession().createQuery(hql);
    	query.setParameter("host", host);
    	
    	@SuppressWarnings("unchecked")
    	List<CaptchaExamClient> list = query.list();
    	return list.size()>0 ? list.get(0) : null;
	}

	@Override
	public CaptchaExamClient register(String host, Warrant warrant) {
		
		String hql = "from Warrant where code=?";
		Query query = this.getSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		//List<Warrant> warrants = query.setParameter("code", (String)warrant).list();
		List<Warrant> warrants = query.setParameter(0, warrant.getCode()).list();
		if(null != warrants && warrants.size() > 0){
			if(!warrants.get(0).getCode().equals(warrant.getCode()))
				return null;
		} else 
			return null;
		
		Warrant pWarrant = warrants.get(0);
		CaptchaExamClient pClient = (CaptchaExamClient) this.getSession().get(CaptchaExamClient.class, host);
		
		Date now = new Date();
		if( null == pClient ){
			
			pClient = new CaptchaExamClient();
			pClient.setHost(host);
			pClient.setCode(pWarrant.getCode());
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(now);
			calendar.add(Calendar.MONTH, pWarrant.getValidate());
			pClient.setExpireTime(calendar.getTime());
			pClient.setUpdateTime(now);
			
			this.getSession().delete(pWarrant);
			this.getSession().save(pClient);
		} else {
			
			pClient.setCode(pWarrant.getCode());
			pClient.setUpdateTime(now);
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(now);
			calendar.add(Calendar.MONTH, pWarrant.getValidate());
			pClient.setExpireTime(calendar.getTime());
			
			this.getSession().delete(pWarrant);
			this.getSession().update(pClient);
		}
		return pClient;
	}

	@Override
	public CaptchaExamClient update(CaptchaExamClient client) {

		this.getSession().update(client);
		return client;
	}

}
