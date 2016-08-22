package com.example.phonehelper.bean;

public class PhoneInfo {
	private int  imageView;
	private String top;
	private String buttom;
	public PhoneInfo(int imageView, String top, String buttom) {
		this.imageView = imageView;
		this.top = top;
		this.buttom = buttom;
	}
	public int getImageView() {
		return imageView;
	}
	public void setImageView(int imageView) {
		this.imageView = imageView;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getButtom() {
		return buttom;
	}
	public void setButtom(String buttom) {
		this.buttom = buttom;
	}
	
}
