package com.vzone.tmdsdk.tool;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.vzone.tmdsdk.model.App;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 詹子聪 on 2016/6/1.
 */
public class AppTool {

    /**
     * 获取已安装软件的信息
     */
    public static List<App> getAppList(Activity activity) {

        List<App> appInfos = new ArrayList<>();

        PackageManager pm = activity.getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
                App appInfo = new App();
                // 应用程序名称
                appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());

                // 应用程序版本
                appInfo.setAppVersion(packageInfo.versionName);

                // 应用大小
                File file = new File(packageInfo.applicationInfo.publicSourceDir);
                if (file != null) {
                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
                    String fileSize = df.format(((float) file.length()) / 1024.0f / 1024.0f);
                    appInfo.setAppSize(Float.parseFloat(fileSize));
                }
                appInfos.add(appInfo);
            }
        }
        return appInfos;
    }
}
