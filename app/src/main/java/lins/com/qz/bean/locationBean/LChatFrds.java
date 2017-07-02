package lins.com.qz.bean.locationBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**             聊天好友列表
 * Created by LINS on 2017/7/2.
 */
@Entity
public class LChatFrds {
    @Id
    private Long id;

    private String name;
    private String rongid;
    private String head_icon;
    public String getHead_icon() {
        return this.head_icon;
    }
    public void setHead_icon(String head_icon) {
        this.head_icon = head_icon;
    }
    public String getRongid() {
        return this.rongid;
    }
    public void setRongid(String rongid) {
        this.rongid = rongid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1004829530)
    public LChatFrds(Long id, String name, String rongid, String head_icon) {
        this.id = id;
        this.name = name;
        this.rongid = rongid;
        this.head_icon = head_icon;
    }
    @Generated(hash = 1486624902)
    public LChatFrds() {
    }

    @Override
    public String toString() {
        return "LChatFrds{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rongid='" + rongid + '\'' +
                ", head_icon='" + head_icon + '\'' +
                '}';
    }
}
