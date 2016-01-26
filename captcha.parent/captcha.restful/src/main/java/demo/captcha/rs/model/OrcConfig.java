package demo.captcha.rs.model;

import org.codehaus.jackson.annotate.JsonIgnore;

public class OrcConfig implements IOrcConfig {
	
	private int[] index;
	private int offsetY;
	private int offsetX;
	private int width, height;
	private int minNearSpots;

	public int[] getIndex() { return index; }
	public void setIndex(int[] offsetX) { this.index = offsetX; }
	
	@JsonIgnore
	public String getRenderIndex(){
		
		StringBuilder sb = new StringBuilder();
		sb.append(this.index[0]);
		for(int i=1; i<this.index.length; i++)
			sb.append(String.format(",%d", this.index[i]));
		return sb.toString();
	}
	
	public int getOffsetX() { return offsetX; }
	public void setOffsetX(int offsetX) { this.offsetX = offsetX; }
	
	public int getOffsetY() { return offsetY; }
	public void setOffsetY(int offsetY) { this.offsetY = offsetY; }
	
	public int getWidth() { return width; }
	public void setWidth(int width) { this.width = width; }
	
	public int getHeight() { return height; }
	public void setHeight(int height) { this.height = height; }
	
	public int getMinNearSpots() { return minNearSpots; }
	public void setMinNearSpots(int minNearSpots) { this.minNearSpots = minNearSpots; }
	
	@Override
	@JsonIgnore
	public String getCategory() {
		return "OrcConfig";
	}
}
