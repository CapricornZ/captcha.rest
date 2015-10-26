package demo.captcha.service.impl;

import java.util.List;
import java.util.Random;

import org.hibernate.Query;

import demo.captcha.model.Status;
import demo.captcha.model.Warrant;
import demo.captcha.service.IWarrantService;
import demo.captcha.service.Page;
import demo.captcha.service.Service;

public class WarrantService extends Service implements IWarrantService {

	@Override
	public Warrant queryByID(int id) {

		Warrant warrant = (Warrant) this.getSession().get(Warrant.class, id);
		return warrant;
	}
	
	@Override
	public Warrant update(Warrant warrant) {
		this.getSession().update(warrant);
		return warrant;
	}

	@Override
	public void deleteByID(int id) {

		Warrant warrant = new Warrant();
		warrant.setId(id);
		this.getSession().delete(warrant);
	}
	
	@Override
	public Page<Warrant> findWithPage(int pageNumber){
		
		Page<Warrant> page = new Page<Warrant>();
		page.setCurrentPage(pageNumber);
		List<Warrant> rtn = this.queryForList(
				"from Warrant", 
				new String[]{}, 
				new Object[]{}, 
				page);
		page.setDataList(rtn);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Warrant> queryForList(String hql, String[] params, Object[] values, Page<Warrant> page) {  
  	  
        Query query = this.getSession().createQuery(hql);
        Query queryCount = this.getSession().createQuery("select count(id) " + hql);
        for(int pos=0; pos<params.length; pos++){
        	query.setParameter(params[pos], values[pos]);
        	queryCount.setParameter(params[pos], values[pos]);
        }
        
        int totalCount = ((Long) queryCount.uniqueResult()).intValue();
        page.setTotalCount(totalCount);
        
        query.setFirstResult(page.getFirstIndex());  
        query.setMaxResults(page.getPageSize());
        
        return (List<Warrant>)query.list();
    }
	
	public void generateWarrant(int count){
		
		for(int i=0; i<count; i++){
			Warrant warrant = new Warrant();
			warrant.setValidate(3);
			warrant.setStatus(Status.AVAILABLE);
			warrant.setCode(CodeGen.genRandomNum(6));
			this.getSession().save(warrant);
		}
	}

	static class CodeGen {

		static char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		/**
		 * 生成随机密码
		 *
		 * @param pwd_len 生成的密码的总长度
		 * @return 密码的字符串
		 */
		public static String genRandomNum(int pwd_len) {
			
			int i; // 生成的随机数
			int count = 0; // 生成的密码的长度
			StringBuffer pwd = new StringBuffer();
			Random r = new Random();
			while (count < pwd_len) {

				// 生成随机数，取绝对值，防止生成负数
				i = Math.abs(r.nextInt(str.length));
				if (i >= 0 && i < str.length) {
					pwd.append(str[i]);
					count++;
				}
			}
			return pwd.toString();
		}
	}
}
