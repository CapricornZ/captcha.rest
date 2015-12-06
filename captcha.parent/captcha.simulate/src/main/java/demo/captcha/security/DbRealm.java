package demo.captcha.security;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import demo.captcha.model.CaptchaExamClient;
import demo.captcha.service.ICaptchaExamClientService;

public class DbRealm extends AuthorizingRealm {

	private ICaptchaExamClientService repository;
	public void setRepository(ICaptchaExamClientService repository){ this.repository = repository; }
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String currentUsername = (String) super.getAvailablePrincipal(principals);
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		if(null != currentUsername){
			
			CaptchaExamClient client = this.repository.queryByHost(currentUsername);
			if(null != client){
				
				simpleAuthorInfo.addRole("user");
				return simpleAuthorInfo;
			}
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		System.out.println("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
		CaptchaExamClient client = this.repository.queryByHost(token.getUsername());
		if( null != client && new String(token.getPassword()).equals(client.getCode())){
			
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(client.getHost(), client.getCode(), this.getName());
			return authcInfo;
		}
		return null;
	}

}
