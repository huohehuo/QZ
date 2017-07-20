package lins.com.qz.utils.share;

import android.os.Bundle;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;

/**
 * Created by LINS on 2017/7/18.
 */

public class ShareUtil {

//    public void share2QQ( int type, String title,String headurl,) {
//        if (mTencent.isSessionValid()) {
//            Toast.makeText(activity, "您还未安装QQ", Toast.LENGTH_SHORT).show();
//        }
//        final Bundle params = new Bundle();
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,QQShare.SHARE_TO_QQ_TYPE_DEFAULT );
//        params.putString(QQShare.SHARE_TO_QQ_TITLE,title );//必填
//        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.36glive.com/");
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, headurl);
//        switch (type){
//            //分享到QQ好友
//            case 0:
//                mTencent.shareToQQ(activity,params,iUiListener);
//                break;
//            //分享到空间
//            case 1:
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);//自动启动QQ空间分享界面
//                mTencent.shareToQQ(activity,params,iUiListener);
//                break;
//        }
//    }
}
