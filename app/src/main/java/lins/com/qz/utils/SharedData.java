package lins.com.qz.utils;

import android.content.Context;
import android.content.SharedPreferences;

import lins.com.qz.Config;
import lins.com.qz.bean.User;

/**
 * Created by aid on 9/18/16.
 */
public class SharedData {

    private static final String NAME = "LiveKeyValueStorage";

    private Context mContext;
    // 注意SharedPreferences并非线程安全的
    private SharedPreferences kv;

    public SharedData(Context context) {
        mContext = context;
        kv = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value){
        kv.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue){
        return kv.getString(key, defaultValue);
    }
    public void clearShareData(){
        kv.getAll().clear();
    }

    public void saveUser(User user){
        kv.edit().putString(Config.USER_NAME,user.getUsername()).apply();
        kv.edit().putString(Config.USER_SEX,user.getSex()).apply();
        kv.edit().putString(Config.USER_HEAD_ICON,user.getIconpic()).apply();
    }

}
