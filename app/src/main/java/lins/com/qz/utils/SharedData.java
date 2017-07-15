package lins.com.qz.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

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

//    public void saveUser(User user){
//        kv.edit().putString(Config.USER_NAME,user.getUsername()).apply();
//        kv.edit().putString(Config.USER_SEX,user.getSex()).apply();
//        kv.edit().putString(Config.USER_HEAD_ICON,user.getIconpic()).apply();
//    }





    /**
     * 根据key和预期的value类型获取value的值
     *
     * @param key
     * @param clazz
     * @return
     */
//    public <T> T getValue(String key, Class<T> clazz) {
//        if (mContext == null) {
//            throw new RuntimeException("请先调用带有mContext，name参数的构造！");
//        }
//        SharedPreferences sp = this.mContext.getSharedPreferences(this.name, Context.MODE_PRIVATE);
//        return getValue(key, clazz, sp);
//    }

    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param object
     */
    public void setObject(String key, Object object) {
//        SharedPreferences kv = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);

        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //然后通过将字对象进行64转码，写入key值为key的kv中
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = kv.edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String key) {
//        SharedPreferences kv = this.mContext.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        if (kv.contains(key)) {
            String objectVal = kv.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 对于外部不可见的过渡方法
     *
     * @param key
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> clazz) {
        T t;
        try {

            t = clazz.newInstance();

            if (t instanceof Integer) {
                return (T) Integer.valueOf(kv.getInt(key, 0));
            } else if (t instanceof String) {
                return (T) kv.getString(key, "");
            } else if (t instanceof Boolean) {
                return (T) Boolean.valueOf(kv.getBoolean(key, false));
            } else if (t instanceof Long) {
                return (T) Long.valueOf(kv.getLong(key, 0L));
            } else if (t instanceof Float) {
                return (T) Float.valueOf(kv.getFloat(key, 0L));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
        }
        Log.e("system", "无法找到" + key + "对应的值");
        return null;
    }
}
