package demo.captcha.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.model.Ranking;
import demo.captcha.model.rank.CaptchaRank;
import demo.captcha.model.rank.RateRank;
import demo.captcha.model.rank.ScoreRank;
import demo.captcha.service.ICaptchaExamClientService;
import demo.captcha.service.IRankService;

public class TaskJob {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskJob.class);

	private ICaptchaExamClientService clientService;
	public void setClientService(ICaptchaExamClientService service){ this.clientService = service; }
	
	private IRankService rankService;
	public void setRankService(IRankService service){ this.rankService = service; }
	
	public void execute() {
		
		logger.info("Ranking execution triggered.....");
		
		List<Ranking> ranking = new ArrayList<Ranking>();
		
		List<CaptchaExamClient> rankingByQuality = this.clientService.rankingQuality();
		for(int i=0; i<rankingByQuality.size(); i++){
			CaptchaRank rank = new CaptchaRank();
			rank.setOwner(rankingByQuality.get(i));
			rank.setRank(i+1);
			rank.setTotal(rankingByQuality.get(i).getTotal());
			rank.setUpdateTime(new Date());
			ranking.add(rank);
		}
		
		List<CaptchaExamClient> rankingByRate = this.clientService.rankingRate();
		for(int i=0; i<rankingByRate.size(); i++){
			RateRank rank = new RateRank();
			rank.setOwner(rankingByRate.get(i));
			rank.setRank(i+1);
			rank.setCorrectRate(rankingByRate.get(i).getCorrectRate());
			rank.setUpdateTime(new Date());
			ranking.add(rank);
		}
		
		List<CaptchaExamClient> rankingByScore = this.clientService.rankingScore();
		for(int i=0; i<rankingByScore.size(); i++){
			
			ScoreRank rank = new ScoreRank();
			rank.setOwner(rankingByScore.get(i));
			rank.setRank(i+1);
			rank.setScore(rankingByScore.get(i).getTotalScore());
			rank.setUpdateTime(new Date());
			ranking.add(rank);
		}
		
		this.rankService.reset(ranking);
    } 
}
