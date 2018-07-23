package com.sinux.ssh.entity;

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

@Entity
@Table(name = "jurisdiction")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Jurisdiction {
	private String id;
	private String text;
	private String jstatus;
	private String type;
	private String authortnum;
	private Set<Role> roles;

	public Jurisdiction() {
		super();
	}

	public Jurisdiction(String id, String text, String jstatus, String type) {
		super();
		this.id = id;
		this.text = text;
		this.jstatus = jstatus;
		this.type = type;
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "jid", length = 64)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "jname", nullable = false, length = 20)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "jstatus", length = 2)
	public String getJstatus() {
		return jstatus;
	}

	public void setJstatus(String jstatus) {
		this.jstatus = jstatus;
	}

	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "authortnum", nullable = false, length = 20)
	public String getAuthortnum() {
		return authortnum;
	}

	public void setAuthortnum(String authortnum) {
		this.authortnum = authortnum;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rjrelation", joinColumns = { @JoinColumn(name = "jid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "rid", nullable = false, updatable = false) })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}