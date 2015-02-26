package com.ncs.bean;

import java.sql.Timestamp;
import java.util.Date;

public class InviteBean extends BaseBean {

	private String name;
	private String email;
	private Timestamp date = new Timestamp(new Date().getTime());

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

}
