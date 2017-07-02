package lins.com.qz.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.R;
import com.android.volley.toolbox.HttpHeaderParser;
/**
 * Created by LINS on 2017/6/13.
 */

public class VolleyUtil {
    private static VolleyUtil instance;
    private RequestQueue queue;
    Context context;
    private VolleyUtil(Context context) {
        if (queue ==null){
            queue = Volley.newRequestQueue(context);
        }
        this.context = context;
    }

    public static synchronized VolleyUtil getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyUtil(context);
        }
        return instance;
    }

    //运用Volley框架获取网络数据（默认GET方法）
    public static void testVolley() throws IOException {
        RequestQueue queue = Volley.newRequestQueue(App.getContext());
        String url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // TODO 自动生成的方法存根
                        Log.d("check>>>>>>", response);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO 自动生成的方法存根

            }

        });
        queue.add(stringRequest);

    }

    //POST：获取融云IM的Token
    public void getToken(final String rongid, final String name){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringBuffer res = new StringBuffer();
        String url = "http://api.cn.ronghub.com/user/getToken.json";
        final String App_Key = "mgb7ka1nmfvmg"; //开发者平台分配的 App Key。
        final String App_Secret = "r4vHGteobc";
        final String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。
        final String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//随机数，无长度限制。
        final String Signature = sha1(App_Secret + Nonce + Timestamp);//数据签名。

        // ?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO 自动生成的方法存根
//                        Log.e("连接融云服务器：返回数据：", response);
                        App.e("Volley","连接融云服务器：返回数据："+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            App.e("Volley","将token存入本地："+jsonObject.get("token").toString());
                            //存入本地token
                            App.setSharedData(Config.HAVE_RONG_TOKEN,jsonObject.get("token").toString());
                            //登录融云
                            RongUtil.connectRong(App.getSharedData(Config.HAVE_RONG_TOKEN));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO 自动生成的方法存根
//                Log.e("check>>>>>>", "出错。。。。。。"+error.getMessage());
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                        Log.e("连接融云：错误：",obj.toString());
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("App-Key",App_Key);
//                map.put("appSecret",App_Secret);
                map.put("Nonce",Nonce);
                map.put("Timestamp",Timestamp);
                map.put("Signature",Signature);
                map.put("Content-Type", "application/x-www-form-urlencoded");
                return map;
            }

            // 重写该方法，构造出post请求信息
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // TODO 自动生成的方法存根
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", rongid);
                map.put("name", name);
                map.put("portraitUri", "https://github.com/huohehuo/QZ/blob/master/app/src/main/res/drawable/mricon.png");
                return map;
            }
        };
        queue.add(stringRequest);

    }
    //SHA1加密//http://www.rongcloud.cn/docs/server.html#通用_API_接口签名规则
    private static String sha1(String data){
        StringBuffer buf = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for(int i = 0 ; i < bits.length;i++){
                int a = bits[i];
                if(a<0) a+=256;
                if(a<16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        }catch(Exception e){

        }
        return buf.toString();
    }
    //直接返回json对象的网络获取信息
    public static void testVolley3() throws IOException {
        RequestQueue queue = Volley.newRequestQueue(App.getContext());
        String url = "http://api.cn.ronghub.com/user/getToken.json?userId=aaaa" +
                "&name=1yuiy" +
                "&portraitUri=https://github.com/huohehuo/QZ/blob/master/app/src/main/res/drawable/mricon.png";
        JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //返回json数据格式
                        Log.e("json_cheack.....", response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO 自动生成的方法存根
                Log.e("json_cheack.....",error.toString());
            }
        });
        queue.add(stringRequest);
    }

//    //不带图片缓存的图片加载方式
//    public void testVolley4() throws IOException {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        // final ImageView imageView = new ImageView(this);
//        ImageRequest imageRequest = new ImageRequest(
//                "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1454784368,1043415621&fm=80&w=179&h=119&img.JPEG",
//                new Response.Listener<Bitmap>() {
//
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        // TODO 自动生成的方法存根
//                        imageView.setImageBitmap(response);
//                    }
//                }, 0, 0, Config.ARGB_8888, new Response.ErrorListener() {
//            /**
//             * ALPHA_8:代表8位Alpha位图
//             * ARGB_444:代表16位ARGB位图
//             * ARGB_888:代表32位ARGB位图
//             * RGB_565:代表8位RGB位图
//             */
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // TODO 自动生成的方法存根
//                imageView.setImageResource(R.drawable.ic_launcher);
//            }
//        });
//        queue.add(imageRequest);
//    }

//    //带缓存优化的图片加载方式
//    public void testVolley5() throws IOException {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        // final ImageView imageView = new ImageView(this);
//        final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(
//                1024 * 1024 * 3);
//        ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
//
//            @Override
//            public Bitmap getBitmap(String url) {
//                //获取图片
//                return cache.get(url);
//            }
//
//            @Override
//            public void putBitmap(String url, Bitmap bitmap) {
//                //把图片存入缓存中
//                cache.put(url, bitmap);
//            }
//
//        });
//        //设置ImageListener回调，把数据进行绑定：1，控件；2，默认图片；3，错误默认图片
//        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
//                R.drawable.del, android.R.drawable.ic_delete);
//        //设置网址链接，并使用listener，设置大小
//        imageLoader
//                .get("http://slbserver-10034414.cos.myqcloud.com/667069.jpg",
//                        listener, 200, 200);// 设置图片大小
//
//        // queue.add(imageLoader);
//    }

}
