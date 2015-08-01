package com.jeegoo.o2o.tehuiduo.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by xintong on 2015/7/20.
 */
public class FileUtils {
    public static final String BASE_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "TeHuiDuo" + File.separator;

    public static String createFile(String username) {
        String path = BASE_FILE_PATH + username + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static String createImageFile(String username, String productId) {
        String path = BASE_FILE_PATH + username + File.separator + "ImageCache" + File.separator  + productId + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

}
