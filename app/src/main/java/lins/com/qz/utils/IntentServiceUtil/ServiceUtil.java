package lins.com.qz.utils.IntentServiceUtil;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.bean.AddAddress;

public class ServiceUtil extends Service {
    private Handler handler;
    private BroadcastReceiver broadcastReceiver;
    public ServiceUtil() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    public static void startServiceUtil(Context context){
        Intent intent = new Intent(context,ServiceUtil.class);
        intent.setAction(Config.MAIN_SERVICE_ACTION);
        context.startService(intent);
    }

    public static void stopServiceUtil(Context context){
        Intent intent = new Intent(context,ServiceUtil.class);
        intent.setAction(Config.MAIN_SERVICE_ACTION);
        context.stopService(intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("serviceUtil","服务器onCreate");
        //注册广播（服务中的广播）
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.RECEIVER_IN_SERVICE);
        registerReceiver(broadcastReceiver,intentFilter);

        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler.post(runnable);

        Log.e("serviceUtil","服务器onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getList();
            handler.postDelayed(runnable,2000);
        }
    };
    @Override
    public void onDestroy() {
        Log.e("service","停止服务");
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    private void getList(){
        BmobQuery<AddAddress> shuoBmobQuery = new BmobQuery<>();
//        shuoBmobQuery.addWhereEqualTo("addr","all");
//        shuoBmobQuery.setLimit(App.getHashMainNum(Config.NUM_OF_MAIN));
        shuoBmobQuery.findObjects(new FindListener<AddAddress>() {
            @Override
            public void done(List<AddAddress> list, BmobException e) {
                if (e == null) {
                    Log.e("service","service_获取地址列表_success");
                    if (App.getHashData(Config.ADDRESS_LIST)!=null
                            &&((List<AddAddress>)App.getHashData(Config.ADDRESS_LIST)).toString().equals(list.toString())){
                        Log.e("service","地址列表变化");
                        App.setHashData(Config.ADDRESS_LIST,list);//把列表存进Hash表
                        App.setSharedData(Config.AT_ADDRESS,list.get(0).getAddr());//存某个地址到SharedPerference
                        //发送广播，使主页面的地址列表数据更新
                        Intent intent = new Intent(Config.MAIN_RECEIVER_ACTION);
                        intent.putExtra("content","0");
                        sendBroadcast(intent);
                    }
                } else {
                    Log.e("service","service_获取地址列表_error");
                }

            }
        });
    }
}
