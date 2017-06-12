package lins.com.qz.ui.login;

import android.databinding.DataBindingUtil;
import android.view.View;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import lins.com.qz.R;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.bean.User;
import lins.com.qz.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity {
    ActivityRegisterBinding binding;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
    }

    @Override
    protected void initEvent() {
        binding.btnRegisterReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("正在注册");
                BmobUser user = new BmobUser();
                user.setUsername(binding.etUsernameReg.getText().toString());
                user.setPassword(binding.etPasswordReg.getText().toString());
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null){
                            showToast("注册成功");
                            finish();
                        }else{
                            showToast("注册失败");
                        }
                        closeDialog();
                    }
                });
            }
        });
    }

    @Override
    protected void getData() {

    }
}
