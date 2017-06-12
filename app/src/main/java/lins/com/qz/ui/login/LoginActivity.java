package lins.com.qz.ui.login;

import android.databinding.DataBindingUtil;
import android.view.View;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
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
}
