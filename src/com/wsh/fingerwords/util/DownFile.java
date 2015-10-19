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
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import com.wsh.fingerwords.listener.DownLoadStatueChangeListener;

public class DownFile {
	private Context mContext;
	private URLConnection connection;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String saveName;//�ļ�������  ������׺
	private String fileUrl;//�ļ������ص�ַ
	private String fileDir;//�ļ�����Ŀ¼
	private DownLoadStatueChangeListener downLoadStatueChangeListener;
	private ProgressBar progressBar;
	public static int FILE_MIN_LENGTH=1024*4;//���ڴ��Ե��ж��ļ��Ƿ�����
	/**
	 * 
	 * 
	 * @param context
	 * @param urlString
	 * @param fileDir ����/
	 * @param saveName
	 * @param downLoadStatueChangeListener
	 * @param progressBar
	 */
	public DownFile(Context context,String urlString,String fileDir, String saveName
			,DownLoadStatueChangeListener downLoadStatueChangeListener,ProgressBar progressBar){
		mContext=context;
		this.saveName = saveName;
		this.fileUrl =urlString;
		this.fileDir=fileDir;
		this.downLoadStatueChangeListener=downLoadStatueChangeListener;
		this.progressBar=progressBar;
	}
	public void DownLoadFile() {
	
		//�ļ��ı���λ��  fileDir ����/
		String savePathString = fileDir+saveName;
		
		File tempfile = new File(savePathString);
		if(tempfile.exists()){
			tempfile.delete();
		}
		// ���Ե��жϴ��ļ��Ƿ��������  ��С���Զ���
		/*if (tempfile.exists() && tempfile.length() > FILE_MIN_LENGTH)// ˵���ļ�������
		{
			if(downLoadStatueChangeListener!=null){
				if(progressBar!=null){
					downLoadStatueChangeListener.onFinishedListener(String.valueOf(progressBar.getProgress()));
				}else{
					downLoadStatueChangeListener.onFinishedListener(null);
				}
			}
			return;
		}else if(tempfile.exists()){
			tempfile.delete();
		}*/
		
		//���ӵ�������  �����ļ�
		if (CheckNetWork.isNetworkAvailable(mContext)) {
			try {
				URL url = new URL(fileUrl);
				connection = url.openConnection();
				if (connection.getReadTimeout() >= 5) {
					if(downLoadStatueChangeListener!=null){
						if(progressBar!=null){
							downLoadStatueChangeListener.onErrorListener(String.valueOf(progressBar.getProgress()));
						}else{
							downLoadStatueChangeListener.onErrorListener(null);
						}
					}
					return;
				}
				inputStream = connection.getInputStream();
				if(downLoadStatueChangeListener!=null){
					if(progressBar!=null){
						downLoadStatueChangeListener.onStartListener(String.valueOf(progressBar.getProgress()));
					}else{
						downLoadStatueChangeListener.onStartListener(null);
					}
				}
				//Log.e("��ʼ����---------->connection.getReadTimeout()", "��������ʱ��"+connection.getReadTimeout());
			
			} catch (MalformedURLException e1) {
				if(downLoadStatueChangeListener!=null){
					if(progressBar!=null){
						downLoadStatueChangeListener.onErrorListener(String.valueOf(progressBar.getProgress()));
					}else{
						downLoadStatueChangeListener.onErrorListener(null);
					}
				}
				e1.printStackTrace();
				return;
			
			} catch (IOException e) {
				if(downLoadStatueChangeListener!=null){
					if(progressBar!=null){
						downLoadStatueChangeListener.onErrorListener(String.valueOf(progressBar.getProgress()));
					}else{
						downLoadStatueChangeListener.onErrorListener(null);
					}
				}			
				e.printStackTrace();
				return;
			}
		} else {
			if(downLoadStatueChangeListener!=null){
				if(progressBar!=null){
					downLoadStatueChangeListener.onErrorListener(String.valueOf(progressBar.getProgress()));
				}else{
					downLoadStatueChangeListener.onErrorListener(null);
				}
			}
			return;
		}
		
		//����ļ���Ŀ¼�Ƿ����
		File file1 = new File(fileDir);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		
		//��ʼ���ļ�����д����
		File file = new File(savePathString);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				if(downLoadStatueChangeListener!=null){
					if(progressBar!=null){
						downLoadStatueChangeListener.onErrorListener(String.valueOf(progressBar.getProgress()));
					}else{
						downLoadStatueChangeListener.onErrorListener(null);
					}
				}
				e.printStackTrace();
				return;			
			}
		}
		try {
			//Log.e("writefilebbb", " "+FileLength);
			outputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024 * 4];
			int FileLength = connection.getContentLength();
			if(progressBar!=null){
				Message message=handler.obtainMessage(0, FileLength);
				handler.sendMessage(message);
			}
			int DownedFileLength=0;
			
			if (FileLength > 0)// ������
			{		
				while (DownedFileLength < FileLength) {
					//Log.e("writefile", " "+FileLength);
					int length = inputStream.read(buffer);
					DownedFileLength += length;
					outputStream.write(buffer, 0, length);
					//�޸�progresBar�Ľ���
					if(progressBar!=null){
						Message message=handler.obtainMessage(1, DownedFileLength);
						handler.sendMessage(message);
					}
				}
				if(downLoadStatueChangeListener!=null){
					if(progressBar!=null){
						downLoadStatueChangeListener.onFinishedListener(String.valueOf(progressBar.getProgress()));
					}else{
						downLoadStatueChangeListener.onFinishedListener(null);
					}
				}
			}

			// reNameFile(savePathString, savePathString+".mp3");
		} catch (FileNotFoundException e) {
			if(downLoadStatueChangeListener!=null){
				if(progressBar!=null){
					downLoadStatueChangeListener.onErrorListener(String.valueOf(progressBar.getProgress()));
				}else{
					downLoadStatueChangeListener.onErrorListener(null);
				}
			}		
			e.printStackTrace();
			
		} catch (IOException e) {
			if(downLoadStatueChangeListener!=null){
				if(progressBar!=null){
					downLoadStatueChangeListener.onErrorListener(String.valueOf(progressBar.getProgress()));
				}else{
					downLoadStatueChangeListener.onErrorListener(null);
				}
			}		
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
	
	//��������ӿڿ�ʼ�����ļ�
	public void startDownLoadFile(){
		ThreadManageUtil.sendRequest(new ThreadObject() {
			@Override
			public Object handleOperation() {
				// downloadStateListener.onStartListener();//��ʼ����
				DownLoadFile();
				return null;
			}
		});
	}	
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0://��ʼ��progressbar��ֵ
				
				if(progressBar!=null){
					progressBar.setMax(Integer.parseInt(msg.obj.toString()));
				}
				break;
			case 1://�޸�progressBar�Ľ���
				if(progressBar!=null){
					progressBar.setProgress(Integer.parseInt(msg.obj.toString()));
				}
				 break;
			default:
				break;
			}
		}
	};
}
