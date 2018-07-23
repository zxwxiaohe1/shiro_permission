package com.sinux.ssh.service.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sinux.ssh.common.utils.AuthUtil;
import com.sinux.ssh.dao.DepartmentDao;
import com.sinux.ssh.dao.UserDao;
import com.sinux.ssh.entity.Department;
import com.sinux.ssh.entity.User;
import com.sinux.ssh.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private DepartmentDao departmentDao;

	/**
	 * 保存一个对象
	 * 
	 * @param User对象
	 * @return result保存成功或失败字符串
	 */
	@Override
	public String savaUser(User user) {
		String result = "faild";

		try {
			if (user != null) {
			}
			user.setPassword(AuthUtil.encryptByMd5("111111x"));
			userDao.save(user);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 所有用户分页查询
	 * 
	 * @param page第几页rows查询总行数
	 * @return List<User>对象集合
	 */
	@Override
	public List<User> findPageAllUser(int page, int rows) {
		String pageUser = "from User as U where ustatus='1'";
		if (page > 0 && rows > 0) {
			return userDao.find(pageUser, page, rows);
		} else {
			return null;
		}

	}

	/**
	 * 查询所有状态正常的用户信息
	 * 
	 * @return List<User>对象集合
	 */
	@Override
	public List<User> findAllUserList() {
		String pageUser = "from User as U where ustatus='1'";
		return userDao.find(pageUser);
	}

	/**
	 * 根据指定ID查询用户信息
	 * 
	 * @param uid用户主键唯一标识
	 * @return User对象集合
	 */
	@Override
	public User findUserInfo(String uid) throws Exception {
		if (!uid.equals(null) || !uid.equals(" ")) {
			return userDao.get(User.class, uid);
		} else {
			return null;
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param User对象集合
	 * @return boolean类型
	 */
	@Override
	public boolean updateUser(User user) {

		boolean update = false;
		try {
			if (user != null && !user.equals(null)) {
			User olduser=userDao.get(User.class, user.getId());
			olduser.setText(user.getText());
			olduser.setAge(user.getAge());
			olduser.setLoginname(user.getLoginname());
			olduser.setNumber(user.getNumber());
			olduser.setSalt(user.getId());
			olduser.setSerialnumber(user.getSerialnumber());
			userDao.update(olduser);
			update = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return update;
	}

	/**
	 * 批量删除用户信息
	 * 
	 * @param uids用户ID集合
	 * @return String类型
	 */
	@Override
	public void deleteUser(String[] uids) throws Exception {
		if (uids.length > 0) {
			for (int i = 0; i < uids.length; i++) {
				User user = userDao.get(User.class, uids[i]);
				user.setUstatus("0");
				List<Department> departments = new ArrayList<Department>(user.getDepartments());
				for (int j = 0; j < departments.size(); j++) {
					departments.get(j).getUsers().remove(user);
					user.getDepartments().remove(departments.get(j));
				}
				userDao.save(user);
			}
		}
	}

	/**
	 * 更新用户部门关系表数据状态
	 * 
	 * @param Map
	 *            <String, Object>params条件key—value值
	 * @return List<Udrelation>用户部门关系信息集合
	 */
	@Override
	public void updateUDStatus(String hql, Map<String, Object> params)
			throws Exception {
		if (hql != null && params != null) {
			userDao.executeHql(hql, params);
		}

	}

	/**
	 * 获得指定用户的角色集
	 * 
	 * @param uid
	 *            用户主键ID号
	 * @return User对象信息
	 */
	@SuppressWarnings("unused")
	@Override
	public User findUserRoles(String uid) throws Exception {
		if (uid != null || uid != " ") {
			return userDao.get(User.class, uid);
		} else {
			return null;
		}
	}

	/**
	 * 更据部门ID查询部门下的所有用户
	 * 
	 * @param dids字符串部门ID数组
	 */
	@Override
	public Set<User> findUserinfoByDid(String[] dids) {
		String hql = "FROM Department AS u WHERE u.id IN (:ids)";
		Set<User> listUser = new HashSet<User>();
		try {
			if (dids.length > 0 && hql != " ") {
				List<Department> department = departmentDao.get(dids, hql);
				for (int i = 0; i < department.size(); i++) {
					if (department.get(i).getUsers() != null) {
						listUser.addAll(department.get(i).getUsers());
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listUser;
	}

	/**
	 * 根据用户账号查询用户信息
	 * 
	 * @param User对象
	 */
	@Override
	public User findSysUserByUserCode(String hql, Map<String, Object> params)
			throws Exception {
		if (hql != " " && params != null) {
			return userDao.find(hql, params).get(0);
		} else {
			return null;
		}

	}

	/**
	 * 删除用户部门关系表数据
	 * 
	 * @param uid
	 *            用户主键唯一标识
	 * @param did
	 *            部门主键唯一标识
	 */
	@Override
	public void deleteUdrelation(String uid, String did) throws Exception {
		if ((!uid.equals(" ") || !uid.equals(null))
				&& (!did.equals(" ") || !did.equals(null))) {
			User user = userDao.get(User.class, uid);
			Department department = departmentDao.get(Department.class, did);
			department.getUsers().remove(user);
			user.getDepartments().remove(department);
			userDao.save(user);
		}
	}

	/**
	 * 新增用户部门关系表数据
	 * 
	 * @param uid
	 *            用户主键唯一标识
	 * @param did
	 *            部门主键唯一标识
	 */
	@Override
	public void addUdrelation(String uid, String did) throws Exception {
		if ((!uid.equals(" ") || !uid.equals(null))
				&& (!did.equals(" ") || !did.equals(null))) {
			User user = userDao.get(User.class, uid);
			Department department = departmentDao.get(Department.class, did);
			user.getDepartments().add(department);
			userDao.save(user);
		}

	}

	/**
	 * 新增用户部门关系表数据
	 * 
	 * @param uid
	 *            用户主键唯一标识
	 * @param did
	 *            部门主键唯一标识
	 */
	@Override
	public boolean savaUDrelations(String uid, String[] did) {
		boolean result = false;
		try {
			if ((!uid.equals(" ") || !uid.equals(null)) && did.length > 0) {
				User user = userDao.get(User.class, uid);
				String equalsNum = " ";
				List<Department> setUser = new ArrayList<Department>(user.getDepartments());
				if (user.getDepartments().size() > 0) {
					for (int i = 0; i < setUser.size(); i++) {// 对数据库原有的部门ID进行操作
						for (int j = 0; j < did.length; j++) {
							if (setUser.get(i).getId().equals(did[j])) {
								equalsNum += j;
								break;
							} else if (!setUser.get(i).getId().equals(did[j])
									&& j == did.length - 1) {
								deleteUdrelation(uid, setUser.get(i).getId());// 取消了，删除用户部门关系表数据
								break;
							}
						}
					}
					for (int k = 0; k < did.length; k++) {// 对前台页面传入的部门ID进行操作
						if ((equalsNum.indexOf(String.valueOf(k)) == -1)) {
							addUdrelation(uid, did[k]);// 为用户部门新添加的部门关系数据
						}
					}
				}
				if (user.getDepartments().size() == 0) {// 如果数据库里没有为用户分配部门
					for (int k = 0; k < did.length; k++) {// 直接对前台选中的部门ID进行保存
						addUdrelation(uid, did[k]);// 为用户部门新添加的部门关系数据
					}
				}

			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
