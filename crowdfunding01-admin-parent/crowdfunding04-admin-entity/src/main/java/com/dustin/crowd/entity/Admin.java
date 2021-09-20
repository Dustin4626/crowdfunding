package com.dustin.crowd.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_admin database table.
 * 
 */
@Entity
@Table(name="t_admin")
@NamedQuery(name="Admin.findAll", query="SELECT a FROM Admin a")
public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="create_time")
	private String createTime;

	private String email;

	@Column(name="login_acct")
	private String loginAcct;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_pswd")
	private String userPswd;

	public Admin() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginAcct() {
		return this.loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPswd() {
		return this.userPswd;
	}

	public void setUserPswd(String userPswd) {
		this.userPswd = userPswd;
	}

}