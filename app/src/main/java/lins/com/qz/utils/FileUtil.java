package lins.com.qz.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import lins.com.qz.Config;

/**
 * Created by LINS on 2017/7/13.
 */

public class FileUtil {

    String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/revoeye/";

    public void saveFile(Bitmap bm, String fileName) throws IOException {
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/revoeye/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    /**
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    public static String isExistDir(String path){
        // 下载位置
        File downloadFile = new File(path);
        if (!downloadFile.mkdirs()) {
            downloadFile.mkdirs();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }
}
