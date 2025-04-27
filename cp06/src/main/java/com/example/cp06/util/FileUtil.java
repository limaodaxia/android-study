package com.example.cp06.util;

import android.graphics.Bitmap;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    public static void saveImage(String fileName, Bitmap bitmap) {
        // 根据文件的指定路径构造输出流对象
        try (FileOutputStream fos = new FileOutputStream(fileName)){
            // 把位图压缩到输出流中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
