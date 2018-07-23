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
@Table(name = "role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Role {
	private String id;

	private String text;

	private String rank;

	private String type;

	private String serialnumber;
	private String rstatus;

	private Set<User> users;
	private Set<Jurisdiction> jurisdiction;

	public Role() {
		super();
	}

	public Role(String id, String text, String rank, String type,
			String serialnumber) {
		super();
		this.id = id;
		this.text = text;
		this.rank = rank;
		this.type = type;
		this.serialnumber = serialnumber;
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "rid", length = 64)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "rname", nullable = false, length = 10)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "rank", nullable = false, length = 10)
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "serialnumber", nullable = false, length = 20)
	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	@Column(name = "rstatus", length = 20)
	public String getRstatus() {
		return rstatus;
	}

	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "urrelation", joinColumns = { @JoinColumn(name = "rid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "uid", nullable = false, updatable = false) })
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rjrelation", joinColumns = { @JoinColumn(name = "rid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "jid", nullable = false, updatable = false) })
	public Set<Jurisdiction> getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(Set<Jurisdiction> jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

}