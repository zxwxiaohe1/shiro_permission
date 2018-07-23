package com.sinux.ssh.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sinux.ssh.dao.RoleDao;
import com.sinux.ssh.entity.Role;
import com.sinux.ssh.pageModel.DataGrid;
import com.sinux.ssh.pageModel.Tree;
import com.sinux.ssh.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;

	/**
	 * 保存一个role角色对象
	 * 
	 * @param boolean类型
	 * 
	 */
	@Override
	public boolean savaRole(Role role) {
		boolean result = false;
		try {
			roleDao.save(role);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 更据角色ID查询角色
	 * 
	 * @param rid角色ID
	 */
	@Override
	public Role findRole(String rid) throws Exception {
		return roleDao.get(Role.class, rid);
	}

	/**
	 * 获得角色树
	 * 
	 * @param ranks树的等级
	 *            、type树的zhonglei
	 * @return List<Object>对象集合
	 */
	@Override
	public List<Tree> findRoleTree(String ranks, String type) throws Exception {
		List<Tree> roleTree = new ArrayList<Tree>();
		String fistRoleHql = "FROM Role as r WHERE r.rank=:rank";
		String otherRankHql = "from Role as r where r.rank=:rank and r.type=:type";
		Map<String, Object> mapfirst = Maps.newHashMap();
		mapfirst.put("rank", ranks);
		List<Role> Roles = null;
		if (type == null || type.equals(null) || type == " ") {
			Roles = roleDao.find(fistRoleHql, mapfirst);
		} else {
			mapfirst.put("type", type);
			Roles = roleDao.find(otherRankHql, mapfirst);
		}
		if (Roles.size() == 0) {
			mapfirst.clear();
			mapfirst.put("rank", String.valueOf(Integer.parseInt(ranks)));
			mapfirst.put("type", type);
			List<Role> otherRoles = roleDao.find(otherRankHql, mapfirst);
			List<Tree> endTree = new ArrayList<Tree>();
			for (int i = 0; i < otherRoles.size(); i++) {
				endTree.add(i, new Tree());
				endTree.get(i).setId(otherRoles.get(i).getId());
				endTree.get(i).setText(otherRoles.get(i).getId());
			}
			return endTree;
		} else {
			for (int j = 0; j < Roles.size(); j++) {
				roleTree.add(j, new Tree());
				roleTree.get(j).setId(Roles.get(j).getId());
				roleTree.get(j).setText(Roles.get(j).getText());
				roleTree.get(j).setChildren(
						findRoleTree(
								String.valueOf(Integer.parseInt(ranks) + 1),
								Roles.get(j).getType()));
			}
			return roleTree;
		}
	}

	/**
	 * 分页查询角色数据
	 * 
	 * @param page当前页rows查询总行数
	 * @return List<Object>对象集合
	 */
	@Override
	public DataGrid<Role> findPageRole(int page, int rows)
			throws Exception {
		List<Role> listRoles = null;
		List<Role> pageRoles = null;
		String allRolesHql = "FROM Role AS r WHERE r.rstatus=:rstatus";
		String pageRolesHql = "FROM Role AS r WHERE r.rstatus='1'";
		Map<String, Object> map = null;
		DataGrid<Role> dataGrid = new DataGrid<Role>();
		if (page > 0 && rows > 0) {
			map = Maps.newHashMap();
			map.clear();
			map.put("rstatus", "1");
			listRoles = roleDao.find(allRolesHql, map);
			pageRoles = roleDao.find(pageRolesHql, page, rows);
		}
		dataGrid.setTotal(Long.valueOf(listRoles.size()));
		dataGrid.setRows(pageRoles);
		return dataGrid;
	}
}
