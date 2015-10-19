package com.wsh.fingerwords.wegdit;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.wsh.fingerwords.R;
import com.wsh.fingerwords.listener.OnActionListener;

public class DragView extends Button {

	private Context mContext;
	public WindowManager manager;
	public WindowManager.LayoutParams params;
	private float mTouchStartX, mTouchStartY;// down������ȥ����Ը�view��λ��
	private int x, y;// �����Ļ��λ��
	private float statusBarHigh;// ״̬���߶�
	private long startTime = 0, endTime = 0;// ��¼��������Ŀ�ʼ�ͽ�����ʱ��
	private int GAP = 100;
	private int startx, starty;
	private OnActionListener onActionListener;
	private LayoutParams layoutParams;
	private LayoutInflater layoutInflater;
	
	public DragView(Context context,OnActionListener onActionListener) {
		super(context);
		mContext = context;
		this.onActionListener=onActionListener;
		
		setBackgroundResource(R.drawable.textnone);
		//setLayoutParams(lp);
		//((Activity) mContext).getLayoutInflater().inflate(R.layout.activity_gt,
			//	this);
		ini();
		iniData();
		// TODO Auto-generated constructor stub
	}
	/**
	 * ��ʼ��λ�õ�����
	 */
	public void iniData(){
		mTouchStartX=0;
		mTouchStartY=0;
		x=0;
		y=0;
		statusBarHigh=getStutsBarHigh(mContext);
		startTime=0;
		endTime=0;
		startx=0;
		starty=0;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// ��ȡ�����Ļ�����꣬������Ļ���Ͻ�Ϊԭ��
		x = (int) event.getRawX();
		y = (int) (event.getRawY());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ��ȡ���View�����꣬���Դ�View���Ͻ�Ϊԭ��
			mTouchStartX = event.getX();
			mTouchStartY = event.getY();
			startx = (int) event.getRawX();
			starty = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			updateViewPosition();
			break;
		case MotionEvent.ACTION_UP:
			updateViewPosition();
			//��������ľ�������10���أ�����Ϊ�������������onclick
			if (Math.sqrt(Math.pow(x - startx, 2) + Math.pow(y - starty, 2)) <=10){
				if(onActionListener!=null){
					Log.e("vhh", params.horizontalWeight+" "+params.horizontalMargin+
							" "+params.verticalWeight+" "+params.verticalMargin);
					onActionListener.onClick(params.x,params.y);
				}
			}
			//����������³�ʼ�����ݣ�Ϊ��һ���¼�����׼��
			iniData();
			break;
		}
		return false;
	}

	/**
	 * 
	 * ����view��λ��
	 */
	private void updateViewPosition() {
		// ���¸�������λ�ò���
		params.x = (int) (x - mTouchStartX);
		params.y = (int) (y - mTouchStartY - statusBarHigh);// ��ȥ״̬���ĸ߶ȡ��������ԭ��ͻ�ð취��
		// ���¸�������
		manager.updateViewLayout(this, params);
	}

	/**
	 * 
	 * ͨ�������ȡ״̬���ĸ߶�
	 * 
	 * @param context
	 * @return
	 */
	public static int getStutsBarHigh(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0;
		int sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
		}
		return sbar;
	}

	public void ini() {
		manager = (WindowManager) mContext.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		params = new WindowManager.LayoutParams();

		params.format = PixelFormat.RGBA_8888; // ����ͼƬ��ʽ��Ч��Ϊ����͸��
		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
				| WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
		// // ����window type,�˿ؼ�λ�ڴ�绰����һ��
		// �����������õĹؼ��ĵط�
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

		params.x = 50;
		params.y = 50;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;

		statusBarHigh = getStutsBarHigh(mContext);
		manager.addView(this, params);
	}
	
}
