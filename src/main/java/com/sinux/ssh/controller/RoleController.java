package com.sinux.ssh.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinux.ssh.entity.Role;
import com.sinux.ssh.pageModel.Tree;
import com.sinux.ssh.service.RoleService;
import com.sinux.ssh.service.UserService;

/**
 * 用途:处理角色请求
 * */
@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;// 角色服务
	@Autowired
	private UserService userService;// 用户服务

	/**
	 * 获取指定用户的角色集
	 * 
	 * @param uid请求
	 * @return Role角色信息
	 */
	@RequestMapping(value = "/getRoles")
	public @ResponseBody
	Object getRoles(String uid) {
		List<Role> roleID = null;
		try {
			roleID = new ArrayList<Role>(userService.findUserRoles(uid)
					.getRoles());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleID;
	}

	/**
	 * 查询角色树
	 * 
	 * @param ranks请求的角色树等级
	 *            、type树的类型
	 * @return roleTree角色数组
	 */
	@ResponseBody
	@RequestMapping(value = "/roleTree")
	public List<Tree> roleTree(String ranks, String type) {
		List<Tree> roleTree = null;
		try {
			roleTree = roleService.findRoleTree(ranks, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleTree;
	}

	/**
	 * 得到某一部门下的所有用户
	 * 
	 * @param rid角色id
	 * @return User用户对象
	 */
	@RequestMapping("/getAllUserByRid")
	public @ResponseBody
	Object getAllUserByRid(String rid) throws Exception {
		Role role = null;
		role = roleService.findRole(rid);
		return role.getUsers();
	}

	/**
	 * 分页获取所有角色
	 * 
	 * @param page当前页
	 *            、rows页面展示行数
	 * @return map分页对象mapjihe
	 */
	@RequestMapping(value = "/getAllRoles")
	public @ResponseBody
	Object getAllRoles(int page, int rows) throws Exception {
		return roleService.findPageRole(page, rows);
	}

	/**
	 * 角色数据添加
	 * 
	 * @param role角色对象
	 * @return 保存对象提示
	 */
	@RequestMapping(value = "/register")
	public @ResponseBody
	String register(Role role) {
		String result = "failed";
		try {
			roleService.savaRole(role);
			result = "success";
		} catch (Exception e) {

		}
		return result;
	}

	/**
	 * 页面地址请求跳转
	 */
	@RequestMapping(value = "/redirct")
	public String regist(String address) {
		System.out.println("address=" + address);
		return address;
	}
}
