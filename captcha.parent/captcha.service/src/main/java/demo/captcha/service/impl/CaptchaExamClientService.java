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
import demo.captcha.util.ScoreGen;

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

	@Override
	public long countRecordByHost(CaptchaExamClient client) {

		String hql = "select count(id) from ExamRecord where client=:client";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("client", client);
		long count=(Long)query.uniqueResult();
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamRecord> queryRecordByHost(CaptchaExamClient client) {

		String hql = "from ExamRecord where client=:client order by updatetime desc";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("client", client);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamRecord> queryRecordByHostDate(CaptchaExamClient client, String yyyyMM) {

		String[] array = yyyyMM.split("-");
		int year = Integer.parseInt(array[0]);
		int month = Integer.parseInt(array[1]);
		int endYear = year + (month+1>12 ? 1 : 0);
		int endMonth = month + 1 > 12 ? 1 : month;
		
		String hql = "from ExamRecord where client=:client and updateTime >= :begin and updateTime < :end order by updatetime desc";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("client", client);
		
		query.setParameter("begin", java.sql.Date.valueOf(String.format("%d-%02d-01", year, month)));
		query.setParameter("end", java.sql.Date.valueOf(String.format("%d-%02d-01", endYear, endMonth)));
		return query.list();
	}

	@Override
	public ExamRecord updateRecord(CaptchaExamClient client, ExamRecord record) {
		
		record.setClient(client);
		record.setUpdateTime(new Date());
		this.getSession().save(record);
		
		double cost = client.getTotal() * client.getAvgCost();
		cost += record.getCost();
		
		client.setTotal(client.getTotal()+1);
		client.addCorrect(record.getCorrect());

		client.setAvgCost((float)(cost/client.getTotal()));
		
		client.setTotalScore(ScoreGen.score(client));
		
		this.getSession().update(client);
		return record;
	}

	@Override
	public CaptchaExamClient save(CaptchaExamClient client) {
		
		Date now = new Date();
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, 3);
		
		//client.setCode(CodeGen.genRandomNum(6));
		client.setUpdateTime(now);
		client.setExpireTime(calendar.getTime());
		this.getSession().save(client);
		return client;
	}
	
	@Override
	public void save(List<CaptchaExamClient> clients) {
		
		Date now = new Date();
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, 3);
		
		for(CaptchaExamClient client : clients){

			client.setUpdateTime(now);
			client.setExpireTime(calendar.getTime());
			this.getSession().save(client);
		}
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

	public Page<CaptchaExamClient> rankingByQuality() {
		
		Page<CaptchaExamClient> page = new Page<CaptchaExamClient>();
		page.setPageSize(5);
		
		String hql = "from CaptchaExamClient";
		Query query = this.getSession().createQuery(hql +  " order by total desc");
        Query queryCount = this.getSession().createQuery("select count(host) " + hql);
        
        int totalCount = ((Long) queryCount.uniqueResult()).intValue();
        page.setTotalCount(totalCount);
        
        query.setFirstResult(page.getFirstIndex());  
        query.setMaxResults(page.getPageSize());
        
        page.setDataList((List<CaptchaExamClient>)query.list());
        
        return page;
	}

	public Page<CaptchaExamClient> rankingByRate() {
		
		Page<CaptchaExamClient> page = new Page<CaptchaExamClient>();
		page.setPageSize(5);
		
		String hql = "from CaptchaExamClient";
		Query query = this.getSession().createQuery(hql +  " order by correctRate desc");
        Query queryCount = this.getSession().createQuery("select count(host) " + hql);
        
        int totalCount = ((Long) queryCount.uniqueResult()).intValue();
        page.setTotalCount(totalCount);
        
        query.setFirstResult(page.getFirstIndex());  
        query.setMaxResults(page.getPageSize());
        
        page.setDataList((List<CaptchaExamClient>)query.list());
        
        return page;
	}

	@Override
	public List<CaptchaExamClient> rankingQuality() {
		
		String hql = "from CaptchaExamClient";
		Query query = this.getSession().createQuery(hql +  " order by total desc");
		return query.list();
	}
	
	@Override
	public List<CaptchaExamClient> rankingRate(){
		
		String hql = "from CaptchaExamClient";
		Query query = this.getSession().createQuery(hql +  " order by correctRate desc");
		return query.list();
	}
}