package lins.com.qz.utils.share;

import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by Administrator on 2017/3/9.
 */

public class QShareListener implements IUiListener {

    private QShareIO dataIO;
    public QShareListener(QShareIO dataIO){
        this.dataIO = dataIO;
    }
    @Override
    public void onComplete(Object o) {
        Log.e("QShare-onComplete",o.toString());
//        App.showToast("QQ分享成功"+o.toString());
        dataIO.getShareData("QQ分享成功"+o.toString());
    }
    @Override
    public void onError(UiError uiError) {
        Log.e("QShare-onError",uiError.toString());
//        App.showToast("QQ分享失败");
        dataIO.getShareData("QQ分享回调信息"+uiError.toString());

    }
    @Override
    public void onCancel() {
        Log.e("QShare-onCancel","取消QQ分享");
//        App.showToast("取消QQ分享");
    }
}
