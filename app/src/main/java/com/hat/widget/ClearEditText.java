package com.hat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.hat.listviewsort_a_z.R;

@SuppressLint("ClickableViewAccessibility")
public class ClearEditText extends EditText implements OnFocusChangeListener,
		TextWatcher {
	
	private Drawable mClearDrawable;
	
	

	public ClearEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mClearDrawable = getCompoundDrawables()[2];
		if(mClearDrawable == null){
			mClearDrawable = getResources().getDrawable(R.drawable.emotionstore_progresscancelbtn);
			mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
			setClearIconVisible(false);
			setOnFocusChangeListener(this);
			addTextChangedListener(this);
		}
	}

	private void setClearIconVisible(boolean visible) {
		Drawable right = visible ? mClearDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(getCompoundDrawables()[2] != null){
			if(event.getAction() == MotionEvent.ACTION_UP){
				boolean touchAble = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) && event.getX() < (getWidth() - getPaddingRight());
				if(touchAble){
					this.setText("");
				}
			}	
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}

	@Override
	public void onTextChanged(CharSequence text, int start, int lengthBefore,
			int lengthAfter) {
		setClearIconVisible(text.length() > 0);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus){
			setClearIconVisible(getText().toString().length() > 0);
		}else{
			setClearIconVisible(false);
		}
	}

}
