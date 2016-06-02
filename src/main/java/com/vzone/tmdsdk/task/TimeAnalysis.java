package com.vzone.tmdsdk.task;

import android.util.Log;

import com.vzone.tmdsdk.TMDAgent;
import com.vzone.tmdsdk.tool.StringTool;

/**
 * 提交登录时长发送给服务器
 */
public class TimeAnalysis implements Runnable {
    private long mStartTimeMillis;
    public TimeAnalysis(long startTimeMillis) {
        this.mStartTimeMillis = startTimeMillis;
    }

    @Override
    public void run() {
        String loginTime = StringTool.getDateFromTimeMillis(mStartTimeMillis);
        long gameTime = System.currentTimeMillis() - mStartTimeMillis;
        // 发送请求更新在线时间
        if (TMDAgent.isDebugMode()) {
            Log.i("登录信息", "登录时间:" + loginTime + " 登录时长：" + String.valueOf(gameTime/1000));
        }
    }
}