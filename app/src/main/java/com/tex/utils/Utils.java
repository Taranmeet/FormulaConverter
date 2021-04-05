package com.tex.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

public class Utils {

    /**
     * Method to remove spaces and white space chars from string.
     *
     * @param input Raw input with spaces.
     * @return String with spaces removed.
     */
    public static String trimWhiteSpaces(String input) {
        return input.replaceAll("\\s", "");
    }

    /**
     * Method to check if image is already cached in local storage.
     *
     * @param iHash hash of image stored.
     * @return true if image is already present.
     */
    public static boolean isImageCached(String iHash) {
        File file = new File(getImagePath(iHash));
        return file.exists();
    }

    /**
     * Method to get image path from hash string.
     *
     * @param iHash String hash from server.
     * @return Path  for file to be saved.
     */
    public static String getImagePath(String iHash) {
        return FormulaApplication.mContext.getExternalFilesDir(null)
                + File.separator
                + iHash
                + ".png";
    }

    /**
     * Method to write Image in response body to disk.
     *
     * @param body Response body from server.
     * @param file file to write data to.
     * @return true when write was success false otherwise.
     */
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

    /**
     * Method to check internet connection.
     *
     * @return true if internet is connected.
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) FormulaApplication
                .mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnected();
    }
}
