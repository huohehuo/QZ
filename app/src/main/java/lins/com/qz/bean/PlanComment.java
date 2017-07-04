package lins.com.qz.bean;

import cn.bmob.v3.BmobObject;
import lins.com.qz.bean.PlanBean;
import lins.com.qz.bean.User;

/**
 * Created by LINS on 2017/7/4.
 */

public class PlanComment extends BmobObject{

    private String content;//评论内容

    private User user;//评论的用户，Pointer类型，一对一关系

    private PlanBean plan; //所评论的帖子，这里体现的是一对多的关系，一个评论只能属于一个微博

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PlanBean getPlan() {
        return plan;
    }

    public void setPlan(PlanBean plan) {
        this.plan = plan;
    }
}
