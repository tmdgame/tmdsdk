package com.vzone.tmdsdk.task;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

public class GetAppsTask {
    /**
     * 获取已安装应用信息
     */
    private List<String> getInstalledAppInfos(Activity mActivity) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = mActivity.getPackageManager().queryIntentActivities(mainIntent, 0);

        PackageManager pm = mActivity.getPackageManager();
        // 放到子线程中执行
        List<String> appInfos = new ArrayList<>();
        for (ResolveInfo resolveInfo : resolveInfos) {
            // 应用名
            appInfos.add(resolveInfo.loadLabel(pm).toString());
            // 获得应用包名
            //resolveInfo.activityInfo.packageName;
            // 应用图标
            //resolveInfo.activityInfo.loadIcon(pm);
            String appVersion = "";
            try {
                appVersion = mActivity.getPackageManager().getPackageInfo(resolveInfo.activityInfo.packageName, 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return appInfos;
    }
}