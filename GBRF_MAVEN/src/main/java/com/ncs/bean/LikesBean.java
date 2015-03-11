package com.ncs.bean;

import java.sql.Timestamp;
import java.util.Date;

public class LikesBean extends BaseBean {

	private String email;
	private String bookName;
	private String bookNo;
	private int like1;
	private int like2;
	private int like3;
	private int like4;
	private int like5;
	private int like6;
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

public int getLike1() {
		return like1;
}

public void setLike1(int like1) {
		this.like1 = like1;
}

public int getLike2() {
		return like2;
}

public void setLike2(int like2) {
		this.like2 = like2;
}

public int getLike3() {
		return like3;
}

public void setLike3(int like3) {
		this.like3 = like3;
}

public int getLike4() {
	return like4;
}

public void setLike4(int like4) {
	this.like4 = like4;
}

public int getLike5() {
	return like5;
}

public void setLike5(int like5) {
	this.like5 = like5;
}

public int getLike6() {
	return like6;
}

public void setLike6(int like6) {
	this.like6 = like6;
}

public Timestamp getDate() {
		return date;
}

public void setDate(Timestamp date) {
		this.date = date;
}

	@Override
	public String getKey() {

		return id + "";
	}

	@Override
	public String getValue() {

		return bookNo +""+ bookName;
	}

}