package com.sinux.ssh.dao.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sinux.ssh.dao.UserDao;
import com.sinux.ssh.entity.User;

@Repository
public class UserImpl extends BaseDaoImpl<User> implements UserDao {
	@Override
	public void deleteUser(String[] ids, String[] hql) throws Exception {
		for (int i = 0; i < hql.length; i++) {
			System.out.println("hql["+i+"]="+hql[i]);
			Query query = getCurrentSession().createQuery(hql[i]);// 防止SQL注入
			query.setParameterList("uids", ids).executeUpdate();
			System.out.println("dfgdfdhfg");
		}
       
	}
}
