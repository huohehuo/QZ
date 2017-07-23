package lins.com.qz.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.R;
import lins.com.qz.bean.User;
import lins.com.qz.ui.login.WelcomeActivity;
import lins.com.qz.utils.AppManager;
import lins.com.qz.utils.DialogUtils;
import lins.com.qz.utils.RongUtil;
import lins.com.qz.utils.VolleyUtil;

import static lins.com.qz.App.userName;
import static lins.com.qz.Config.USER_NAME;
import static lins.com.qz.Config.USER_PWD;


/**
 * Created by LINS on 2017/4/8.
 */

public abstract class BaseActivity extends AppCompatActivity {
//    public String TGP = this.getA
    private Toast toast;
    private Dialog dialog;
    public BmobUser bmobUser;
    public User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉actionBar
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        AppManager.getAppManager().addActivity(this);
        bmobUser = BmobUser.getCurrentUser();
        user = App.getObj(Config.OBJ_USER);
//        Log.e("获取到User对象：",user.toString());
        if (user==null){
            user=new User("","","","","");
        }
        initView();//初始化页面
        initEvent();//初始化事件
        getData();//加载数据

    }

    public void initMap(Bundle bundle){

    }


    //设置基本的返回键，结束Activity
    public void setToolbarBack(ImageView view){
        view.setImageResource(R.drawable.back);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

//    public void setToolBar(String )
    /**
     * 跳转至Activity
     * @param pClass
     */
    public void startActivity(Class<?> pClass){
        startActivity(new Intent(this,pClass));
    }
    /**
     * 自定义Toast消息展示
     * @param msg
     */
    public void showToast(String msg){
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
    //长时间的Toast
    public void showToast(String msg,int type){
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
    public void showToast(int resId){
        showToast(getString(resId));
    }

    public void showBottomToast(View view,String str){
        Snackbar snackbar= Snackbar.make(view,str, Snackbar.LENGTH_SHORT).setDuration(3000);
        setSnackbarMessageTextColor(snackbar,getResources().getColor(R.color.item_weibo));
        snackbar.show();
    }
    //设置Snackbar的字体颜色
    public static void setSnackbarMessageTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }
    public void startActivityWith(final Class<?> pClass, View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(App.getContext(),pClass));
            }
        });
    }

    /**
     * 封装Activity关闭方法
     * @param view
     */
    public void closeActivity(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void showDialog(String str){
        dialog = DialogUtils.createLoadingDialog(this,str);
    }
    public void closeDialog(){
        if (dialog!=null){
            DialogUtils.closeDialog(dialog);
        }
    }
    public void closeDialgWithActivity(){
        if (dialog!=null){
            DialogUtils.closeDialog(dialog);
            finish();
        }
    }

    public void checkIM(String rongid){
        String token =App.getSharedData(Config.HAVE_RONG_TOKEN);
        if (rongid==null){
            Log.e("checkIM","rongid is null");
            return;
        }
        if ("".equals(rongid)){
            Log.e("checkIM","rongid is nothing");
            return;
        }
        //判断是否存在融云token，连接登录
        if (token != null && !"".equals(token)) {
            //存在
            RongUtil.connectRong(token);
        }else{
            //否则，获取本地保存的融云id（实际是之前通过UUID生成的，并传至服务器的字段）
            App.setSharedData(Config.USER_RONG_UUID,rongid);//将rongid存入本地
//            Log.e("无Token","重新获取Token";
            App.e("BaseActivity","本地无token,重新从融云服务器获取");
            //执行网络操作：从融云服务器获取Token
            VolleyUtil.getInstance(App.getContext())
                    .getToken(rongid, App.getSharedData(USER_NAME));
        }
    }






    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }







    protected abstract void initView();
    protected abstract void initEvent();
    protected abstract void getData();
}
