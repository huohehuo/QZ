package lins.com.qz.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.MainActivity;
import lins.com.qz.R;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.bean.User;
import lins.com.qz.databinding.ActivityLoginBinding;
import lins.com.qz.utils.SharedData;

//登录
public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    private SharedData data;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
         data= new SharedData(App.getContext());
    }

    @Override
    protected void initEvent() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect("Bg7E2wRYSWmfIP70Ve9kt4jNf8Zdbl73VSyH/x2R72dgAgqdBUkeec2GL1OHBmvgu/avZYdU0yBH1ITxNZabkA==");
                showDialog("正在登录");
                BmobUser.loginByAccount(binding.etUsername.getText().toString(),
                        binding.etPassword.getText().toString(),
                        new LogInListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if (e == null){
                                    data.putString(Config.USER_NAME,binding.etUsername.getText().toString());
                                    data.putString(Config.USER_PWD,binding.etPassword.getText().toString());
                                    App.setSharedData(Config.IS_AUTO_LOGIN,"1");
                                    startActivity(MainActivity.class);
                                    finish();
                                }else{
                                    showToast("登录失败");
                                }
                                closeDialog();
                            }
                        });
            }
        });
        startActivityWith(RegisterActivity.class,binding.btnRegis);
    }

    @Override
    protected void getData() {

    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * callback 连接回调。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }
                @Override
                public void onSuccess(String userid) {
                    Log.d("RongCloud", "--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                }
            });

    }
}
