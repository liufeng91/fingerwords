package com.wsh.fingerwords.util;

import android.os.Environment;

public class Constant {
	public static String SDCARD_PATH =Environment
			.getExternalStorageDirectory().toString();
	// ��������sd���ϵ�ַ
	public static final String APP_DATA_PATH = Environment
				.getExternalStorageDirectory() + "/wsh/fingerwords/";
	// mp3�ļ����sd�����ļ���
	public static final String SDCARD_AUDIO_PATH = "audio";
	//�����ղص��ʷ����ĵ�ַ
	public static final String SDCARD_FAVWORD_AUDIO_PATH="word";
	//������Ƶʱ�����ǰ����ʱ��
	public static final int SEEK_NEXT=5000;
	//������Ƶʱ���ˣ����˵�ʱ��
	public static final int SEEK_PRE=-5000;
	//�����ղص��ʷ����ĵ�ַ
	public static final String SCREENSHOT_IMAGE_PATH=Environment
			.getExternalStorageDirectory()+"/QQ_Screenshot/";
	public static final String ASSETS_TESSDATA_PATH = "tessdata";
	public static final String TESSBASE_PATH = Constant.APP_DATA_PATH
			+ "tessbase/";
	public static final String TESSDATA_PATH = TESSBASE_PATH+"tessdata";
	
}
