package lins.com.qz.bean;

/**
 * Created by LINS on 2017/6/22.
 */

public class SysNotify {

    private String id;
    private String title;
    private String url;
    private String bgurl;
    private String createtime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBgurl() {
        return bgurl;
    }

    public void setBgurl(String bgurl) {
        this.bgurl = bgurl;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "SysNotify{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", bgurl='" + bgurl + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
