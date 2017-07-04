package lins.com.qz.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**             发布的计划
 * Created by LINS on 2017/7/4.
 */

public class PlanBean extends BmobObject{
    private String essay;
    private String bg_pic;


    private User author;//帖子的发布者，这里体现的是一对一的关系，该帖子属于某个用户
    private BmobRelation favour;//多对多关系：用于存储喜欢该帖子的所有用户


    public String getEssay() {
        return essay;
    }

    public void setEssay(String essay) {
        this.essay = essay;
    }

    public String getBg_pic() {
        return bg_pic;
    }

    public void setBg_pic(String bg_pic) {
        this.bg_pic = bg_pic;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public BmobRelation getFavour() {
        return favour;
    }

    public void setFavour(BmobRelation favour) {
        this.favour = favour;
    }
}
