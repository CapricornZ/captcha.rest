package demo.captcha.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.Ranking;
import demo.captcha.service.IRankService;
import demo.captcha.service.Service;

public class RankService extends Service implements IRankService {

	@Override
	public void reset(List<Ranking> rankings) {
		
		Session session = this.getSession();
		Query deleteAll = session.createQuery("delete Ranking");
		deleteAll.executeUpdate();
		
		for(Ranking rank : rankings)
			session.save(rank);
	}

	@Override
	public List<Ranking> topCaptcha(int num) {

		String hql = "from CaptchaRank";
		Query query = this.getSession().createQuery(hql +  " order by rank");
		query.setMaxResults(num);
		return query.list();
	}

	@Override
	public List<Ranking> topRate(int num) {

		String hql = "from RateRank";
		Query query = this.getSession().createQuery(hql +  " order by rank");
		query.setMaxResults(num);
		return query.list();
	}

	@Override
	public List<Ranking> getRankByOwner(CaptchaExamClient owner) {

		String hql = "from Ranking where owner=:owner";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("owner", owner);
		return query.list();
	}
}
