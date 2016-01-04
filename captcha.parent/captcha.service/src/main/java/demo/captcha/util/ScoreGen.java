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
			if(total < 200)
				return 0;
			if(total < 500)
				return 65;
			if(total < 1000)
				return 70;
			if(total < 2000)
				return 75;
			if(total < 3000)
				return 80;
			if(total < 4000)
				return 85;
			if(total < 5000)
				return 90;
			if(total < 6000)
				return 95;
			return 100;
		}
		
		@Override
		public boolean arriveAverage(CaptchaExamClient client){
			return client.getTotal() >= 200;
		}
	}
	
	static public class RateScore implements IScore{
		@Override
		public int score(CaptchaExamClient client){
			int scoreRate = (int)client.getCorrectRate() > 95 ? (int)client.getCorrectRate() : 0;
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
			
			int keyAvgCost = (int)client.getAvgCost()/1000;
			if(keyAvgCost < 3)
				return 100;
			if(keyAvgCost < 4)
				return 95;
			if(keyAvgCost < 5)
				return 90;
			if(keyAvgCost<6)
				return 85;
			if(keyAvgCost<7)
				return 80;
			if(keyAvgCost<8)
				return 75;
			if(keyAvgCost<9)
				return 70;
			if(keyAvgCost<10)
				return 65;
			return 60;
		}

		@Override
		public boolean arriveAverage(CaptchaExamClient client) {

			int keyAvgCost = (int)client.getAvgCost()/1000;
			return keyAvgCost < 11;
		}
	}
	
	static Map<Integer, Integer> CaptchaAvgCost;
	static Map<Integer, Integer> CaptchaTotal;
	static Map<Integer, String> comments;
	
	static {
		CaptchaAvgCost = new HashMap<Integer, Integer>();
		CaptchaAvgCost.put(0, 100);
		CaptchaAvgCost.put(1, 100);
		CaptchaAvgCost.put(2, 100);
		CaptchaAvgCost.put(3, 95);
		CaptchaAvgCost.put(4, 90);
		CaptchaAvgCost.put(5, 85);
		CaptchaAvgCost.put(6, 80);
		CaptchaAvgCost.put(7, 75);
		CaptchaAvgCost.put(8, 70);
		CaptchaAvgCost.put(9, 65);
		
		CaptchaTotal = new HashMap<Integer, Integer>();
		CaptchaTotal.put(60, 100);
		CaptchaTotal.put(50, 95);
		CaptchaTotal.put(40, 90);
		CaptchaTotal.put(30, 85);
		CaptchaTotal.put(20, 80);
		CaptchaTotal.put(10, 75);
		//小于1000,70分
		CaptchaTotal.put(9, 70);
		CaptchaTotal.put(8, 70);
		CaptchaTotal.put(7, 70);
		CaptchaTotal.put(6, 70);
		CaptchaTotal.put(5, 70);
		//小于500,65分
		CaptchaTotal.put(4, 65);
		CaptchaTotal.put(3, 65);
		CaptchaTotal.put(2, 65);
		
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
		
		client.setAvgCost((float)2990.5396);
		client.setCorrectRate((float)97.066666);
		client.setTotal(375);
		System.out.println(score(client));
	}
}
