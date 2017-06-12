package lins.com.qz.ui.login;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.MainActivity;
import lins.com.qz.R;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.bean.User;
import lins.com.qz.databinding.ActivityWelcomeBinding;
import lins.com.qz.utils.SharedData;

import static lins.com.qz.Config.USER_NAME;
import static lins.com.qz.Config.USER_PWD;

public class WelcomeActivity extends BaseActivity {
    ActivityWelcomeBinding binding;
    private SharedData data;
    private Animation animation;
    private Handler handler = new Handler();
    @Override
    protected void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_welcome);
        data= new SharedData(App.getContext());
    }

    //执行登录操作
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            autoLogin();
        }
    };
    @Override
    protected void initEvent() {

    }

    @Override
    protected void getData() {

        animation = AnimationUtils.loadAnimation(this, R.anim.logo_and_text);
        //为动画设置监听事件
        animation.setAnimationListener(animationListener);
        //给logo图片设置渐变动画效果
        binding.ll.startAnimation(animation);

    }
    //跳过登陆界面，自动登录
    private void autoLogin(){
        if ("1".equals(data.getString(Config.IS_AUTO_LOGIN,"0"))){

            BmobUser.loginByAccount(data.getString(USER_NAME,""),
                    data.getString(USER_PWD,""),
                    new LogInListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null){
                                startActivity(MainActivity.class);
                                finish();
                            }else{
                                showToast("登录失败");
                                startActivity(LoginActivity.class);
                                finish();
                            }
                        }
                    });
        }else{
            startActivity(LoginActivity.class);
            finish();
        }
    }
    //设置动画监听器AnimationListener,需要重写三个方法
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            //当动画开始的时候执行
            binding.ll.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //当动画结束的时候执行
            handler.postDelayed(runnable,100);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            //当动画重复的时候执行
        }
    };
}
