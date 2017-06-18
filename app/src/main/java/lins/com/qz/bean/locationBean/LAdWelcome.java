package lins.com.qz.bean.locationBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LINS on 2017/6/18.
 */
@Entity
public class LAdWelcome {

    @Id
    private Long id;
    private String picurl;
    private String withtxt;
    private String withurl;
    public String getWithurl() {
        return this.withurl;
    }
    public void setWithurl(String withurl) {
        this.withurl = withurl;
    }
    public String getWithtxt() {
        return this.withtxt;
    }
    public void setWithtxt(String withtxt) {
        this.withtxt = withtxt;
    }
    public String getPicurl() {
        return this.picurl;
    }
    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 347407384)
    public LAdWelcome(Long id, String picurl, String withtxt, String withurl) {
        this.id = id;
        this.picurl = picurl;
        this.withtxt = withtxt;
        this.withurl = withurl;
    }
    @Generated(hash = 840991997)
    public LAdWelcome() {
    }
}
