package lins.com.qz.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by LINS on 2017/6/18.
 */

public class Friend extends BmobObject{

    private List<DataBean> friends;


    public List<DataBean> getFriends() {
        return friends;
    }

    public void setFriends(List<DataBean> friends) {
        this.friends = friends;
    }

    public class DataBean{
        private String name;
        private String rongid;
        private String head_icon;

        public DataBean(String rongid, String name, String head_icon) {
            this.rongid = rongid;
            this.name = name;
            this.head_icon = head_icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRongid() {
            return rongid;
        }

        public void setRongid(String rongid) {
            this.rongid = rongid;
        }

        public String getHead_icon() {
            return head_icon;
        }

        public void setHead_icon(String head_icon) {
            this.head_icon = head_icon;
        }
    }
}
