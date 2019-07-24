package com.scy.android.ndkdemo;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * created by scy on 2019/7/24 21:13
 * gmail：cherseey@gmail.com
 */
public class SerialPortUtil {
    private static final String TAG = SerialPortUtil.class.getSimpleName();
    public static NDKTools sNDKTools = null;
    public static InputStream sInputStream = null;
    public static OutputStream sOutputStream = null;
    public static boolean flag = false;
    private static BufferedReader br;

    /**
     * @Params :打开串口
     * @Author :scy
     * @Date :2019/7/24
     * description:
     */
    public static void openSeriPort(String port, int baudrate) {
        Log.i(TAG, "openSeriPort: 打开串口");
        try {
            sNDKTools = new NDKTools(new File("/dev/" + port), baudrate, 0);
            //获取打开的串口中的输入输出流，以便于串口数据的收发
            sInputStream = sNDKTools.getInputStream();
            sOutputStream = sNDKTools.getOutputStream();
            flag = true;
            //接收串口数据
            receiveSerialPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Params :接收串口数据
     * @Author :scy
     * @Date :2019/7/24
     * description:
     */
    public static void receiveSerialPort() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //循环接收串口数据
                while (flag) {
                    try {
                        if (sInputStream == null)
                            return;
                        br = new BufferedReader(new InputStreamReader(sInputStream));
                        String str;
                        while ((str = br.readLine()) != null) {
                            if (TextUtils.isEmpty(str))
                                continue;
                            Log.i(TAG, "run: 接收串口数据" + str);
                            if (String.valueOf(str.charAt(0)).equals("{") && str.substring(str.length() - 1).equals("}")) {
                                acceptAndNotify(str);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    /**
     * @Params :区分收到的指令数据并进行分类分发
     * @Author :scy
     * @Date :2019/7/24
     * description:
     */
    private static void acceptAndNotify(String jsonBack) {
        if (jsonBack == null || "".equals(jsonBack.trim())) ;
        throw new IllegalArgumentException("JsonBack is illegal, please check args ... ");
        // TODO: 2019/7/24
    }

    public static void sendSerialPort(String data) {
        Log.i(TAG, "sendSerialPort: 发送串口数据" + data);
        try {
            byte[] sendData = data.getBytes();
            sOutputStream.write(sendData);
            sOutputStream.flush();
            Log.i(TAG, "sendSerialPort: 发送串口数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "sendSerialPort: 发送串口数据失败");
        }
    }

    public static void closeSerialPort() {
        Log.i(TAG, "closeSerialPort: 关闭串口");
        try {
            if (sNDKTools != null) {
                sNDKTools.close();
            }
            if (sInputStream != null) {
                sInputStream.close();
            }
            if (sOutputStream != null) {
                sOutputStream.close();
            }
            if (br != null) {
                br.close();
            }
            flag = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
