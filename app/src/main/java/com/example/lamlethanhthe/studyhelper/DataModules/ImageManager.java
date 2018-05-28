package com.example.lamlethanhthe.studyhelper.DataModules;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Refered to Ilya Gazman on 3/6/2016.
 */

public class ImageManager {
    private String dir = "images";
    private String fileName = "default.png";
    private Context context;
    private boolean external;

    public ImageManager(String dir, String fileName, Context context, boolean external) {
        this.dir = dir;
        this.fileName = fileName;
        this.context = context;
        this.external = external;
    }

    public void save(Bitmap bmp) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(createFile());
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    private File createFile() {
        File directory;
        if (external)
            directory = getAlbumStorageDir(dir);
        else
            directory = context.getDir(dir, Context.MODE_PRIVATE);
        return new File(directory, fileName);
    }

    private File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdir())
            Log.e("ImageManager", "Directory not exists!");
        return file;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public Bitmap load() {
        FileInputStream iStream = null;
        try {
            iStream = new FileInputStream(createFile());
            return BitmapFactory.decodeStream(iStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (iStream != null)
                    iStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean delete() {
        File file = createFile();
        return file.delete();
    }
}
