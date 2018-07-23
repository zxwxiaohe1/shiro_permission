package com.sinux.ssh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.sinux.ssh.Exception.LoginException;
import com.sinux.ssh.entity.Department;
import com.sinux.ssh.entity.User;
import com.sinux.ssh.interceptor.SessionManager;
import com.sinux.ssh.service.DepartmentService;
import com.sinux.ssh.service.UserService;

/**
 * 用途:处理用户请求updateUser
 * */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;// 用户服务
	@Autowired
	private DepartmentService departmentService;// 用户服务
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private SessionManager sessionManager;

	/**
	 * 用户身份验证
	 * 
	 * @param request
	 *            请求
	 * @return 登录失败字符串
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		sessionManager.clear=false;//关闭session销毁等待时间
		sessionManager.validateSessions();
		// 如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
		String exceptionClassName = (String) request
				.getAttribute("shiroLoginFailure");
		// 根据shiro返回的异常类路径判断，抛出指定异常信息
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(
					exceptionClassName)) {
				throw new LoginException("账号不存在");
			} else if (IncorrectCredentialsException.class.getName().equals(
					exceptionClassName)) {
				throw new LoginException("用户名/密码错误");
			} else {
				throw new Exception();
			}
		}
		// 登陆失败还到index页面
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 *            用户实体类
	 * @return 跳转页面字符串
	 */
	@RequestMapping(value = "/updateUser")
	public String updateUser(User user) throws Exception {
		String result = "user/userData";
		if (userService.updateUser(user)) {
			result = "user/userData";// 指定返回页面
		}
		return result;
	}

	/**
	 * 更据传入的为用户添加部门关系
	 * 
	 * @param 用户id
	 *            、部门id
	 * @return 页面字符串
	 */
	@ResponseBody
	@RequestMapping(value = "/savaUDrelations")
	public String savaUDrelations(@RequestParam(value = "uid") String uid,
			@RequestParam(value = "did") String[] did) {
		String result = "faild";
		if (userService.savaUDrelations(uid, did)) {// 判断保存用户部门关系是否成功
			result = "success";
		}
		return result;
	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 *            请求
	 * @return 登录失败字符串
	 */
	@ResponseBody
	@RequestMapping(value = "/register")
	public String register(User user) throws Exception {
		return userService.savaUser(user);
	}

	/**
	 * 分页查询用户信息
	 * 
	 * @param page当前页
	 *            ，rows请求的行数
	 * 
	 * @return map
	 */
	@RequestMapping(value = "/userlist")
	public @ResponseBody
	Object checkLogin(int page, int rows) {
		List<User> list = null;
		List<User> allUser = null;
		Map<String, Object> map = Maps.newHashMap();
		try {
			list = userService.findPageAllUser(page, rows);
			allUser = userService.findAllUserList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("total", allUser.size());
		map.put("rows", list);
		return map;
	}

	/**
	 * 获取指定用户的部门ID集
	 * 
	 * @param uid用户ID
	 * 
	 * @return departmentId部门Id集合
	 */
	@ResponseBody
	@RequestMapping(value = "/getUDid")
	public Object getUDid(String uid) {
		List<Department> departmentId = null;
		try {
			User user = userService.findUserInfo(uid);
			departmentId = new ArrayList<Department>(user.getDepartments());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departmentId;
	}

	/**
	 * 批量删除用户(删除用户、部门、角色关系)并将用户状态更改为失效状态
	 * 
	 * @param uids用户ID集
	 * 
	 * @return result返回成功或失败字符串提示
	 */
	@RequestMapping(value = "/deleteUser")
	public @ResponseBody
	String deleteUser(String[] uids) {
		String result = "faild";
		try {
			userService.deleteUser(uids);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 用户编辑
	 * 
	 * @param uid用户主键id
	 *            、address成功跳转页面、model封装用户信息
	 * 
	 */
	@RequestMapping(value = "/redactUser")
	public String redactUser(Model model, String address, String uid) {
		User user = null;
		try {
			user = userService.findUserInfo(uid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("user", user);
		return address;
	}

	/**
	 * 统一跳转方法
	 * 
	 * @param address指定跳转地址
	 * @return address指定跳转地址
	 */
	@RequestMapping(value = "/redirct")
	public String regist(String address) {
		return address;
	}
}
