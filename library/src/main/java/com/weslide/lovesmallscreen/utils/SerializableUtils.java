package com.weslide.lovesmallscreen.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by xu on 2016/6/7.
 * 序列化的工具类
 */
public class SerializableUtils {

    private SerializableUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将文件序列化到缓存目录中
     * @param context
     * @param object
     * @param name
     */
    public static void serializableToCacheFile(Context context, Serializable object, String name){
//        File cacheDir = context.getCacheDir();//文件所在目录为getFilesDir();
        File cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//文件所在目录为getFilesDir();
        try{
            FileOutputStream fs = new FileOutputStream(cacheDir+"/"+name);
            ObjectOutputStream os =  new ObjectOutputStream(fs);
            os.writeObject(object);
            os.close();
            fs.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 将序列化文件移除
     * @param context
     * @param name
     */
    public static void removeCacheFile(Context context, String name){
        File serializableFile = new File(context.getCacheDir()+"/"+name);//文件所在目录为getFilesDir();
        serializableFile.delete();
    }

    /**
     * 在缓存目录中读取缓存对象
     * @param context
     * @param name
     * @return
     */
    public static Object getObjectByCacheFile(Context context, String name){
        try{
//            File cacheDir = context.getCacheDir();//文件所在目录为getFilesDir();
            File cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//文件所在目录为getFilesDir();
            File objectFile = new File(cacheDir.getPath() + "/" + name);
            if(!objectFile.exists()){
                return null;
            }
            FileInputStream fs = new FileInputStream(objectFile.getPath());
            ObjectInputStream ois = new ObjectInputStream(fs);
            Object object = ois.readObject();
            ois.close();
            fs.close();
            return object;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;

    }
}
