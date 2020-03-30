package com.lt.lxc.jpa.support;

/**
 * 排序查询对象
 * @author Fewstrong
 *
 */
public class DataQueryObjectSort implements DataQueryObject {
	
	protected String propertyName;
	
	protected boolean ascending = false;


	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

}
