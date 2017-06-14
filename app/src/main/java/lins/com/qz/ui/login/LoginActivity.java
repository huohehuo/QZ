package lins.com.qz.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import java.io.IOException;

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
import lins.com.qz.utils.RongUtil;
import lins.com.qz.utils.SharedData;
import lins.com.qz.utils.VolleyUtil;

//登录
public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    private SharedData data;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        data = new SharedData(App.getContext());
    }

    @Override
    protected void initEvent() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("正在登录");
                BmobUser.loginByAccount(binding.etUsername.getText().toString(),
                        binding.etPassword.getText().toString(),
                        new LogInListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if (e == null) {
                                    data.putString(Config.USER_NAME, binding.etUsername.getText().toString());
                                    data.putString(Config.USER_PWD, binding.etPassword.getText().toString());
                                    if (App.getHashData(Config.HAVE_RONG_TOKEN) == null || "".equals(App.getHashData(Config.HAVE_RONG_TOKEN))) {
                                        VolleyUtil.getToken(LoginActivity.this, binding.etUsername.getText().toString(), binding.etUsername.getText().toString());
                                    }else{
                                        RongUtil.connectRong(App.getSharedData(Config.HAVE_RONG_TOKEN));
                                    }

                                    App.setSharedData(Config.IS_AUTO_LOGIN, "1");
                                    startActivity(MainActivity.class);
                                    finish();
                                } else {
                                    showToast("登录失败");
                                }
                                closeDialog();
                            }
                        });
            }
        });
        startActivityWith(RegisterActivity.class, binding.btnRegis);
    }

    @Override
    protected void getData() {

    }
}
