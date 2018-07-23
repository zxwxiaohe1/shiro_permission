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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "department")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Department {
	private String id;

	private String text;

	private String dtype;

	private String rank;

	private String dstatus;

	private Set<User> users;

	public Department() {
		super();
	}

	public Department(String id, String text, String dtype, String rank,
			String dstatus) {
		super();
		this.id = id;
		this.text = text;
		this.dtype = dtype;
		this.rank = rank;
		this.dstatus = dstatus;
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "did", length = 64)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "dname", nullable = false, length = 20)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "dtype", nullable = false, length = 20)
	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	@Column(name = "rank", nullable = false, length = 10)
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Column(name = "dstatus", length = 2)
	public String getDstatus() {
		return dstatus;
	}

	public void setDstatus(String dstatus) {
		this.dstatus = dstatus;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "udrelation", joinColumns = { @JoinColumn(name = "did", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "uid", nullable = false, updatable = false) })
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}