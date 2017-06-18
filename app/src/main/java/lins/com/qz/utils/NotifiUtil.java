package lins.com.qz.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import lins.com.qz.App;
import lins.com.qz.MainActivity;
import lins.com.qz.R;

/**
 * Created by LINS on 2017/6/17.
 */

public class NotifiUtil {
    //没流量时的通知提示(跳转至MainActivity)
    public static void NoFlowNotification(NotificationManager notificationManager){
        long[] vibrate = {0, 100, 200, 200};//震动模式
        PendingIntent pendingIntent = PendingIntent.getActivity(App.getContext(), 0, new Intent(App.getContext(), MainActivity.class), 0);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(App.getContext())
                .setAutoCancel(true)
                .setVibrate(vibrate)
                .setContentTitle(App.getContext().getString(R.string.about_help))
                .setContentText(App.getContext().getString(R.string.about_help))
                .setContentIntent(pendingIntent);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // SDK < 6.0:解决谷歌手机通知栏图标空白问题
            notification.setSmallIcon(R.mipmap.ic_launcher);
        }else{
            notification.setSmallIcon(R.mipmap.ic_launcher);
        }
        Log.e("noti","通知无流量");
        notificationManager.notify(R.layout.activity_account, notification.build());//发送通知
    }
}