package com.sinux.ssh.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestParam;

import com.sinux.ssh.entity.User;

/**
 * @描述:用户服务层接口定义方法
 * @业务:提供控制层调用的服务方法、调用Dao层接口方法
 * 
 * @author Mr.xiaohe
 * 
 * @param java基础类型
 * 
 */
public interface UserService {
	/**
	 * 保存一个对象
	 * 
	 * @param User对象
	 * @return 对象的ID
	 */
	public String savaUser(User user);

	/**
	 * 所有用户分页查询
	 * 
	 * @param page第几页rows查询总行数
	 * @return List<User>对象集合
	 */
	public List<User> findPageAllUser(int page, int rows);

	/**
	 * 查询所有状态正常的用户信息
	 * 
	 * @return List<User>对象集合
	 */
	public List<User> findAllUserList();

	/**
	 * 根据指定ID查询用户信息
	 * 
	 * @param uid用户主键唯一标识
	 * @return User对象集合
	 */
	public User findUserInfo(String uid) throws Exception;

	/**
	 * 更新用户信息
	 * 
	 * @param User对象集合
	 * @return boolean类型
	 */
	public boolean updateUser(User user);

	/**
	 * 删除用户信息
	 * 
	 * @param uids用户ID集合
	 * @return String类型
	 */
	public void deleteUser(String[] uids) throws Exception;

	/**
	 * 更新用户部门关系表数据状态
	 * 
	 * @param Map
	 *            <String, Object>params条件key—value值
	 * @return List<Udrelation>用户部门关系信息集合
	 */
	public void updateUDStatus(String hql, Map<String, Object> params)
			throws Exception;

	/**
	 * 删除用户部门关系表数据
	 * 
	 * @param uid
	 *            用户主键唯一标识
	 * @param did
	 *            部门主键唯一标识
	 */
	public void deleteUdrelation(String uid, String did) throws Exception;

	/**
	 * 新增用户部门关系表数据
	 * 
	 * @param uid
	 *            用户主键唯一标识
	 * @param did
	 *            部门主键唯一标识
	 */
	public void addUdrelation(String uid, String did) throws Exception;

	/**
	 * 获得指定用户的角色集
	 * 
	 * @param uid
	 *            用户主键ID号
	 * @return User对象信息
	 */
	public User findUserRoles(String uid) throws Exception;

	/**
	 * 更据部门ID查询部门下的所有用户
	 * 
	 * @param dids字符串部门ID数组
	 */
	public Set<User> findUserinfoByDid(String[] dids);

	/**
	 * 根据用户账号查询用户信息
	 * 
	 * @param User对象
	 */
	public User findSysUserByUserCode(String hql, Map<String, Object> params)
			throws Exception;

	/**
	 * 根据用户id、部门id为用户添加部门关系
	 * 
	 * @param boolean
	 */
	public boolean savaUDrelations(String uid, String[] did);
}
