package com.wsh.fingerwords.sqlite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.wsh.fingerwords.R;
/**
 * 
 * 
 * ���ܣ�����res/rawĿ¼�����ݿ��ļ���data�У���ֹ�û���ɾ
 * �ο���http://www.cnblogs.com/xiaowenji/archive/2011/01/03/1925014.html
 *
 */
public class ZDBManager {
	private final int BUFFER_SIZE = 3000000;
	  public static final String DB_NAME = "fingerwords.sqlite"; //��������ݿ��ļ���
	  public static final String PACKAGE_NAME = "com.wsh.fingerwords";
	  public static final String DB_PATH = "/data"
	      + Environment.getDataDirectory().getAbsolutePath() + "/"
	      + PACKAGE_NAME;  //���ֻ��������ݿ��λ��

	  private SQLiteDatabase database;
	  private Context context;
	  private int VERSION=1;
	  
	  public ZDBManager(Context context) {
	    this.context = context;
	    
	  }
	  /**
	   * 
	   * 
	   * ���ܣ���ʼ��ʱ����
	   */
	  public void openDatabase() {
	    this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
	  }
	  
	  /**
	   * 
	   * @param dbfile
	   * @return
	   * ���ܣ�����ָ����ַ�����ݿ��ļ�ʱ����
	   */
	  private SQLiteDatabase openDatabase(String dbfile) {
	    try {
	     if (!(new File(dbfile).exists())) {//�ж����ݿ��ļ��Ƿ���ڣ�����������ִ�е��룬����ֱ�Ӵ����ݿ�
	        InputStream is = this.context.getResources().openRawResource(R.raw.fingerwords); //����������ݿ�
	        FileOutputStream fos = new FileOutputStream(dbfile);
	        byte[] buffer = new byte[BUFFER_SIZE];
	        int count = 0;
	        while ((count = is.read(buffer)) > 0) {
	          fos.write(buffer, 0, count);
	        }
	        fos.close();
	        is.close();
	      }
	      SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
	          null);
	      return db;
	    } catch (FileNotFoundException e) {
	    Log.e("Database", "File not found");
	      e.printStackTrace();
	    } catch (IOException e) {
	     Log.e("Database", "IO exception");
	      e.printStackTrace();
	    }
	    return null;
	  }//do something else here  
	  public void closeDatabase() {
	    this.database.close();
	  }
	}