package com.ci123.library.basiclib.utils;

import java.io.File;

/**
 * Created by zc on 2018/1/29.
 */

public class FileUtils {
    public static boolean checkDirCreate(String path) {
        File file = new File(path);

        return file.exists() || file.mkdir();
    }

    public static boolean isDirExist(String path) {
        return new File(path).exists();
    }

    /**
     * 删除目录下的所有文件
     * @param path
     */
    public static void deleteFilesInPath(String path) {
        File file = new File(path);
        if(file.exists() && file.isDirectory()) {
            for(File f : file.listFiles()) {
                deleteFile(f.getAbsolutePath());
            }
        }
    }

    /**
     * 递归删除文件或子文件夹
     * @param path 路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if(!file.exists()) {
            return;
        }

        if(file.isFile()) {
            file.delete();
            return;
        }

        if(file.isDirectory()) {
            for(File f : file.listFiles()) {
                deleteFile(f.getAbsolutePath());
            }
            file.delete();
        } else{
            file.delete();
        }
    }
}
