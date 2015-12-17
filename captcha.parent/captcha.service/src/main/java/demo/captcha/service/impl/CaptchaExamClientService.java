package demo.captcha.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.ExamRecord;
import demo.captcha.model.Warrant;
import demo.captcha.security.CodeGen;
import demo.captcha.service.ICaptchaExamClientService;
import demo.captcha.service.Page;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<CaptchaExamClient> listAll() {
		
		return (List<CaptchaExamClient>)this.getSession().createQuery(
				"from CaptchaExamClient order by updateTime desc").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamRecord> queryRecordByHost(CaptchaExamClient client) {

		String hql = "from ExamRecord where client=:client order by updatetime desc";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("client", client);
		return query.list();
	}

	@Override
	public ExamRecord updateRecord(CaptchaExamClient client, ExamRecord record) {
		
		record.setClient(client);
		record.setUpdateTime(new Date());
		this.getSession().save(record);
		return record;
	}

	@Override
	public CaptchaExamClient save(CaptchaExamClient client) {
		
		Date now = new Date();
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, 1);
		
		client.setCode(CodeGen.genRandomNum(6));
		client.setUpdateTime(now);
		client.setExpireTime(calendar.getTime());
		this.getSession().save(client);
		return client;
	}

	@Override
	public Page<ExamRecord> queryRecordByHostWithPage(CaptchaExamClient client, int pageNumber) {
		
		Page<ExamRecord> page = new Page<ExamRecord>();
		page.setCurrentPage(pageNumber);
		List<ExamRecord> rtn = this.queryForList(
				"from ExamRecord where client=:client", 
				new String[]{"client"}, 
				new Object[]{client}, 
				page);
		page.setDataList(rtn);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	protected List<ExamRecord> queryForList(String hql, String[] params, Object[] values, Page<ExamRecord> page) {  
  	  
        Query query = this.getSession().createQuery(hql + " order by updatetime desc");
        Query queryCount = this.getSession().createQuery("select count(id) " + hql);
        for(int pos=0; pos<params.length; pos++){
        	query.setParameter(params[pos], values[pos]);
        	queryCount.setParameter(params[pos], values[pos]);
        }
        
        int totalCount = ((Long) queryCount.uniqueResult()).intValue();
        page.setTotalCount(totalCount);
        
        query.setFirstResult(page.getFirstIndex());  
        query.setMaxResults(page.getPageSize());
        
        return (List<ExamRecord>)query.list();
    }

}
