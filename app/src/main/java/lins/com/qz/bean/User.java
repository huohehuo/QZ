package lins.com.qz.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

import static android.R.attr.name;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class User extends BmobUser implements Serializable{
    private String nickname;
    private String sex;
    private String age;
    private String rongid;//为一个唯一的UUID
    private String note;
    private String iconpic;

    public User(){}

    public User(String nickname,String sex, String age, String note, String iconpic) {
        this.nickname = nickname;
        this.sex = sex;
        this.age = age;
        this.note = note;
        this.iconpic = iconpic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIconpic() {
        return iconpic;
    }

    public void setIconpic(String iconpic) {
        this.iconpic = iconpic;
    }

    public String getRongid() {
        return rongid;
    }

    public void setRongid(String rongid) {
        this.rongid = rongid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", rongid='" + rongid + '\'' +
                ", note='" + note + '\'' +
                ", iconpic='" + iconpic + '\'' +
                '}';
    }
}
