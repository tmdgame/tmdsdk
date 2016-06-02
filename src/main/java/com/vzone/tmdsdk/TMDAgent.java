package com.vzone.tmdsdk;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.vzone.tmdsdk.config.PreferencesUtils;
import com.vzone.tmdsdk.task.TimeAnalysis;
import com.vzone.tmdsdk.tool.HardwareTool;
import com.vzone.tmdsdk.tool.NetworkTool;
import com.vzone.tmdsdk.tool.AppTool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 詹子聪 on 2016/5/31.
 */
public class TMDAgent {

    private static boolean mIsDebugMode;
    /**设置调试模式**/
    public static void setDebugMode(boolean isDebugMode) {
        mIsDebugMode = isDebugMode;
    }
    /**查看是否调试模式**/
    public static boolean isDebugMode() {
        return mIsDebugMode;
    }

    private static String mUserId;
    private static String mAppKey;
    private static PreferencesUtils mPreferences;
    // 定时更新在线时间
    private static ScheduledExecutorService mScheduledService;
    // 不定时任务
    private static ExecutorService mUntimeService;

    /**登录时间*/
    private static long mStartTimeMillis;
    private static TimeAnalysis mTimeAnalysis;

    /**
     * 在应用的入口Activity的onCreate()方法中调用
     * 初始化配置信息
     * 1.获取统计策略，获取用户ID========================================================
     * 2.本机硬件信息是否已经上传过
     */
    public static void initTMDAgent(Activity activity) {
        mUserId = activity.getIntent().getStringExtra("userId");
    }

    /**
     * 登录成功之后调用
     */
    public static void onLoginSucceed(final Activity activity) {
        try {
            ApplicationInfo appInfo = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
            mAppKey = appInfo.metaData.getString("WEIZONE_APPKEY");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mPreferences = PreferencesUtils.getInstance(activity);

        // 有AppKey=================================还要有我们平台的用户ID才上报信息======================
        if (!TextUtils.isEmpty(mAppKey)) {
            mScheduledService = Executors.newScheduledThreadPool(5);
            mUntimeService = Executors.newFixedThreadPool(5);

            // 每隔90秒提交一次，90秒之后提交第一次
            mStartTimeMillis = System.currentTimeMillis();
            mTimeAnalysis = new TimeAnalysis(mStartTimeMillis);
            mScheduledService.scheduleAtFixedRate(mTimeAnalysis, 90, 90, TimeUnit.SECONDS);

            // 以下信息仅在启动的时候提交一次
            if (!mPreferences.hasUploadedHardware()) {
                mUntimeService.submit(new Runnable() {
                    @Override
                    public void run() {
                        HardwareTool.getHardwareInfos(activity); // 硬件
                        // 成功之后将该key设置为true====================================
                    }
                });
            }

            if (!mPreferences.hasUploadedSoftware()) {
                mUntimeService.submit(new Runnable() {
                    @Override
                    public void run() {
                        AppTool.getAppList(activity); // 软件
                    }
                });
            }

            if (!mPreferences.hasUploadedNetwork()) {
                mUntimeService.submit(new Runnable() {
                    @Override
                    public void run() {
                        // 网络采集
                        if (NetworkTool.isMobileConnected(activity)) {
                            String corporation = NetworkTool.getCommunicationCorporation(activity);
                            Log.i("网络模式", corporation);
                        } else {
                            // WIFI，上传外网IP
                            Log.i("网络模式是WiFi", NetworkTool.getOutIP());
                        }
                    }
                });
            }
        } else {
            if (mIsDebugMode) {
                Log.e("WARMING", "没有AppKey，请检查是否已在清单文件的Application节点内添加。");
            }
        }
    }

    /**
     * 退出应用的时候调用
     */
    public static void onAppExit() {
        if (mScheduledService != null && !mScheduledService.isShutdown()) {
            mScheduledService.shutdownNow();
        }
    }

    /**
     * 支付信息，请在支付成功之后调用
     * @param payChannel 支付的渠道
     * @param account 支付的金额（单位：元/RMB）
     * @param payTime 支付时间（建议采用“xxxx年-xx月-xx日 xx时:xx分:xx秒”的格式，例如：2016-05-31 15:32:24）
     */
    public static void onPayMoneySucceed(String payChannel, float account, String payTime) {

    }
}



