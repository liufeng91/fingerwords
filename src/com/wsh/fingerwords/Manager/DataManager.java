package com.wsh.fingerwords.Manager;

import java.util.ArrayList;

import com.wsh.fingerwords.entity.FavoriteWord;

/**
 * 
 * ���ڸ���ҳ��֮������ݹ����봫��
 * @author κ���
 *
 */
public class DataManager {
	private static DataManager instance;
    public static DataManager Instance() {
      if (instance == null) {
        instance = new DataManager();
      }
      return instance;
    }
    
	public ArrayList<FavoriteWord> favWordList=null;//�ղصĵ��ʵ��б� 
	
	
}
