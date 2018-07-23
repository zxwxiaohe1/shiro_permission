package com.sinux.ssh.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@DynamicInsert(true)
@DynamicUpdate(true)
public class User {
	private String id;
	private int age;
	private String text;
	private String number;
	private String loginname;
	private String password;
	private String serialnumber;
	private String ustatus;
	private String salt;
	
	private Set<Department> departments;
	
	private Set<Role> roles;

	public User() {
	}

	public User(String id, int age, String text, String number,
			String loginname, String password, String serialnumber,
			String ustatus) {
		super();
		this.id = id;
		this.age = age;
		this.text = text;
		this.number = number;
		this.loginname = loginname;
		this.password = password;
		this.serialnumber = serialnumber;
		this.ustatus = ustatus;
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "uid", length = 64)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "age", nullable = false, length = 11)
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Column(name = "name", nullable = false, length = 10)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "number", nullable = false, length = 20)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "loginname", length = 20)
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "password", length = 100, updatable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "serialnumber", nullable = false, length = 20)
	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	@Column(name = "ustatus", length = 2)
	public String getUstatus() {
		return ustatus;
	}

	public void setUstatus(String ustatus) {
		this.ustatus = ustatus;
	}

	@Column(name = "salt", length = 20,updatable = false)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	// 用户、部门多对多映射
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "udrelation", joinColumns = { @JoinColumn(name = "uid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "did", nullable = false, updatable = false) })
	public  Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	// 用户、角色多对多映射
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "urrelation", joinColumns = { @JoinColumn(name = "uid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "rid", nullable = false, updatable = false) })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
