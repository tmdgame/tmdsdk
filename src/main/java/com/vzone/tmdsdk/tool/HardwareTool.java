package com.vzone.tmdsdk.tool;

import android.app.Activity;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;

import com.vzone.tmdsdk.model.Hardware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * Created by 詹子聪 on 2016/5/31.
 */
public class HardwareTool {

    /**
     * 获取SD卡可用容量
     * @return 单位(GB)
     */
    public static float getAvailableSize() {
        //取得SD卡文件路径
        String path = Environment.getExternalStorageDirectory().getPath();
        StatFs sf = new StatFs(path);
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();

        //返回SD卡空闲大小(GB)
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        String fileSize = df.format((freeBlocks * blockSize) / 1024.0f / 1024.0f / 1024.0f);
        return Float.parseFloat(fileSize);
    }

    /**
     * 获取SD卡总容量
     * @return 单位(GB)
     */
    public static float getSDCardSize(){
        String path = Environment.getExternalStorageDirectory().getPath();
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getBlockCount();

        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        String fileSize = df.format((availableBlocks * blockSize) / 1024.0f / 1024.0f / 1024.0f);
        return Float.parseFloat(fileSize);
    }

    /**
     * 硬件信息
     */
    public static Hardware getHardwareInfos(Activity activity) {
        Hardware hardware = new Hardware();

        // 设备型号
        hardware.setDeviceModel(Build.MODEL);

        // 总容量
        hardware.setTotalSize(getSDCardSize());

        // 剩余容量
        hardware.setAvailableSize(getAvailableSize());

        // 分辨率
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        hardware.setWidthPixels(displayMetrics.widthPixels);
        hardware.setHeightPixels(displayMetrics.heightPixels);

        return hardware;
    }
}
