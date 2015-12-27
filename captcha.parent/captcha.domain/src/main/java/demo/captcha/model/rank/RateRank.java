package demo.captcha.model.rank;

import demo.captcha.model.Ranking;

public class RateRank extends Ranking {
	
	private float correctRate;

	public float getCorrectRate() { return correctRate; }
	public void setCorrectRate(float correctRate) { this.correctRate = correctRate; }
}
