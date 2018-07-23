package com.sinux.ssh.controller;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinux.ssh.entity.Jurisdiction;
import com.sinux.ssh.service.DepartmentService;
import com.sinux.ssh.service.JurisdictionService;
import com.sinux.ssh.service.RoleService;
import com.sinux.ssh.service.UserService;

/**
 * 用途:处理权限请求
 * */
@Controller
@RequestMapping("/jurisdiction")
public class JurisdictionController {
	@Autowired
	private UserService userService;// 用户服务
	@Autowired
	private DepartmentService departmentService;// 用户服务
	@Autowired
	private JurisdictionService jurisdictionService;// 权限服务
	@Autowired
	private RoleService roleService;
	@Autowired
	private HttpServletRequest request;

	/**
	 * 得到指定角色的所有权限
	 * 
	 * @param rid角色主键
	 * @return Set<Jurisdiction>角色的权限集合
	 */
	@RequestMapping(value = "/getRoleAuthor")
	public @ResponseBody
	Object getRoleAuthor(String rid) {
		Set<Jurisdiction> jurisdictions = null;
		try {
			jurisdictions = jurisdictionService.findRoleJurisdiction(rid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jurisdictions;
	}

	/**
	 * 查询除了当前角色权限id的所有权限权限信息
	 * 
	 * @param rid角色id
	 * @return Set<Jurisdiction>角色的权限集合
	 */
	@RequestMapping(value = "/getAllauthors")
	public @ResponseBody
	synchronized Object getAllauthors(String rid) {
		Set<Jurisdiction> OtherJurisdictions = null;
		if (rid != null || rid != " ") {
			OtherJurisdictions = jurisdictionService.findOtherJurisdiction(rid);
		}
		return OtherJurisdictions;
	}

	/**
	 * 调整角色的权限
	 * 
	 * @param authorId权限id
	 *            、roleid角色id
	 * @return String类型
	 */
	@RequestMapping(value = "/saverRJrelation")
	public @ResponseBody
	String saverRJrelation(String[] authorId, String roleid) {
		String result = "faild";
		if (authorId.length > 0 && (roleid != null || roleid != " ")) {
			if (jurisdictionService.updateRJrelation(authorId, roleid)) {
				result = "success";
			}
		}
		return result;
	}

	/**
	 * 查询所有状态正常的菜单项
	 * 
	 * @return Set<Jurisdiction>角色的权限集合
	 */
	@ResponseBody
	@RequestMapping(value = "/findMenu")
	public Object findMenu() {
		return jurisdictionService.findJurisdiction("menu", "1");
	}

	/* 权限添加 */
	@RequestMapping(value = "/register")
	public @ResponseBody
	String register(Jurisdiction jurisdiction) {
		String result = "failed";
		try {
			jurisdictionService.savaJurisdiction(jurisdiction);
			result = "success";
		} catch (Exception e) {

		}
		return result;
	}

	/* 跳转 */
	@RequestMapping(value = "/redirct")
	public String regist(String address) {
		System.out.println("address=" + address);
		return address;
	}
}
