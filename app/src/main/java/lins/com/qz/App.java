package lins.com.qz;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.tencent.tauth.Tencent;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import lins.com.qz.bean.AddAddress;
import lins.com.qz.bean.gen.DaoMaster;
import lins.com.qz.bean.gen.DaoSession;
import lins.com.qz.manager.DaoManager;
import lins.com.qz.manager.GreenDaoManager;
import lins.com.qz.manager.UpgradeHelper;
import lins.com.qz.utils.SharedData;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by LINS on 2017/5/2.
 */

public class App extends MultiDexApplication {
    // 用来保存整个应用程序数据
    private static HashMap<String, Object> allData = new HashMap<String, Object>();
    private static Context mContext;
    private static Tencent mTencent;
    private DaoSession mDaoSession;
    private static DaoManager daoManager;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        GreenDaoManager.getInstance();
        initDaoSession();
        Bmob.initialize(this,"33e6d0b9cc101e2998dc0493a98e1b5d");
//        cn.bmob.v3.statistics.AppStat.i(appKey, channel);
        JPushInterface.init(this);
        RongIM.init(this);

        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(Config.QQ_ID, this);

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
    public static void clearShareData(){
        new SharedData(mContext).clearShareData();
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
    public static Tencent getTencent(){
        return mTencent;
    }

    public static String getObjectId(){
        return BmobUser.getCurrentUser().getObjectId();
    }


    public static DaoManager getDaoManager() {
        if (daoManager == null) {
            daoManager = new DaoManager();
        }
        return daoManager;
    }


    /*
         * GreenDao相关
         */
//    public synchronized DaoSession getDaoSession() {
//        if (mDaoSession == null) {
//            initDaoSession();
//        }
//        return mDaoSession;
//    }

    private void initDaoSession() {
        // 相当于得到数据库帮助对象，用于便捷获取db
        // 这里会自动执行upgrade的逻辑.backup all table→del all table→create all new table→restore data
        UpgradeHelper helper = new UpgradeHelper(this, "note.db", null);
        // 得到可写的数据库操作对象
        SQLiteDatabase db = helper.getWritableDatabase();
        // 获得Master实例,相当于给database包装工具
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取类似于缓存管理器,提供各表的DAO类
        mDaoSession = daoMaster.newSession();
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
