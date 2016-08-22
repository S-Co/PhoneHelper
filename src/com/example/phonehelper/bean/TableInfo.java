package com.example.phonehelper.bean;

public class TableInfo {
	private String name;
	private long number;

	public String getName() {
		return name;
	}

	public TableInfo(String name, long number) {
		this.name = name;
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

}
