package com.sinux.ssh.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sinux.ssh.dao.DepartmentDao;
import com.sinux.ssh.entity.Department;
import com.sinux.ssh.pageModel.Tree;
import com.sinux.ssh.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentDao departmentDao;

	/**
	 * 保存一个对象
	 * 
	 * @param Department对象
	 * @return boolean值
	 */
	@Override
	public boolean saveDepartment(Department department) {
		boolean result = false;
		try {
			departmentDao.save(department);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 保存一个对象
	 * 
	 * @param prentId父级部门id
	 *            , text新添加的部门名称 ,部门等级
	 * @return boolean值
	 */
	@Override
	public boolean saveDepartment(String prentId, String text, String level) {
		boolean result = false;
		try {
			Department department = null;
			Department newDepartment = null;
			if (level != "1") {
				department = departmentDao.get(Department.class, prentId);
			}
			newDepartment = new Department();
			newDepartment.setText(text);
			newDepartment.setRank(level);
			newDepartment.setDtype(department.getDtype());
			newDepartment.setDstatus("1");
			departmentDao.save(newDepartment);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询部门信息
	 * 
	 * @param did部门id
	 *            ，部门等级
	 * @return Department对象集合
	 */
	@Override
	public List<Department> findDepartmentByRank(String rank) {
		String hql = "from Department as d where d.rank=:rank";
		List<Department> departments = null;
		Map<String, Object> map = Maps.newHashMap();
		try {
			map.put("rank", rank);
			departments = departmentDao.find(hql, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departments;
	}

	/**
	 * 查询部门树
	 * 
	 * @param ranks部门等级
	 *            , dtype 部门类型
	 */
	@Override
	public List<Tree> findDepartmentTree(String ranks, String dtype) {
		List<Tree> departmentTree = new ArrayList<Tree>();
		String firstDHql = "FROM Department AS d WHERE d.rank=:rank ORDER BY d.id ASC ";
		String secondDHql = "FROM Department AS d WHERE d.rank=:rank AND d.dtype=:dtype ORDER BY d.id ASC";
		Map<String, Object> mapD = Maps.newHashMap();
		List<Department> firstDepartments = null;
		mapD.clear();
		mapD.put("rank", ranks);
		if (dtype == null || dtype.equals(null) || dtype == " ") {
			firstDepartments = departmentDao.find(firstDHql, mapD);
		} else {
			mapD.put("dtype", dtype);
			firstDepartments = departmentDao.find(secondDHql, mapD);
		}
		if (firstDepartments.size() == 0) {
			mapD.clear();
			mapD.put("rank", ranks);
			mapD.put("dtype", dtype);
			List<Department> child = departmentDao.find(secondDHql, mapD);
			List<Tree> trees = new ArrayList<Tree>(child.size());
			for (int i = 0; i < child.size(); i++) {
				trees.add(i, new Tree());
				trees.get(i).setId(child.get(i).getId());
				trees.get(i).setText(child.get(i).getText());
			}
			return trees;
		} else {
			for (int i = 0; i < firstDepartments.size(); i++) {
				departmentTree.add(i, new Tree());
				departmentTree.get(i).setId(firstDepartments.get(i).getId());
				departmentTree.get(i)
						.setText(firstDepartments.get(i).getText());
				departmentTree.get(i).setChildren(
						findDepartmentTree(
								String.valueOf(Integer.parseInt(ranks) + 1),
								firstDepartments.get(i).getDtype()));
			}
			return departmentTree;
		}
	}

	/**
	 * 查询部门信息
	 * 
	 * @param did部门id
	 *            ，ranks部门等级
	 * @return Department对象集合
	 */
	@Override
	public List<Department> findDepartmentList(String did, String ranks) {
		String hql = "from Department as d where d.dtype=:dtype and d.rank=:rank";
		List<Department> departments = null;
		Map<String, Object> map = Maps.newHashMap();
		try {
			map.put("rank", ranks);
			map.put("dtype", departmentDao.get(Department.class, did)
					.getDtype());
			departments = departmentDao.find(hql, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departments;
	}

	/**
	 * 更据id查询部门
	 * 
	 * @param did部门id
	 */
	@Override
	public Department findDepartment(String did) {
		if (!did.equals(null) || did != " ") {
			return departmentDao.get(Department.class, did);
		} else {
			return null;
		}
	}
}
