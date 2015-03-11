package com.ncs.bean;

import java.io.Serializable;

public abstract class BaseBean implements Serializable, Comparable<BaseBean>, DropdownListBean { 

	protected  long id;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public int compareTo(BaseBean next) {
		return (int) (id - next.getId());
	}

}
