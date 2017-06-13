package lins.com.qz.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lins.com.qz.App;
import lins.com.qz.R;

/**
 * Created by LINS on 2017/6/13.
 */

public class VolleyUtil {

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

    //用post方法获取网络信息
    public static void getToken() throws IOException {
        RequestQueue queue = Volley.newRequestQueue(App.getContext());
        String url = "http://api.cn.ronghub.com/user/getToken.json";
        // ?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                Log.d("check>>>>>>", "出错。。。。。。");
            }

        }) {
            // 重写该方法，构造出post请求信息
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // TODO 自动生成的方法存根
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", "1");
                map.put("name", "1");
                map.put("portraitUri", "1");
                map.put("nid", "1");
                map.put("stamp", "20140321");
                map.put("cnt", "20");
                return map;
            }
        };
        queue.add(stringRequest);

    }
    //直接返回json对象的网络获取信息
    public static void testVolley3() throws IOException {
        RequestQueue queue = Volley.newRequestQueue(App.getContext());
        String url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
        JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //返回json数据格式
                        Log.d("json_cheack.....", response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO 自动生成的方法存根

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
