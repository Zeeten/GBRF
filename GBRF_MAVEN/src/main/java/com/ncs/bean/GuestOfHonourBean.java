package com.ncs.bean;

import java.sql.Timestamp;
import java.util.Date;

public class GuestOfHonourBean extends BaseBean {

	private String name;
	private Timestamp date = new Timestamp(new Date().getTime());

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return name;
	}

}
