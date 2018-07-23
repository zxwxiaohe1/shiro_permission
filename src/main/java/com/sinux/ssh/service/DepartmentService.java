package com.sinux.ssh.service;

import java.util.List;

import com.sinux.ssh.entity.Department;
import com.sinux.ssh.pageModel.Tree;

/**
 * @描述:部门服务层接口定义方法
 * @业务:提供控制层调用的服务方法、调用Dao层接口方法
 * 
 * @author Mr.xiaohe
 * 
 * @param java基础类型
 * 
 */
public interface DepartmentService {
	/**
	 * 保存一个对象
	 * 
	 * @param Department对象
	 * @return boolean值
	 */
	public boolean saveDepartment(Department department) throws Exception;

	/**
	 * 保存一个对象
	 * 
	 * @param prentId父级部门id, text新添加的部门名称 ,部门等级
	 * @return boolean值
	 */
	public boolean saveDepartment(String prentId,String text,String level);

	/**
	 * 查询部门信息
	 * 
	 * @param did部门id
	 *            ，部门等级
	 * @return Department对象集合
	 */
	public List<Department> findDepartmentList(String did, String ranks);
	/**
	 * 更据ID查询部门信息
	 * 
	 * @param did部门id
	 * @return Department对象集合
	 */
	public Department findDepartment(String did);

	/**
	 * 分等级查询部门信息
	 * 
	 * @param hql部门查询语句
	 */
	public List<Department> findDepartmentByRank(String rank);

	/**
	 * 查询部门树
	 * 
	 * @param ranks部门等级
	 *            , dtype 部门类型
	 */
	public List<Tree> findDepartmentTree(String ranks, String dtype);

}
