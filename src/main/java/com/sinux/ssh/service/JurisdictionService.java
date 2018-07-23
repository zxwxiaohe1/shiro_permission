package com.sinux.ssh.service;

import java.util.List;
import java.util.Set;

import com.sinux.ssh.entity.Jurisdiction;

/**
 * @描述:权限服务层接口定义方法
 * @业务:提供控制层调用的服务方法、调用Dao层接口方法
 * 
 * @author Mr.xiaohe
 * 
 * @param java基础类型
 * 
 */
public interface JurisdictionService {
	/**
	 * 保存一个对象
	 * 
	 * @param jurisdiction对象
	 */
	public void savaJurisdiction(Jurisdiction jurisdiction);

	/**
	 * 得到指定角色的所有权限
	 * 
	 * @param rid角色主键
	 * @return Set<Jurisdiction>角色的权限集合
	 */
	public Set<Jurisdiction> findRoleJurisdiction(String rid);

	/**
	 * 查询除了当前角色权限id的所有权限权限信息
	 * 
	 * @param String
	 *            rid
	 * @return List<Jurisdiction>角色的权限集合
	 */
	public Set<Jurisdiction> findOtherJurisdiction(String rid);

	/**
	 * 查询权限信息
	 * 
	 * @param String
	 *            rid
	 * @return List<Jurisdiction>角色的权限集合
	 */
	public List<Jurisdiction> findJurisdiction(String type, String jstatus);

	/**
	 * 更新role具有的权限(增、删)
	 * 
	 * @param authorId权限id
	 *            ，roleid角色id
	 * @return boolean
	 */
	public boolean updateRJrelation(String[] authorId, String roleid);
}
