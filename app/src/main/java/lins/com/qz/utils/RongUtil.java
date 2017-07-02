package lins.com.qz.utils;

import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.ui.login.WelcomeActivity;

import static android.R.attr.data;
import static lins.com.qz.Config.USER_NAME;
import static lins.com.qz.Config.USER_PWD;

/**
 * Created by LINS on 2017/6/13.
 */

public class RongUtil {

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * callback 连接回调。
     * @return RongIM  客户端核心类的实例。
     */
    public static void connectRong(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                App.e("RongCloud","--error---connect");
                //重新获取token；传值：通过生成的UUID与用户名生成对应的融云id
                VolleyUtil.getInstance(App.getContext()).getToken(App.getSharedData(Config.USER_RONG_UUID), App.getSharedData(USER_NAME));

            }
            @Override
            public void onSuccess(String userid) {
                App.e("RongCloud","--onSuccess---融云帐号登录id：" + userid+"__"+App.getSharedData(Config.USER_NAME));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });

    }
}
