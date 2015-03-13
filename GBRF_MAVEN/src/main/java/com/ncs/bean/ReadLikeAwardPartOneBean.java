package com.ncs.bean;

import java.sql.Timestamp;

public class ReadLikeAwardPartOneBean extends BaseBean {

	private long bookId;
	private String bookName;
	private String bookNo;
	private int amount;
	private Timestamp awardDate;
	private Timestamp submitDate;
	private String firstName;
	private String lastName;
	private String email;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Timestamp getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(Timestamp awardDate) {
		this.awardDate = awardDate;
	}

	public Timestamp getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}

	@Override
	public String getKey() {

		return bookId +"";
	}

	@Override
	public String getValue() {

		return bookName;
	}


}
