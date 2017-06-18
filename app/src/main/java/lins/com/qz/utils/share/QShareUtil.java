package lins.com.qz.utils.share;

import android.os.Bundle;

import com.tencent.connect.share.QzoneShare;

import java.util.ArrayList;

import lins.com.qz.App;

/**
 * Created by LINS on 2017/6/17.
 */

public class QShareUtil {


    public void share(){
        final Bundle params2 = new Bundle();
        ArrayList<String> aa = new ArrayList<>();
        aa.add("http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params2.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,"heheh");
        params2.putString(QzoneShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params2.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
        params2.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");
        params2.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, aa);
//        App.getTencent().shareToQzone(App.getContext(), params2, new QLoginListener(dataIO));
    }
}
