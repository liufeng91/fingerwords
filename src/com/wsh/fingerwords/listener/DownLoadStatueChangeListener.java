package com.wsh.fingerwords.listener;

public interface DownLoadStatueChangeListener {
	/**
	 * 
	 * 
	 * ���ܣ���ʼ���أ��õ����ؽ���
	 */
	public void onStartListener(String info);// ��ʼ����

	/**
	 * 
	 * 
	 * ���ܣ���ͣ����
	 */
	public void onPausedListener(String info);// ��ͣ����

	/**
	 * 
	 * 
	 * ���ܣ��������
	 */
	public void onFinishedListener(String info);// ���

	/**
	 * 
	 * 
	 * ���ܣ����س��������쳣���쳣�жϡ����������쳣��Ϣ�����߳���ʾ
	 */
	public void onErrorListener(String info);// ���س��������쳣���쳣�жϡ�����
	
	/**
	 * 
	 * @return ���ܣ��ֶ���ͣ
	 */
	public boolean isPausedListener();// ��ͣ

}
