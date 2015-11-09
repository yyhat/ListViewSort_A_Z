package com.hat.impl;


import com.hat.bean.SortMode;

import java.util.Comparator;

public class PinyinCompartor implements Comparator<SortMode> {

	@Override
	public int compare(SortMode lhs, SortMode rhs) {
		//这里主要是来对Listview里面的数据根据ABCDEFG...来排序
		if(rhs.getSortLetters().equals("#")){
			return -1;
		}else if(lhs.getSortLetters().equals("#")){
			return 1;
		}else{
			return lhs.getSortLetters().compareTo(rhs.getSortLetters());
		}
	}

}
