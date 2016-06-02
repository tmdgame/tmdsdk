package com.vzone.tmdsdk.model;

/**
 * Created by 詹子聪 on 2016/6/2.
 */
public class OperatingSystem {
    // 系统名称
    private String systemName;
    // 系统版本
    private String systemVersion;
    // 安卓版本
    private String androidVersion;
    // 内核版本
    private String kernelVersion;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getKernelVersion() {
        return kernelVersion;
    }

    public void setKernelVersion(String kernelVersion) {
        this.kernelVersion = kernelVersion;
    }
}
