package com.wsh.fingerwords.wegdit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wsh.fingerwords.R;
import com.wsh.fingerwords.util.CheckNetWork;

public class PanelView extends RelativeLayout {

	private Context mContext;
	private LayoutInflater inflater;
	private String selectText;
	
	public WindowManager manager;
	public WindowManager.LayoutParams params;
	public RelativeLayout bgLayout;// �������Ƶ������������ʧ�����
	public WordCard wordCard;// ����չʾ���ݵ����
	

	public PanelView(Context context,String selectText){
		super(context);
		mContext = context;
		this.selectText=selectText;
		ini();
		iniWindowManager();
		
	}
	
	public PanelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		ini();
		iniWindowManager();
		// TODO Auto-generated constructor stub
	}
	
	public PanelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		ini();
		iniWindowManager();
	}

	public void ini() {
		inflater = ((Activity) mContext).getLayoutInflater();
		// ����viewinflate�����view�ĸ��ؼ�
		inflater.inflate(R.layout.wordpanel, this);
		getView();
		setView();
	}

	public void iniWindowManager() {
		manager = (WindowManager) mContext.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		params = new WindowManager.LayoutParams();
		// ��һ��ǳ���Ҫ���������ñ���͸��
		params.format = PixelFormat.RGBA_8888; // ����ͼƬ��ʽ��Ч��Ϊ����͸��
		// params.alpha=0.8f;//�������ô��屾���͸����

		// params.gravity = Gravity.CENTER;
		params.gravity = Gravity.FILL;
		params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
				| WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
				| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
				| WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
				| WindowManager.LayoutParams.FLAG_FULLSCREEN;
		// // ����window type,�˿ؼ�λ�ڴ�绰����һ��
		// �����������õĹؼ��ĵط�
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

		// params.x = 50;
		// params.y = 50;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		manager.addView(this, params);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e("out touch", "");
		return false;
	}

	public void getView() {
		bgLayout = (RelativeLayout) findViewById(R.id.bg_panel);
		wordCard = (WordCard) findViewById(R.id.word_card);
	}

	public void setView() {
		bgLayout.setBackgroundColor(Color.TRANSPARENT);
		wordCard.setOnTouchListener(onTouchListener);
		bgLayout.setOnClickListener(onClickListener);
		if(CheckNetWork.isNetworkAvailable(mContext)){
				wordCard.searchWord(selectText);
		}else{
			Toast.makeText(mContext, "�������Ӳ����ã����������������", 1000).show();
		}
	}

	private OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			}
			// �˴�����true���������¼�����������ݵ����㡣��content��һ�����Ҫ������Ӧ�ĵ���¼�
			// ����Ӧ�Ĵ�����Ȼ�Ὣ�¼����ݵ����㣬�����¼��Ĵ�����
			return true;
		}
	};
	
	/**
	 * 
	 * �ڱ�����һ�㲶�����¼��������view�ؼ�
	 */
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			manager.removeView(PanelView.this);
		}
	};

}
