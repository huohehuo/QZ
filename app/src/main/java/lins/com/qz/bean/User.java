package lins.com.qz.bean;

import cn.bmob.v3.BmobUser;

import static android.R.attr.name;

/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class User extends BmobUser {
    private String sex;
    private String age;
    private String shuoshuo;
    private String born;
    private String like;
    private String pic;

    public User(){}

    public User(String sex, String age, String shuoshuo, String born, String like) {
        this.sex = sex;
        this.age = age;
        this.shuoshuo = shuoshuo;
        this.born = born;
        this.like = like;
    }

    public String getShuoshuo() {
        return shuoshuo;
    }

    public void setShuoshuo(String shuoshuo) {
        this.shuoshuo = shuoshuo;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
