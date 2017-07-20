package lins.com.qz.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by LINS on 2017/7/18.
 */

public class Version extends BmobObject{

    private String version;
    private String vsDetails;
    private String appSize;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVsDetails() {
        return vsDetails;
    }

    public void setVsDetails(String vsDetails) {
        this.vsDetails = vsDetails;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    @Override
    public String toString() {
        return "Version{" +
                "version='" + version + '\'' +
                ", vsDetails='" + vsDetails + '\'' +
                ", appSize='" + appSize + '\'' +
                '}';
    }
}
