package lins.com.qz.bean.locationBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LINS on 2017/7/23.
 */

@Entity
public class LMapCity {
    @Id
    private Long id;

    private String city;
    private String code;
    private String jianpin;
    private String pinyin;
    public String getPinyin() {
        return this.pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public String getJianpin() {
        return this.jianpin;
    }
    public void setJianpin(String jianpin) {
        this.jianpin = jianpin;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 584263076)
    public LMapCity(Long id, String city, String code, String jianpin, String pinyin) {
        this.id = id;
        this.city = city;
        this.code = code;
        this.jianpin = jianpin;
        this.pinyin = pinyin;
    }
    @Generated(hash = 1618805255)
    public LMapCity() {
    }


}
