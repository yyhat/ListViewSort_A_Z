package com.hat.bean;

import android.util.Log;

public class SortMode {
	
	private String name; 		//名称
	private String sortLetters;	//排序字母  一个字母
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
		Log.i("test", "name =" + name + ", sortLetters=" + sortLetters);
	}
	
	

}
