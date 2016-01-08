package demo.captcha.rs.model;

/***
 * 自定义策略V2 配置
 * @author martin
 *
 */
public class TriggerV2 implements ITrigger {

	private String category;
	private Trigger[] triggers;

	public Trigger[] getTriggers() { return triggers; }
	public void setTriggers(Trigger[] triggers) { this.triggers = triggers; }
	
	@Override
	public String getCategory() { return this.category; }
	@Override
	public void setCategory(String value) { this.category = value; }
}
