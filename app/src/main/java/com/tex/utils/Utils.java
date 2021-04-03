package com.tex.utils;

import android.util.Log;

import com.tex.FormulaApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

public class Utils {

    public static String trimWhiteSpaces(String input) {
        return input.replaceAll("\\s", "");
    }

    public static boolean isImageCached(String iHash) {
        File file = new File(getImagePath(iHash));
        return file.exists();
    }

    public static String getImagePath(String iHash){
        return FormulaApplication.mContext.getExternalFilesDir(null)
                + File.separator
                + iHash
                + ".png";
    }

    public static boolean writeResponseBodyToDisk(ResponseBody body, File file) {
        try {

            if (!file.exists()) {
                boolean created = file.createNewFile();
                Log.e("Taran", "created " + created);
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
