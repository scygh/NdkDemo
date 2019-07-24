package com.scy.android.ndkdemo;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * created by scy on 2019/7/23 09:46
 * gmail：cherseey@gmail.com
 */
public class NDKTools {
    private static final String TAG = NDKTools.class.getSimpleName();
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    public NDKTools(File device, int baudrate, int flags) throws SecurityException, IOException {
        if (!device.canRead() || !device.canWrite()) {
            try {
                Process su;
                su = Runtime.getRuntime().exec("/system/bin/su");
                String cmd = "chmod 666" + device.getAbsolutePath() + "\n" + "exit\n";
                su.getOutputStream().write(cmd.getBytes());
                if ((su.waitFor() != 0) || !device.canRead() || !device.canWrite()) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }
        System.out.print(device.getAbsolutePath() + "=================");
        mFd = open(device.getAbsolutePath(), baudrate, flags);
        if (mFd == null) {
            Log.e(TAG, "NDKTools: native open return null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
    }

    static {
        System.loadLibrary("ndkdemotest-jni");
    }

    public InputStream getInputStream() {
        return mFileInputStream;
    }


    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }


    public static native String getStringFromNDK();

    public static native FileDescriptor open(String path, int baudrate, int flags);

    public static native void close();
}
