package demo.captcha.util;

import java.util.HashMap;
import java.util.Map;

import demo.captcha.model.CaptchaExamClient;

public class ScoreGen {
	
	interface IScore{
		int score(CaptchaExamClient client);
		boolean arriveAverage(CaptchaExamClient client);
	}
	
	static class TotalScore implements IScore{
		@Override
		public int score(CaptchaExamClient client){
			
			int total = client.getTotal();
			int minus = total >= 1000 ? 0 : total - 1000;
			minus = minus / 20;
			return (100 + minus) < 55 ? 0 : (100 + minus);
		}
		
		@Override
		public boolean arriveAverage(CaptchaExamClient client){
			return client.getTotal() >= 900;
		}
	}
	
	static public class RateScore implements IScore{
		@Override
		public int score(CaptchaExamClient client){
			int scoreRate = (int)client.getCorrectRate() > 89 ? (int)client.getCorrectRate() : 0;
			return scoreRate;
		}

		@Override
		public boolean arriveAverage(CaptchaExamClient client) {
			return (int)client.getCorrectRate() > 95;
		}
	}
	
	static public class CostScore implements IScore{
		@Override
		public int score(CaptchaExamClient client){
			
			int keyAvgCost = (int)client.getAvgCost()/100;
			int minus = keyAvgCost <= 30 ? 0 : (30 - keyAvgCost)/2;
			return (100 + minus)<65?0:(100 + minus);
		}

		@Override
		public boolean arriveAverage(CaptchaExamClient client) {

			int keyAvgCost = (int)client.getAvgCost()/1000;
			return keyAvgCost < 4;
		}
	}
	
	static Map<Integer, String> comments;
	
	static {
		
		comments = new HashMap<Integer, String>();
		comments.put(0, "还没进入状态？要努力练习才不会有遗憾！");
		comments.put(1, "你有努力练习，注意提高准确率&缩短平均耗时会更好哟。");
		comments.put(3, "我们知道你很努力。不要着急，确保准确再输入。");
		comments.put(7, "我们给你99分，留一分是怕你骄傲～！");
		comments.put(6, "增加练习次数，可以帮助你尽快提高综合分。");
		comments.put(4, "你的准确率很棒！要对自己有信心，多家练习，缩短平均耗时。");
	}
	
	public static String comment(CaptchaExamClient client){
		
		int total = 0;
		total += new RateScore().arriveAverage(client) ? 4 : 0;
		total += new TotalScore().arriveAverage(client) ? 1 : 0;
		total += new CostScore().arriveAverage(client) ? 2 : 0;
		if(comments.containsKey(total))
			return comments.get(total);
		return "";
	}
	
	public static int score(CaptchaExamClient client){
		
		/*
		int scoreRate = (int)client.getCorrectRate() > 95 ? (int)client.getCorrectRate() : 0;
		int scoreTotal, scoreAvgCost;
		int keyTotal = (int)client.getTotal()/100;
		if(CaptchaTotal.containsKey(keyTotal))
			scoreTotal = CaptchaTotal.get(keyTotal);
		else
			scoreTotal = 0;
		
		int keyAvgCost = (int)client.getAvgCost()/1000;
		if(CaptchaAvgCost.containsKey(keyAvgCost))
			scoreAvgCost = CaptchaAvgCost.get(keyAvgCost);
		else
			scoreAvgCost = 60;
		return scoreTotal + scoreAvgCost + scoreRate;
		*/
		int total = new TotalScore().score(client);
		total += new RateScore().score(client);
		total += new CostScore().score(client);
		return total;
	}
	
	public static void main(String[] args){
		
		CaptchaExamClient client = new CaptchaExamClient();
		//client.setAvgCost((float)3714.5396);
		//client.setCorrectRate((float)96.299034);
		//client.setTotal(1351);
		
		client.setAvgCost((float)2937.778);
		client.setCorrectRate((float)96.15115);
		client.setTotal(1429);
		System.out.println(score(client));
	}
}
