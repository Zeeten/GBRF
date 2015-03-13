package com.ncs.bean;

public class BooksBean extends BaseBean {

	private String bookName;
	private int noOfChapters;

	public int getNoOfChapters() {
		return noOfChapters;
	}

	public void setNoOfChapters(int noOfChapters) {
		this.noOfChapters = noOfChapters;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Override
	public String getKey() {
		return bookName +"";
	}

	@Override
	public String getValue() {
		return bookName;
	}
}
