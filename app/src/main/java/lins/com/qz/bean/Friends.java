package lins.com.qz.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

import static android.R.attr.x;

/**
 * Created by LINS on 2017/6/18.
 */

public class Friends extends BmobObject{

    private String whosfriendid;
    private List<Friend> friendlist;

    public String getWhosfriendid() {
        return whosfriendid;
    }

    public void setWhosfriendid(String whosfriendid) {
        this.whosfriendid = whosfriendid;
    }

    public List<Friend> getFriendList() {
        return friendlist;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendlist = friendList;
    }


//    public class Friend {
//        private String objid;
//        private String fname;
//        private String ficonurl;
//        private String fnote;
//
//        public Friend(String objid, String fname, String ficonurl, String fnote) {
//            this.objid = objid;
//            this.fname = fname;
//            this.ficonurl = ficonurl;
//            this.fnote = fnote;
//        }
//
//        public String getObjid() {
//            return objid;
//        }
//
//        public void setObjid(String objid) {
//            this.objid = objid;
//        }
//
//        public String getFname() {
//            return fname;
//        }
//
//        public void setFname(String fname) {
//            this.fname = fname;
//        }
//
//        public String getFiconurl() {
//            return ficonurl;
//        }
//
//        public void setFiconurl(String ficonurl) {
//            this.ficonurl = ficonurl;
//        }
//
//        public String getFnote() {
//            return fnote;
//        }
//
//        public void setFnote(String fnote) {
//            this.fnote = fnote;
//        }
//    }
}
