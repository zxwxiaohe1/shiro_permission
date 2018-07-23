package com.sinux.ssh.shrio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.sinux.ssh.common.utils.ConfigUtil;
import com.sinux.ssh.entity.ActiveUser;
import com.sinux.ssh.entity.Jurisdiction;
import com.sinux.ssh.entity.Role;
import com.sinux.ssh.entity.User;
import com.sinux.ssh.interceptor.SessionManager;
import com.sinux.ssh.service.UserService;

/**
 * Description:继承认证授权类
 */
public class CustomRealm extends AuthorizingRealm {

	// 注入service
	@Autowired
	private UserService userService;// 用户服务
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}

	/**
	 * Description: realm的认证方法，从数据库查询用户信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		String userCode = (String) token.getPrincipal();
		System.out.println("userCode=" + userCode);
		User user = null;
		String hql = "from User where loginname=:loginname";
		Map<String, Object> map = Maps.newHashMap();
		map.put("loginname", userCode);
		try {
			user = userService.findSysUserByUserCode(hql, map);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (user == null) {//
			return null;
		}
		String password = user.getPassword();
		String salt = user.getSalt();
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(user.getId());
		activeUser.setUsercode(user.getLoginname());
		activeUser.setUsername(user.getText());
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				activeUser, password, ByteSource.Util.bytes(salt),
				this.getName());
		
		return simpleAuthenticationInfo;
	}

	/**
	 * Description: 用于授权，从数据库查询用户权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();
		request.getSession().setAttribute(ConfigUtil.getSessionInfoName(),
				activeUser);// 将登陆用户信息存入Session
		User userAllAuthor = null;
		try {
			userAllAuthor = userService.findUserRoles(activeUser.getUserid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> permissions = new ArrayList<String>();

		if (userAllAuthor != null) {
			Set<Jurisdiction> author = new HashSet<Jurisdiction>();
			for (Role role : userAllAuthor.getRoles()) {
				author.addAll(role.getJurisdiction());
			}
			for (Jurisdiction jurisdiction : author) {
				permissions.add(jurisdiction.getAuthortnum());
			}
		}
		// 查到权限数据，返回授权信息
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissions);
		request.getSession().setAttribute("permissions", permissions);// 将登陆用户的所有权限存入Session供js授权
		return simpleAuthorizationInfo;
	}

	// 清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject()
				.getPrincipals();
		super.clearCache(principals);
	}
}
