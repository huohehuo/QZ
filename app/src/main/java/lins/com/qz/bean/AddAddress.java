package lins.com.qz.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by LINS on 2017/5/2.
 */

public class AddAddress extends BmobObject{
    private String belong;
    private String addr;


    public AddAddress(String belong, String addr) {
        this.belong = belong;
        this.addr = addr;
    }


    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
