package com.ncs.bean;

public class BooksBean extends BaseBean {

	private String bookName;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Override
	public String getKey() {
		return id +"";
	}

	@Override
	public String getValue() {
		return bookName;
	}
}
