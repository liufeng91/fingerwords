package com.wsh.fingerwords.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.wsh.fingerwords.entity.FavoriteWord;


import android.R.bool;
import android.R.integer;
import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 
 * @author zzaidb.sqlite������ݿ����
 * 
 */
public class ZDBHelper {
	
	public String DBNAME = "zzaidb";// ���ݿ���
	
	public String TABLE_FAVORITEWORD = "FavoriteWord";// �ղص��ʱ���
	public String TABLE_USER = "user";

	

	
	// FavoriteWord����ֶ�
	public String FIELD_WORD = "Word";
	public String FIELD_AUDIO = "audio";
	public String FIELD_PRON = "pron";
	public String FIELD_DEF = "def";
	public String FIELD_ISCLOUD = "isCloud";
	public String FIELD_CREATEDATE = "CreateDate";
	public String FIELD_USERNAME = "userName";
	public String FIELD_ID="id";
	
	
	private SQLiteDatabase mDB = null;


	private Context mContext;

	public ZDBHelper(Context context) {
		mContext = context;
		mDB = openDatabase();
	}
	
	/**
	 * ���ܣ������ݿ�
	 */
	
	private SQLiteDatabase openDatabase() {
		return SQLiteDatabase.openOrCreateDatabase(ZDBManager.DB_PATH + "/"
				+ ZDBManager.DB_NAME, null);
	}
	

	/**
	 * 
	 * ��ӵ��ղصĵ���
	 * 
	 * @param favoriteWord
	 * @return
	 */
	public boolean saveFavoriteWord(FavoriteWord favoriteWord) {
		String sqlString = "insert into FavoriteWord(word,audio,pron,def,CreateDate)"
				+" values(?,?,?,?,?)";
		Object[] objects=new Object[]{favoriteWord.Word,
				favoriteWord.audio,favoriteWord.pron,favoriteWord.def,favoriteWord.CreateDate};
		return exeSql(sqlString,objects);
	}

	/**
	 * 
	 * �Ƴ��ղصĵ���
	 * 
	 */
	public boolean deleteFavoriteWord(String word) {
		String sqlString = "delete from FavoriteWord" + " where Word='" + word
				+ "'";
		return exeSql(sqlString);
	}

	/**
	 * 
	 * ��װ�ĸ���ִ�����ݿ����
	 * 
	 */
	public boolean exeSql(String sqlString) {
		boolean flag;
		if (!mDB.isOpen()) {
			mDB = openDatabase();
		}
		try {
			mDB.execSQL(sqlString);
			mDB.close();
			flag = true;
		} catch (Exception e) {
			flag = false;
			// TODO: handle exception
		} finally {
			if (mDB.isOpen()) {
				mDB.close();
			}
		}
		return flag;
	}
	public boolean exeSql(String sqlString,Object[] objects) {
		boolean flag;
		if (!mDB.isOpen()) {
			mDB = openDatabase();
		}
		try {
			mDB.execSQL(sqlString,objects);
			mDB.close();
			flag = true;
		} catch (Exception e) {
			flag = false;
			// TODO: handle exception
		} finally {
			if (mDB.isOpen()) {
				mDB.close();
			}
		}
		return flag;
	}

	/**
	 * 
	 * �����ղصĵ���
	 * 
	 */
	public ArrayList<FavoriteWord> getFavoriteWords() {
		if (!mDB.isOpen()) {
			mDB = openDatabase();
		}
		ArrayList<FavoriteWord> favWordlist = new ArrayList<FavoriteWord>();
		String sqlSting = "select * from FavoriteWord";
		Cursor cursor = null;
		try {
			cursor = mDB.rawQuery(sqlSting, null);
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				for (int i = 0; i < cursor.getCount(); i++) {
					FavoriteWord favoriteWord = new FavoriteWord();
					favoriteWord.audio = cursor.getString(cursor
							.getColumnIndex(FIELD_AUDIO));
					favoriteWord.CreateDate = cursor.getString(cursor
							.getColumnIndex(FIELD_CREATEDATE));
					favoriteWord.def = cursor.getString(cursor
							.getColumnIndex(FIELD_DEF));
					favoriteWord.id = cursor.getInt(cursor
							.getColumnIndex(FIELD_ID));
					favoriteWord.pron = cursor.getString(cursor
							.getColumnIndex(FIELD_PRON));
					favoriteWord.Word = cursor.getString(cursor
							.getColumnIndex(FIELD_WORD));
					
					favWordlist.add(i, favoriteWord);
					
					cursor.moveToNext();
				}
			}
			cursor.close();
			mDB.close();
		} catch (Exception e) {
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (mDB != null) {
				mDB.close();
			}
		}
		return favWordlist;
	}
}
