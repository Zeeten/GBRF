package com.ncs.bean;

import java.sql.Timestamp;
import java.util.Date;

public class RegisterPrintedBookBean extends BaseBean {

	private String bookName;
	private String bookId;
	private String mobileno;
	private String email;
	private String password;
	private Date date;
	private Boolean rlPartI;
	private Boolean rlPartII;
	public Boolean getRlPartI() {
		return rlPartI;
	}

	public void setRlPartI(Boolean rlPartI) {
		this.rlPartI = rlPartI;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getRlPartII() {
		return rlPartII;
	}

	public void setRlPartII(Boolean rlPartII) {
		this.rlPartII = rlPartII;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String getKey() {
	
		return bookId + "";
	}
	@Override
	public String getValue() {

		return bookName+ " "+bookId;
	}


}
