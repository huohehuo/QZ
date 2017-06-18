package lins.com.qz.bean.locationBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LINS on 2017/6/18.
 */

@Entity
public class LFriend {
    @Id
    private Long id;


    private String whofriendsid;
    private String thefriendsid;
    private String thefriendsname;
    private String thefriendssex;
    private String thefriendsnote;
    private String thefriendsiconurl;
    public String getThefriendsiconurl() {
        return this.thefriendsiconurl;
    }
    public void setThefriendsiconurl(String thefriendsiconurl) {
        this.thefriendsiconurl = thefriendsiconurl;
    }
    public String getThefriendsnote() {
        return this.thefriendsnote;
    }
    public void setThefriendsnote(String thefriendsnote) {
        this.thefriendsnote = thefriendsnote;
    }
    public String getThefriendssex() {
        return this.thefriendssex;
    }
    public void setThefriendssex(String thefriendssex) {
        this.thefriendssex = thefriendssex;
    }
    public String getThefriendsname() {
        return this.thefriendsname;
    }
    public void setThefriendsname(String thefriendsname) {
        this.thefriendsname = thefriendsname;
    }
    public String getThefriendsid() {
        return this.thefriendsid;
    }
    public void setThefriendsid(String thefriendsid) {
        this.thefriendsid = thefriendsid;
    }
    public String getWhofriendsid() {
        return this.whofriendsid;
    }
    public void setWhofriendsid(String whofriendsid) {
        this.whofriendsid = whofriendsid;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 2067169897)
    public LFriend(Long id, String whofriendsid, String thefriendsid,
            String thefriendsname, String thefriendssex, String thefriendsnote,
            String thefriendsiconurl) {
        this.id = id;
        this.whofriendsid = whofriendsid;
        this.thefriendsid = thefriendsid;
        this.thefriendsname = thefriendsname;
        this.thefriendssex = thefriendssex;
        this.thefriendsnote = thefriendsnote;
        this.thefriendsiconurl = thefriendsiconurl;
    }
    @Generated(hash = 1224638203)
    public LFriend() {
    }


}
