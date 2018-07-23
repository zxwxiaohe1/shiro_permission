package com.sinux.ssh.dao;

import com.sinux.ssh.entity.User;

/**
 * 用户操作dao层类
 * 
 * 其他DAO继承此类获取常用的数据库操作方法
 * 
 * @author liliangdong
 * 
 * @param <T>
 *            模型
 */
public interface UserDao extends BaseDaoI<User> {
	/**
	 * 删除用户信息
	 * 
	 * @param ids
	 *            删除用户的id数组
	 * @param hql
	 *            删除hql语句
	 * @return User 用户对象
	 */
	public void deleteUser(String[] ids, String[] hql) throws Exception;

}
