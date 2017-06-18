package lins.com.qz.utils;

import android.content.Context;
import android.content.SharedPreferences;

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

}
