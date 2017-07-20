//package lins.com.qz.thirdparty.downfile;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.databinding.DataBindingUtil;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.gson.JsonObject;
//import com.quseit.xsocks.Config;
//import com.quseit.xsocks.R;
//import com.quseit.xsocks.XApp;
//import com.quseit.xsocks.activity.BaseActivity;
//import com.quseit.xsocks.bean.UserBean;
//import com.quseit.xsocks.databinding.ActivityAccountBinding;
//import com.quseit.xsocks.modules.vpnservice.BuyActivity;
//import com.quseit.xsocks.modules.vpnservice.MainActivity;
//import com.quseit.xsocks.net.XSubscribe;
//import com.quseit.xsocks.util.downfile.Base64;
//import com.quseit.xsocks.util.downfile.asihttp.AsyncHttpClient;
//import com.quseit.xsocks.util.downfile.asihttp.AsyncHttpResponseHandler;
//import com.quseit.xsocks.util.downfile.asihttp.JsonHttpResponseHandler;
//import com.quseit.xsocks.util.downfile.asihttp.RequestParams;
//
//import org.apache.http.HttpHost;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//import static com.google.android.gms.common.ConnectionResult.SERVICE_DISABLED;
//import static com.google.android.gms.common.ConnectionResult.SERVICE_MISSING;
//import static com.google.android.gms.common.ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED;
//
///**
// *          账户信息
//
//
//
// android {
//    compileSdkVersion 25
//    buildToolsVersion "25.0.2"
//    useLibrary 'org.apache.http.legacy'
//
//
// */
//public class AccountActivity extends BaseActivity {
//
//    @Override
//    protected void loadData() {
//
//        //------
//        get2(this, Config.UPDATER_URL, null, new JsonHttpResponseHandler() {
//            @Override
//            public void onFailure(Throwable error) {
//                Log.e("check---no",error.toString());
//            }
//
//            @Override
//            public void onSuccess(final JSONObject response) {
//                Log.e("check",response.toString());
//
//            }
//        });
//
//        //---------
//
//    }
//
//    public static void get2(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        HttpHost hcProxyHost = null;
//        AsyncHttpClient client = new AsyncHttpClient(hcProxyHost);
//        client.get(url, params, responseHandler);
//    }
//}
