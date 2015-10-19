package com.wsh.fingerwords.util;

import java.io.File;
import java.io.FilenameFilter;



import android.R.integer;
import android.util.Log;

/**
* File���list�������ظ�Ŀ¼�µ������ļ�������Ŀ¼�����ļ������ļ�������·����Ϣ
* File���listFiles��������Ŀ¼�µ������ļ�������Ŀ¼����File����
* FilenameFilter���ļ����������ӿ��࣬�����Զ�����ļ�������������ʵ�ָýӿڵ�accept����
* @author Administrator
*
*/
public class ListFileUtil {

/**
* ����һ���ڲ��࣬ʵ����FilenameFilter�ӿڣ����ڹ����ļ� 
*/
public static class MyFilenameFilter implements FilenameFilter{

   private String suffix = "";//�ļ�����׺
  
   public MyFilenameFilter(String suffix){
    this.suffix = suffix;
   }
  @Override
  public boolean accept(File dir, String filename)
  {
    //����ļ�����ָ���ĺ�׺��ͬ�㷵��true
    if(new File(dir,filename).isFile()){
     return filename.endsWith(suffix);
    }
    else{
     //������ļ���
     return true;
    }
  }
  
}
/**
 * 
 * @param dirName
 * @return
 * ���ܣ����dirName�µ����ļ������ļ�����
 */
public static int checkSubFiles(String dirName)
{
  int subFileNum = 0;//���ļ�����
  //���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
  if(!dirName.endsWith(File.separator)){
   dirName = dirName + File.separator;
  }
 
  File dirFile = new File(dirName);
  //���dir��Ӧ���ļ������ڣ����߲���һ���ļ������˳�
  if(!dirFile.exists() || (!dirFile.isDirectory())){
//   System.out.println("Listʧ�ܣ��Ҳ���Ŀ¼��"+dirName);
   return subFileNum;
  }
 
  //�г��ļ��������е��ļ�,listFiles��������Ŀ¼�µ������ļ�������Ŀ¼����File����
  File[] files = dirFile.listFiles();
//  for(int i = 0; i < files.length; i++){
//  if (files[i].isDirectory()){
////    Log.e(" checkSubFiles�����Ŀ¼��",files[i].getAbsolutePath() );
////    ListFileUtil.listAllFiles(files[i].getAbsolutePath());
//    subFileNum += 1;
//   }
//  }
  subFileNum = files.length;
  return subFileNum;
}
/**
 * 
 * @param dirName
 * ���ܣ�ɾ��ָ��Ŀ¼�µ������ļ���
 */
public static void  deleteFiles(String dirName)
{
  //���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
  if(!dirName.endsWith(File.separator)){
   dirName = dirName + File.separator;
  }
 
  File dirFile = new File(dirName);
  //���dir��Ӧ���ļ������ڣ����߲���һ���ļ������˳�
  if(!dirFile.exists() || (!dirFile.isDirectory())){
   return;
  }
 
  //�г��ļ��������е��ļ�,listFiles��������Ŀ¼�µ������ļ�������Ŀ¼����File����
  File[] files = dirFile.listFiles();
  for(int i = 0; i < files.length; i++){
   if(files[i].isFile()){
     files[i].delete();
//    System.out.println(files[i].getAbsolutePath() + " ���ļ���");
   }else if (files[i].isDirectory()){
//    System.out.println(files[i].getAbsolutePath() + " ��Ŀ¼��");
    ListFileUtil.deleteFiles(files[i].getAbsolutePath());
   }
  }
}
/**
* �г�Ŀ¼�����е��ļ�������Ŀ¼���ļ�·��
* @param dirName �ļ��е��ļ�·��
*/
public static void listAllFiles(String dirName){
  
  
   //���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
   if(!dirName.endsWith(File.separator)){
    dirName = dirName + File.separator;
   }
  
   File dirFile = new File(dirName);
   //���dir��Ӧ���ļ������ڣ����߲���һ���ļ������˳�
   if(!dirFile.exists() || (!dirFile.isDirectory())){
//    System.out.println("Listʧ�ܣ��Ҳ���Ŀ¼��"+dirName);
    return;
   }
  
   /*  
   * list�������ظ�Ŀ¼�µ������ļ�������Ŀ¼�����ļ������ļ�������·����Ϣ
   * 
      String[] files = dirFile.list();
    for(int i = 0; i < files.length; i++){
     System.out.println(files[i]);
    }
   */
  
   //�г��ļ��������е��ļ�,listFiles��������Ŀ¼�µ������ļ�������Ŀ¼����File����
   File[] files = dirFile.listFiles();
   for(int i = 0; i < files.length; i++){
    if(files[i].isFile()){
//     System.out.println(files[i].getAbsolutePath() + " ���ļ���");
    }else if (files[i].isDirectory()){
//     System.out.println(files[i].getAbsolutePath() + " ��Ŀ¼��");
     ListFileUtil.listAllFiles(files[i].getAbsolutePath());
    }
   }
}

/**
* �г�Ŀ¼��ͨ���ļ������������˺���ļ�
* @param filter �ļ�������������
* @param dirName Ŀ¼��
*/
public static int listFilesByFilenameFilter(FilenameFilter filter,String dirName){
  int fileSum = 0;
   //���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
   if(!dirName.endsWith(File.separator)){
    dirName = dirName + File.separator;
   }
  
   File dirFile = new File(dirName);
   //���dir��Ӧ���ļ������ڣ����߲���һ���ļ������˳�
   if(!dirFile.exists() || (!dirFile.isDirectory())){
//    Log.e("Listʧ�ܣ��Ҳ���Ŀ¼��", dirName);
    return fileSum;
   }
  
   //���Դ�ļ�����������ͨ�����������ļ�������Ŀ¼
   File[] files = dirFile.listFiles(filter);
   for(int i = 0; i < files.length; i++){
    if(files[i].isFile()){
//      Log.e(files[i].getAbsolutePath() , " ���ļ���");
      fileSum ++;
    }
//    else if (files[i].isDirectory()){
//      Log.e(files[i].getAbsolutePath() , " ��Ŀ¼��");
//     ListFileUtil.listFilesByFilenameFilter(filter,files[i].getAbsolutePath());
//    }
   }
   return fileSum;
}
/**
 * 
 * ��ȡ�ļ��Ĵ�С  ��λM
 * @param file
 * @return
 */
public static float getDirSize(File file) {     
    //�ж��ļ��Ƿ����     
    if (file.exists()) {     
        //�����Ŀ¼��ݹ���������ݵ��ܴ�С    
        if (file.isDirectory()) {     
            File[] children = file.listFiles();     
            float size = 0;     
            for (File f : children)     
                size += getDirSize(f);     
            return size;     
        } else {//������ļ���ֱ�ӷ������С,�ԡ��ס�Ϊ��λ   
            float size =file.length()/1024/1024;        
            return size;     
        }     
    } else {     
    	return 0;
    }     
}    
}
