package com.wsh.fingerwords.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.util.Log;


public class DownAudio {
	private Context mContext;
	private URLConnection connection;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String saveName;
	private String fileUrl;
	
	public DownAudio(Context context,String urlString, String saveName){
		mContext=context;
		this.saveName = saveName;
		this.fileUrl =urlString;
	}
	public void DownFile() {

		// SDcard��Ŀ¼//iyuba/toelflistening/audio/����/��������
		//����������titleNum+sound
		String savePathString = Constant.APP_DATA_PATH + Constant.SDCARD_AUDIO_PATH
				+ "/" + Constant.SDCARD_FAVWORD_AUDIO_PATH + "/" + this.saveName;
		// Log.e("��ǰ�����ļ�--����·��",savePathString);
		File mp3File = new File(savePathString);

		// ���Ե��жϴ��ļ��Ƿ��������
		if (mp3File.exists() && mp3File.length() > 64)// ˵���ļ�������
		{
			return;
		}

		// else
		File fileTemp = new File(savePathString);// +".4ma"
		if (fileTemp.exists() && fileTemp.length() > 0) {// ����ļ����ڲ����ļ���С����0������ļ�δ������ɣ���Ҫ��������
			fileTemp.delete();// ɾ��ԭ�ļ�
			// downTests.remove(0);
		}

		// Log.e("������Ƶ-�����ַ", fileUrl);
		// Log.e("������Ƶ-��������", saveName);
		/*
		 * ���ӵ�������
		 */
		if (CheckNetWork.isNetworkAvailable(mContext)) {
			try {
				URL url = new URL(fileUrl);
				Log.e(fileUrl, "url");
				connection = url.openConnection();
				if (connection.getReadTimeout() >= 5) {
					return;
				}
				inputStream = connection.getInputStream();
				//Log.e("��ʼ����---------->connection.getReadTimeout()", "��������ʱ��"+connection.getReadTimeout());
			
			} catch (MalformedURLException e1) {
				 //Log.e("������Ƶ", "error");
				// TODO Auto-generated catch block
				e1.printStackTrace();
				// downloadStateListener.onErrorListener(e1.getMessage());
				// downloadStateListener.onPausedListener();
			} catch (IOException e) {
				 //Log.e("������Ƶ", "error");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return;
		}

		/*
		 * �ļ��ı���·���ͺ��ļ�������Nobody.mp3�����ֻ�SD����Ҫ�����·����������������½�
		 */
		String savePAth = Constant.APP_DATA_PATH + Constant.SDCARD_AUDIO_PATH
				+ "/" + Constant.SDCARD_FAVWORD_AUDIO_PATH;
		File file1 = new File(savePAth);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		// Log.e("������Ƶ-��������(����)", savePathString);
		File file = new File(savePathString);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			//Log.e("writefilebbb", " "+FileLength);
			outputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024 * 4];
			int FileLength = connection.getContentLength();
			int DownedFileLength=0;
			if (FileLength > 0)// ������
			{
				
				while (DownedFileLength < FileLength) {
					//Log.e("writefile", " "+FileLength);
					int length = inputStream.read(buffer);
					DownedFileLength += length;
					outputStream.write(buffer, 0, length);
					
					// message1.what = 1;
					// handler.sendEmptyMessage(1);
				}
			}

			// reNameFile(savePathString, savePathString+".mp3");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// handler.sendEmptyMessage(3);
			//ConfigManager.Instance().putInt(saveName, 0); // ������ȣ������б�ʶ�������
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// handler.sendEmptyMessage(3);
			//ConfigManager.Instance().putInt(saveName, 0); // ������ȣ������б�ʶ�������
			e.printStackTrace();
		} finally {
			try {
				
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void startDownLoadAudio(){
		ThreadManageUtil.sendRequest(new ThreadObject() {
			@Override
			public Object handleOperation() {
				// downloadStateListener.onStartListener();//��ʼ����
				DownFile();
				return null;
			}
		});
	}
}
