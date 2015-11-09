package com.hat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.hat.listviewsort_a_z.R;

public class SideBar extends View {

	private OnTouchingLetterChangedListener mListener;

	public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "#" };
	private int choose = -1;
	private Paint paint = new Paint();
	private TextView mTextDialog;

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点该表背景颜色
		int width = this.getWidth();
		int height = this.getHeight();
		int singleHeight = height / b.length;// 获取每一个字母的高度
		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.rgb(0, 255, 0));
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(singleHeight > 30 ? 30 : singleHeight);
			// 选中的状态
			if (i == choose) {
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半
			float xPosition = width / 2 - paint.measureText(b[i]) / 2;
			float yPosition = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPosition, yPosition, paint);
			paint.reset();
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mTextDialog.setVisibility(View.INVISIBLE);
		};
	};

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于b中的个数
		switch (action) {
			case MotionEvent.ACTION_UP:
				setBackgroundDrawable(new ColorDrawable(0x00000000));
				choose = -1;
				invalidate();
				if (mTextDialog != null) {
					handler.sendEmptyMessageDelayed(0, 1500);
				}
				break;

			default:
				setBackgroundResource(R.drawable.slidebar_background);
				if (oldChoose != c) {
					if (c >= 0 && c < b.length) {
						if (mListener != null) {
							mListener.onTouchingChanged(b[c]);
						}
						if (mTextDialog != null) {
							mTextDialog.setText(b[c]);
							mTextDialog.setVisibility(View.VISIBLE);
						}
						choose = c;
						invalidate();
					}
				}
				break;
		}
		return true;
	}

	public interface OnTouchingLetterChangedListener {
		void onTouchingChanged(String s);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener listener) {
		this.mListener = listener;
	}

	public void setTextDialog(TextView textDialog) {
		this.mTextDialog = textDialog;
	}
}