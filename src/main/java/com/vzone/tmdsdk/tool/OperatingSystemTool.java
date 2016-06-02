package com.vzone.tmdsdk.tool;

import android.os.Build;

import com.vzone.tmdsdk.model.OperatingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 詹子聪 on 2016/6/2.
 */
public class OperatingSystemTool {

    /***
     * 获取Android Linux内核版本信息
     */
    private static String getLinuxKernelVersion() {
        Process process = null;
        String mLinuxKernal = null;
        try {
            process = Runtime.getRuntime().exec("cat /proc/version");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // get the output line
        InputStream outs = process.getInputStream();
        InputStreamReader isrout = new InputStreamReader(outs);
        BufferedReader brout = new BufferedReader(isrout, 8 * 1024);

        String result = "";
        String line;
        // get the whole standard output string
        try {
            while ((line = brout.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (result != "") {
            String Keyword = "version ";
            int index = result.indexOf(Keyword);
            line = result.substring(index + Keyword.length());
            index = line.indexOf(" ");
            mLinuxKernal = line.substring(0, index);
        }

        return mLinuxKernal;
    }

    public static OperatingSystem getOperatingSystemInfo() {
        OperatingSystem operatingSystem = new OperatingSystem();
        // 系统名称 比如flyme
        operatingSystem.setSystemName(Build.MANUFACTURER + " " + Build.MODEL);

        // 系统版本 比如2.0.1
        operatingSystem.setSystemVersion(Build.VERSION.INCREMENTAL);

        // 安卓版本，比如4.4.4
        operatingSystem.setAndroidVersion(Build.VERSION.RELEASE);

        // 内核版本
        operatingSystem.setKernelVersion(getLinuxKernelVersion());
        return operatingSystem;
    }
}
