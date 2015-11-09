package com.hat.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hat.bean.SortMode;
import com.hat.listviewsort_a_z.R;

public class MyAdapter extends BaseAdapter implements SectionIndexer {

	private Context mContext;
	private List<SortMode> mData;
	private LayoutInflater mInflater;

	public MyAdapter(Context context, List<SortMode> data){
		this.mContext = context;
		this.mData = data;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {

		return mData.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final SortMode sortMode = mData.get(position);
		if(convertView == null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_item, parent,false);
			holder.tv_letter = (TextView) convertView.findViewById(R.id.catlog);
			holder.tv_title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}

		int section = getSectionForPosition(position);//根据position获取分类首字母的char ascii值

		if(position == getPositionForSection(section)){
			holder.tv_letter.setVisibility(View.VISIBLE);
			holder.tv_letter.setText(sortMode.getSortLetters());
		}else{
			holder.tv_letter.setVisibility(View.GONE);
		}

		holder.tv_title.setText(sortMode.getName());

		return convertView;
	}

	public void updateListView(List<SortMode> list){
		this.mData = list;
		notifyDataSetChanged();
	}

	private final class ViewHolder{
		TextView tv_letter;
		TextView tv_title;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	/*
	 * 根据分类的首字母的char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int sectionIndex) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = mData.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if(firstChar == sectionIndex){
				return i;
			}
		}
		return -1;
	}

	/*
	 * 根据Listview当前位置获取分类首字母的char ascii值
	 */
	@Override
	public int getSectionForPosition(int position) {
		return mData.get(position).getSortLetters().charAt(0);
	}
}
