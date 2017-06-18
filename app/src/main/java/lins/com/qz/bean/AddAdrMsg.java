package lins.com.qz.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by LINS on 2017/5/2.
 */

public class AddAdrMsg extends BmobObject{
    private String title;
    private String addr;

    private String pic ="1";

    public AddAdrMsg(String title, String addr) {
        this.title = title;
        this.addr = addr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "AddAdrMsg{" +
                "title='" + title + '\'' +
                ", addr='" + addr + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
