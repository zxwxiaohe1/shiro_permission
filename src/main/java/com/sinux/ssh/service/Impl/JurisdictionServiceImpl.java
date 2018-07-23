package com.sinux.ssh.service.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import com.sinux.ssh.dao.JurisdictionDao;
import com.sinux.ssh.dao.RoleDao;
import com.sinux.ssh.entity.Jurisdiction;
import com.sinux.ssh.entity.Role;
import com.sinux.ssh.service.JurisdictionService;
import com.sinux.ssh.shrio.CustomRealm;

@Service
public class JurisdictionServiceImpl implements JurisdictionService {
	@Autowired
	private JurisdictionDao jurisdictionDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private CustomRealm customRealm;

	@Override
	public void savaJurisdiction(Jurisdiction jurisdiction) {
		jurisdictionDao.save(jurisdiction);
	}

	/**
	 * 得到指定角色的所有权限
	 * 
	 * @param rid角色主键
	 * @return Set<Jurisdiction>角色的权限集合
	 */
	@Override
	public Set<Jurisdiction> findRoleJurisdiction(String rid) {
		Set<Jurisdiction> jurisdictions = new HashSet<Jurisdiction>();
		if (rid != null && !rid.equals(null)) {
			jurisdictions
					.addAll(roleDao.get(Role.class, rid).getJurisdiction());
		}
		return jurisdictions;
	}

	/**
	 * 查询除了当前角色权限id的所有权限权限信息
	 * 
	 * @param String
	 *            rid
	 * @return List<Jurisdiction>角色的权限集合
	 */
	@Override
	public Set<Jurisdiction> findOtherJurisdiction(String rid) {
		Set<Jurisdiction> OtherJurisdictions = new HashSet<Jurisdiction>();
		String RoleNotAuthorHql = "FROM Jurisdiction AS j WHERE j.jstatus=:jstatus";
		Map<String, Object> map = Maps.newHashMap();
		map.clear();
		map.put("jstatus", "1");
		try {
			List<Jurisdiction> roleJurisdiction = new ArrayList<Jurisdiction>(
					roleDao.get(Role.class, rid).getJurisdiction());
			List<Jurisdiction> Jurisdictions = jurisdictionDao.find(
					RoleNotAuthorHql, map);
			for (int i = 0; i < roleJurisdiction.size(); i++) {
				if (Jurisdictions.contains(roleJurisdiction.get(i))) {
					Jurisdictions.remove(roleJurisdiction.get(i));
				}
			}
			OtherJurisdictions.addAll(Jurisdictions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return OtherJurisdictions;
	}

	/**
	 * 查询权限信息
	 * 
	 * @param String
	 *            rid
	 * @return List<Jurisdiction>角色的权限集合
	 */
	@Override
	public List<Jurisdiction> findJurisdiction(String type, String jstatus) {
		// Set<Jurisdiction> Jurisdictions = new HashSet<Jurisdiction>();
		List<Jurisdiction> Jurisdictions = new ArrayList<Jurisdiction>();
		String hql = "from Jurisdiction where type=:type and jstatus=:jstatus order by id desc";
		Map<String, Object> map = Maps.newHashMap();
		map.put("type", type);
		map.put("jstatus", jstatus);
		try {
			if (type != null && jstatus != " ") {
				Jurisdictions.addAll(jurisdictionDao.find(hql, map));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Jurisdictions;
	}

	/**
	 * 更新role具有的权限(增、删)
	 * 
	 * @param authorId权限id
	 *            ，roleid角色id
	 * @return boolean
	 */
	@Override
	public boolean updateRJrelation(String[] authorId, String roleid) {
		boolean result = false;
		String eqJid = "";
		Map<String, Object> map = Maps.newHashMap();
		map.clear();
		map.put("rid", roleid);
		Role role = roleDao.get(Role.class, roleid);
		List<Jurisdiction> jurisdictions = new ArrayList<Jurisdiction>(
				role.getJurisdiction());
		try {
			if (authorId.length != 0 && jurisdictions.size() != 0) {
				for (int k = 0; k < jurisdictions.size(); k++) {
					for (int j = 0; j < authorId.length; j++) {
						if (jurisdictions.get(k).getId().equals(authorId[j])) {
							eqJid += authorId[j];
							break;
						} else if (!jurisdictions.get(k).getId()
								.equals(authorId[j])
								&& j == (authorId.length - 1)) {// 删除
							jurisdictions.get(k).getRoles().remove(role);
							role.getJurisdiction().remove(jurisdictions.get(k));
							roleDao.save(role);
						}
					}
				}
				for (int i = 0; i < authorId.length; i++) {
					if (eqJid.indexOf(String.valueOf(authorId[i])) == -1) {// 保存
						Jurisdiction Jurisdiction = jurisdictionDao.get(
								Jurisdiction.class, authorId[i]);
						role.getJurisdiction().add(Jurisdiction);
						roleDao.save(role);

					}
				}
			} else if (authorId.length == 0 && jurisdictions.size() != 0) {
				for (int k = 0; k < jurisdictions.size(); k++) {
					jurisdictions.get(k).getRoles().remove(role);
					role.getJurisdiction().remove(jurisdictions.get(k));// 删除
					roleDao.save(role);
				}

			} else if (authorId.length != 0 && jurisdictions.size() == 0) {
				for (int j = 0; j < authorId.length; j++) {
					Jurisdiction Jurisdiction = jurisdictionDao.get(
							Jurisdiction.class, authorId[j]);
					role.getJurisdiction().add(Jurisdiction);
					roleDao.save(role);// 保存
				}

			}
			customRealm.clearCached();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
