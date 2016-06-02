package com.vzone.tmdsdk.model;

/**
 * Created by 詹子聪 on 2016/6/2.
 * 硬件采集
 */
public class Hardware {
    // 设备型号
    private String deviceModel;
    // 总容量
    private float totalSize;
    // 可用容量
    private float availableSize;
    // 分辨率的宽
    private int widthPixels;
    // 分辨率的高
    private int heightPixels;

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public float getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(float totalSize) {
        this.totalSize = totalSize;
    }

    public float getAvailableSize() {
        return availableSize;
    }

    public void setAvailableSize(float availableSize) {
        this.availableSize = availableSize;
    }

    public int getWidthPixels() {
        return widthPixels;
    }

    public void setWidthPixels(int widthPixels) {
        this.widthPixels = widthPixels;
    }

    public int getHeightPixels() {
        return heightPixels;
    }

    public void setHeightPixels(int heightPixels) {
        this.heightPixels = heightPixels;
    }
}
