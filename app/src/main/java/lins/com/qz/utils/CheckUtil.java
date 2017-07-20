package lins.com.qz.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import lins.com.qz.App;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by LINS on 2017/5/10.
 */

public class CheckUtil {

    /*
     * 判断服务是否启动,context上下文对象 ，className服务的name
     */
    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }


    private static ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    public static boolean isNetworkAvailable() {
        if (connectivityManager == null) {
            return false;
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 connectivityManager.getActiveNetworkInfo().isAvailable();
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();//判断是否有网
            }
        }
        return false;
    }
    public static boolean is3rd() {
        NetworkInfo networkINfo = connectivityManager.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }
    public static boolean isWifi() {
        NetworkInfo networkINfo = connectivityManager.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
    //扫描所有网络接口，查看是否有使用VPN的（接口名为tun0或ppp0）
    public static boolean isVpnConnected() {
        try {
            Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
            if (niList != null) {
                for (NetworkInterface intf : Collections.list(niList)) {
                    if (!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())) {
                        return true;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }
    //用于判断服务器是否可以连接（成功200）
    public static boolean isConnectionOK(String url) {
        HttpURLConnection connection = null;
        try {
            URL server = new URL(url);
            connection = (HttpURLConnection) server.openConnection();
            connection.setRequestMethod("GET");
            // 判断请求是否成功
            Log.e("http",connection.getResponseCode()+"");
            if (connection.getResponseCode() == 200) {
                // 成功
                Log.e("http","请求成功");
                connection.disconnect();
                return true;
            } else {
                Log.e("http","请求失败");
                connection.disconnect();
                return false;
            }
            // 关闭连接
        } catch (IOException e) {
            e.printStackTrace();
            connection.disconnect();
        }
        return false;
    }
    //用于判断服务器是否可以连接（成功200）
    public static boolean isConnectionYouToBe(String url) {
        HttpURLConnection connection = null;
        try {
            URL server = new URL(url);
            connection = (HttpURLConnection) server.openConnection();
            connection.setRequestMethod("GET");
            // 判断请求是否成功
            Log.e("http",connection.getResponseCode()+"");
            if (connection.getResponseCode() == 200) {
                // 成功
                Log.e("http","请求成功");
                connection.disconnect();
                return true;
            } else {
                Log.e("http","请求失败");
                connection.disconnect();
                return false;
            }
            // 关闭连接
        } catch (IOException e) {
            e.printStackTrace();
            connection.disconnect();
        }
        return false;
    }


    //返回版本名称
    public static String getVersionName(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi.versionName;
    }
    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }
    //获取手机的ip地址
    public static String getPhoneIp() {
        if (is3rd()){
            Log.e("net","当前为：移动网络");
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface
                        .getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf
                            .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()
                                && inetAddress instanceof Inet4Address) {
                            // if (!inetAddress.isLoopbackAddress() && inetAddress
                            // instanceof Inet6Address) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (isWifi()){
            Log.e("net","当前为：WIFI网络");
            WifiManager wifiManager = (WifiManager) App.getContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String str=(ipAddress & 0xFF)+ "." +((ipAddress >> 8) & 0xFF)+ "." + ((ipAddress >> 16) & 0xFF)+ "."
                    + (((ipAddress >> 24) & 0xFF));
            //此处格式化ip地址，app端应与服务器连接在统一网络，并且ip要改成服务器电脑一样的ip地址才能有效连接
            if (str!=null){
                return str;
            }else{
                return "0.0.0.0";
            }
        }else{
            Log.e("net","当前为：位置网络");
            return "0.0.0.0";
        }
        return "0.0.0.0";
    }

}
