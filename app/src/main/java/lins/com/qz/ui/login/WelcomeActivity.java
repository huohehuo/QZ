package lins.com.qz.ui.login;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.Log;
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
import lins.com.qz.bean.locationBean.LUser;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.bean.User;
import lins.com.qz.databinding.ActivityWelcomeBinding;
import lins.com.qz.utils.RongUtil;
import lins.com.qz.utils.SaveService;
import lins.com.qz.utils.SharedData;
import lins.com.qz.utils.VolleyUtil;

import static lins.com.qz.Config.USER_NAME;
import static lins.com.qz.Config.USER_PWD;

public class WelcomeActivity extends BaseActivity {
    ActivityWelcomeBinding binding;
    private Animation animation;
    private Handler handler = new Handler();
    @Override
    protected void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_welcome);
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
        if ("1".equals(App.getSharedData(Config.IS_AUTO_LOGIN))){
            Log.e("自动登录",App.getSharedData(USER_NAME));
            App.e("welcomeLogin","自动登录"+App.getSharedData(USER_NAME));
            BmobUser.loginByAccount(App.getSharedData(USER_NAME),
                    App.getSharedData(USER_PWD),
                    new LogInListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null){
                                //将用户基本信息存入本地
                                SaveService.startSaveLocationUser(App.getContext(),user.getAge(),
                                        user.getSex(),user.getNote(),user.getIconpic(),user.getRongid());
                                //检查Im是否存在Token。存在就登录，否则服务器重新获取
                                checkIM(user.getRongid());

                                startActivity(MainActivity.class);
                                finish();
                            }else{
                                App.e("welcome","登录失败："+e.toString());
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
