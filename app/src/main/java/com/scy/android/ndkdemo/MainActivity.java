package com.scy.android.ndkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String text = NDKTools.getStringFromNDK();
        ((TextView) findViewById(R.id.tv1)).setText(text);
        //创建NDKTools,和native方法
        //build make project 获取classes文件
        // 获取.h文件  G:\ndk\NdkDemo\app\build\intermediates\classes\debug>javah -jni com.scy.android.ndkdemo.NDKTools
        //新建jni 剪切.h文件到jni ,编写c文件的方法，创建mk文件
        //配置build.gradle
    }
}
