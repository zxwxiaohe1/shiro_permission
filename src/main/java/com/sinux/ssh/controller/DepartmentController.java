package com.sinux.ssh.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.sinux.ssh.entity.Department;
import com.sinux.ssh.pageModel.Tree;
import com.sinux.ssh.service.DepartmentService;
import com.sinux.ssh.service.UserService;

@Controller
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	private UserService userService;// 用户服务
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private HttpServletRequest request;

	/**
	 * 查询部门树
	 * 
	 * @param ranks部门等级
	 *            , dtype 部门类型
	 */
	@ResponseBody
	@RequestMapping(value = "/departmentTree")
	public List<Tree> departmentTree(String ranks, String dtype)
			throws Exception {
		List<Tree> departmentTree = null;
		if ((ranks != null && dtype != null) || (ranks != " " && dtype != " ")) {
			departmentTree = departmentService.findDepartmentTree(ranks, dtype);
		}
		return departmentTree;
	}

	/**
	 * 分页查询指定部门下的用户信息
	 * 
	 * @param ranks部门等级
	 *            , dtype 部门类型
	 */
	@ResponseBody
	@RequestMapping(value = "/finduUsersByDid")
	public Object finduUsersByDid(@RequestParam(value = "dids") String[] dids) {
		Map<String, Object> map = Maps.newHashMap();
		try {
			map.put("total", 12);
			map.put("rows", userService.findUserinfoByDid(dids));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 更据指定等级部门查询部门信息
	 * 
	 * @param ranks部门等级
	 */
	@ResponseBody
	@RequestMapping(value = "/findDepartmentByRank")
	public Object findDepartmentByRank(String ranks) {
		List<Department> department = null;
		if (ranks != null || ranks != " ") {
			department = departmentService.findDepartmentByRank(ranks);
		}
		return department;
	}

	/**
	 * 更据指定的父级Id查找直接子集部门信息
	 * 
	 * @param did部门id
	 *            ,ranks部门等级
	 */
	@RequestMapping(value = "/findDepartmentByDtype")
	public @ResponseBody
	Object findDepartmentByDtype(@RequestParam(value = "did") String did,
			@RequestParam(value = "ranks") String ranks) {
		List<Department> departments = null;
		if ((did != null && did != " ") || (ranks != null && ranks != " ")) {
			departments = departmentService.findDepartmentList(did, ranks);
		}
		return departments;
	}

	/**
	 * 更据id查询部门
	 * 
	 * @param did部门id
	 */
	@ResponseBody
	@RequestMapping(value = "/findDepartmentByDid")
	public Object findDepartmentByDid(String did) {
		// Department departments = null;
		if (did != null || did != " ") {
			return departmentService.findDepartment(did);
		} else {
			return null;
		}
	}
	/**
	 * 部门添加
	 * 
	 * @param did部门id
	 *            ,ranks部门等级
	 */
	@ResponseBody
	@RequestMapping(value = "/registDepartment")
	public String registDepartment(Department department) {
		String result="faild";
		try {
			departmentService.saveDepartment(department);
			result="success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 部门添加
	 * 
	 * @param prentId父级部门id
	 *            , text新添加的部门名称 ,部门等级
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/register")
	public String registDepartment(String prentId, String text, String level) {
		String result="faild";
		try {
			departmentService.saveDepartment(prentId, text, level);
			result="success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 跳转页面跳转
	 * 
	 * @param did部门id
	 *            ,ranks部门等级
	 */
	@RequestMapping(value = "/redirct")
	public String regist(String address) {
		System.out.println("address=" + address);
		return address;
	}
}
