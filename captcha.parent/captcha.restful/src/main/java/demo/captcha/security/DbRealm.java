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

import demo.captcha.model.Client;
import demo.captcha.service.IClientService;

public class DbRealm extends AuthorizingRealm {

	private IClientService repository;
	public void setRepository(IClientService repository){ this.repository = repository; }
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String currentUsername = (String) super.getAvailablePrincipal(principals);
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		if(null != currentUsername){
			
			Client client = this.repository.queryByIP(currentUsername);
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
		Client client = this.repository.queryByIP(token.getUsername());
		if( null != client ){
			
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(client.getIp(), client.getCode(), this.getName());
			return authcInfo;
		}
		return null;
	}

}
