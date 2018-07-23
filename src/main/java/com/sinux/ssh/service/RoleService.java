package com.sinux.ssh.service;

import java.util.List;
import java.util.Map;

import com.sinux.ssh.entity.Role;
import com.sinux.ssh.pageModel.DataGrid;
import com.sinux.ssh.pageModel.Tree;

/**
 * @描述:角色服务层接口定义方法
 * @业务:提供控制层调用的服务方法、调用Dao层接口方法
 * 
 * @author Mr.xiaohe
 * 
 * @param java基础类型
 * 
 */
public interface RoleService {
	/**
	 * 保存一个role角色对象
	 * 
	 * @param boolean类型
	 * 
	 */
	public boolean savaRole(Role role) throws Exception;

	/**
	 * 更据角色ID查询角色
	 * 
	 * @param rid角色ID
	 */
	public Role findRole(String rid) throws Exception;

	/**
	 * 获得角色树
	 * 
	 * @param ranks树的等级
	 *            、type树的zhonglei
	 * @return List<Object>对象集合
	 */
	public List<Tree> findRoleTree(String ranks, String type)
			throws Exception;

	/**
	 * 分页查询角色数据
	 * 
	 * @param page当前页rows查询总行数
	 * @return List<Object>对象集合
	 */
	public DataGrid<Role> findPageRole(int page, int rows)
			throws Exception;
}
