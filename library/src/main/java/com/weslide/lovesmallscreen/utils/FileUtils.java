package com.weslide.lovesmallscreen.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by xu on 2016/7/7.
 * 文件操作
 */
public class FileUtils {

    private FileUtils() {
        /**cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 压缩图片
     *
     * @param filePath
     * @return
     */
    public static File compressImage(String filePath) {
        File uploadFile = new File(filePath);

        //压缩后上传
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uploadFile.getPath(), options);
        // 计算缩放比&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
//        int scale = computeSampleSize(options, -1, 128 * 128);
        int scale = 1;
        options.inSampleSize = scale;
        options.inJustDecodeBounds =  false;
        Bitmap bitmap = BitmapFactory.decodeFile(uploadFile.getPath(), options);
        Bitmap compressBitmap = ImageBitmapTools.comp(bitmap);
        //将图片重新保存
        ImageBitmapTools.savePhotoToSDCard(compressBitmap, uploadFile.getPath());
        bitmap.recycle();
        compressBitmap.recycle();
        uploadFile = new File(uploadFile.getPath());

        L.e("压缩后的图片大小：" + uploadFile.length() / 1024);

        return uploadFile;

    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :(int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 将输入流保存为文件
     *
     * @param inputStream
     * @param saveFile
     */
    public static void inputStreamSaveFile(InputStream inputStream, String saveFile) {

        L.i("保存的文件目录为：" + saveFile);

        File file = new File(saveFile);
        if (!file.exists()) {
            //创建文件
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[10240];
            int len = 0;

            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();

        } catch (Exception e) {
            file.delete();
        }
    }

    public static long getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                long size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                long size = file.length();
                return size;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0;
        }
    }


}
