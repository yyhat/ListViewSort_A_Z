package com.hat.listviewsort_a_z;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hat.adapter.MyAdapter;
import com.hat.bean.SortMode;
import com.hat.impl.PinyinCompartor;
import com.hat.widget.ClearEditText;
import com.hat.widget.SideBar;

public class MainActivity extends Activity {
	
	private ClearEditText mClearEditText;
	private SideBar mSideBar;
	private ListView mListView;
	private TextView mTextDialog;
	
	private MyAdapter mAdapter;

	private CharacterParser characterParser;
	private List<SortMode> data;

	private PinyinCompartor pinyiCompartor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		characterParser = CharacterParser.getInstance();
		pinyiCompartor = new PinyinCompartor();

		mClearEditText = (ClearEditText) this.findViewById(R.id.clearEditText);
		mSideBar = (SideBar) this.findViewById(R.id.sidebar);
		mListView = (ListView) this.findViewById(R.id.listView);
		mTextDialog = (TextView) this.findViewById(R.id.textDialog);
		mSideBar.setTextDialog(mTextDialog);
		mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingChanged(String s) {
				int position = mAdapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					mListView.setSelection(position);
				}
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this, data.get(position).getName(),
						Toast.LENGTH_SHORT).show();
			}
		});
		data = filledData(getResources().getStringArray(R.array.date));
		mAdapter = new MyAdapter(this, data);
		mListView.setAdapter(mAdapter);
		
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}

	private List<SortMode> filledData(String[] data) {
		List<SortMode> result = new ArrayList<SortMode>();
		for (int i = 0; i < data.length; i++) {
			SortMode sortMode = new SortMode();
			sortMode.setName(data[i]);
			String pinyin = characterParser.getSelling(data[i]);
			Log.i("test", "src: " + data[i] + ", " + pinyin);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			if (sortString.matches("[A-Z]")) {
				sortMode.setSortLetters(sortString.toUpperCase());
			} else {
				sortMode.setSortLetters("#");
			}
			result.add(sortMode);
		}
		return result;
	}

	/*
	 * 根据输入框里的值来过滤数据并更新Listv1iew
	 */
	private void filterData(String str) {
		List<SortMode> filterDataList = new ArrayList<SortMode>();

		Log.i("test", "search str=" + str);
		if (TextUtils.isEmpty(str)) {
			filterDataList = data;
		} else {
			filterDataList.clear();
			for (SortMode s : data) {
				String name = s.getName();
				Log.i("test", "filter name=" + name);
				if (name.toUpperCase().indexOf(str.toString().toUpperCase()) != -1
						|| characterParser.getSelling(name).toUpperCase()
								.startsWith(str.toString().toUpperCase())) {
					filterDataList.add(s);

				}
			}
		}
		Collections.sort(filterDataList,pinyiCompartor);
		mAdapter.updateListView(filterDataList);
	}

}