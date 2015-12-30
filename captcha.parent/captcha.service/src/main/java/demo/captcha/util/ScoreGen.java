package demo.captcha.util;

import java.util.HashMap;
import java.util.Map;

import demo.captcha.model.CaptchaExamClient;

public class ScoreGen {
	
	static Map<Integer, Integer> CaptchaAvgCost;
	static Map<Integer, Integer> CaptchaTotal;
	
	static {
		CaptchaAvgCost = new HashMap<Integer, Integer>();
		CaptchaAvgCost.put(0, 100);
		CaptchaAvgCost.put(1, 100);
		CaptchaAvgCost.put(2, 100);
		CaptchaAvgCost.put(3, 100);
		CaptchaAvgCost.put(4, 95);
		CaptchaAvgCost.put(5, 90);
		CaptchaAvgCost.put(6, 85);
		CaptchaAvgCost.put(7, 80);
		CaptchaAvgCost.put(8, 75);
		CaptchaAvgCost.put(9, 70);
		CaptchaAvgCost.put(10, 65);
		
		CaptchaTotal = new HashMap<Integer, Integer>();
		CaptchaTotal.put(60, 100);
		CaptchaTotal.put(50, 95);
		CaptchaTotal.put(40, 90);
		CaptchaTotal.put(30, 85);
		CaptchaTotal.put(20, 80);
		CaptchaTotal.put(10, 75);
		CaptchaTotal.put(5, 70);
		CaptchaTotal.put(2, 65);
	}
	
	public static int score(CaptchaExamClient client){
		
		int scoreRate = (int)client.getCorrectRate() > 95 ? (int)client.getCorrectRate() : 0;
		int scoreTotal, scoreAvgCost;
		int keyTotal = (int)client.getTotal()/100;
		if(CaptchaTotal.containsKey(keyTotal))
			scoreTotal = CaptchaTotal.get(keyTotal);
		else
			scoreTotal = 60;
		
		int keyAvgCost = (int)client.getAvgCost()/1000;
		if(CaptchaAvgCost.containsKey(keyAvgCost))
			scoreAvgCost = CaptchaAvgCost.get(keyAvgCost);
		else
			scoreAvgCost = 60;
		
		return scoreTotal + scoreAvgCost + scoreRate;
	}
}
