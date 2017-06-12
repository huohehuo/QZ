package lins.com.qz;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import lins.com.qz.bean.AddAddress;
import lins.com.qz.utils.SharedData;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by LINS on 2017/5/2.
 */

public class App extends Application{
    // 用来保存整个应用程序数据
    private static HashMap<String, Object> allData = new HashMap<String, Object>();
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Bmob.initialize(this,"33e6d0b9cc101e2998dc0493a98e1b5d");
        JPushInterface.init(this);
        RongIM.init(this);

    }
    public static Context getContext() {
        return mContext;
    }

    public static String getSharedData(String key) {
        return new SharedData(mContext).getString(key,"0");
    }

    public static void setSharedData(String key,String autoLogin) {
        new SharedData(mContext).putString(key,autoLogin);
    }

    //    public static String getIsVip(){
//        return new KeyValueStorage(getContext()).getString(Config.IS_VIP,"");
//    }
//    public static void setIsVip(){
//        new KeyValueStorage(getContext()).putString(Config.IS_VIP,"");
//    }

    // 存数据*/
    public static void setHashData(String key, Object value) {
        allData.put(key, value);
    }

    // 取数据
    public static Object getHashData(String key) {
        if (allData.containsKey(key)) {
            return allData.get(key);
        }
        return null;
    }
    // 删除一条数据
    public static void delHashDataBykey(String key) {
        if (allData.containsKey(key)) {
            allData.remove(key);
        }
    }

    // 删除所有数据
    public static void delAllHashData() {
        allData.clear();
    }






















    // 结束的时候
    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }

    // 配置改变的时候
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }
}
