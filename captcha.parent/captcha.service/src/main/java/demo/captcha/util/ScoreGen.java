package demo.captcha.util;

import java.util.HashMap;
import java.util.Map;

import demo.captcha.model.CaptchaExamClient;

public class ScoreGen {
	
	static class TotalScore{
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
	}
	
	static public class RateScore{
		
		public int score(CaptchaExamClient client){
			int scoreRate = (int)client.getCorrectRate() > 95 ? (int)client.getCorrectRate() : 0;
			return scoreRate;
		}
	}
	
	static public class CostScore{
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
	}
	
	static Map<Integer, Integer> CaptchaAvgCost;
	static Map<Integer, Integer> CaptchaTotal;
	
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
