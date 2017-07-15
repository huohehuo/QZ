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
import lins.com.qz.utils.SaveService;
import lins.com.qz.utils.SharedData;
import lins.com.qz.utils.VolleyUtil;

import static lins.com.qz.Config.USER_NAME;
import static lins.com.qz.Config.USER_PWD;

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
                                    App.saveObj(Config.OBJ_USER,user);
//                                    App.setUser(user);
                                    App.e("Login","登录获的user"+user.toString()+"\n");
                                    App.setSharedData(Config.HAVE_RONG_TOKEN,"");//清空token，防止登录IM错乱
                                    App.setSharedData(Config.USER_NAME, binding.etUsername.getText().toString());
                                    App.setSharedData(Config.USER_PWD, binding.etPassword.getText().toString());
                                    //将数据存入本地
                                    SaveService.startSaveLocationUser(App.getContext(),user.getAge(),
                                            user.getSex(),user.getNote(),user.getIconpic(),user.getRongid());
                                    //若无token，则从融云服务器获取token
                                    checkIM(user.getRongid());

                                    //设置为下次自动登录
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
