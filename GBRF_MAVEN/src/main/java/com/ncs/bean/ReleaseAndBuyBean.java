package com.ncs.bean;

public class ReleaseAndBuyBean extends BaseBean {

	private int top10;
	private int amount;
	
	
	public int getTop10() {
		return top10;
	}

	public void setTop10(int top10) {
		this.top10 = top10;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return null;
	}


}
