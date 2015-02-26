package com.ncs.bean;

import java.sql.Timestamp;
import java.util.Date;

public class LikesBean extends BaseBean {

	private String email;
	private String bookName;
	private String bookNo;
	private String like1;
	private String like2;
	private String like3;
	private Timestamp date = new Timestamp(new Date().getTime());

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	public String getLike1() {
		return like1;
	}

	public void setLike1(String like1) {
		this.like1 = like1;
	}

	public String getLike2() {
		return like2;
	}

	public void setLike2(String like2) {
		this.like2 = like2;
	}

	public String getLike3() {
		return like3;
	}

	public void setLike3(String like3) {
		this.like3 = like3;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

}