
package com.forezp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SysLog implements Serializable {

	private static final long serialVersionUID = 1769645390323012310L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id = 0L;
	// 用户名
	@Column
	private String username = null;
	// 用户操作
	@Column
	private String operation = null;
	// 请求方法
	@Column
	private String method = null;
	// 请求参数
	@Column
	private String params = null;
	// IP地址
	@Column
	private String ip = null;
	// 创建时间
	@Column
	private Date createDate = null;

	public SysLog() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}