package lins.com.qz.bean.locationBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LINS on 2017/6/18.
 */
@Entity
public class LUser {
    @Id
    private Long id;

    private String objectid;
    private String username;
    private String sex;
    private String age;
    private String note;
    private String phone;
    private String email;
    private String iconurl;
    public String getIconurl() {
        return this.iconurl;
    }
    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getNote() {
        return this.note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getObjectid() {
        return this.objectid;
    }
    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1410548009)
    public LUser(Long id, String objectid, String username, String sex, String age,
            String note, String phone, String email, String iconurl) {
        this.id = id;
        this.objectid = objectid;
        this.username = username;
        this.sex = sex;
        this.age = age;
        this.note = note;
        this.phone = phone;
        this.email = email;
        this.iconurl = iconurl;
    }
    @Generated(hash = 388715034)
    public LUser() {
    }

    @Override
    public String toString() {
        return "LUser{" +
                "objectid='" + objectid + '\'' +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", note='" + note + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", iconurl='" + iconurl + '\'' +
                '}';
    }
}
