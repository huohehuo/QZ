package lins.com.qz.utils.IntentServiceUtil;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.bean.AddAddress;
import lins.com.qz.bean.AddAdrMsg;


public class BackService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "lins.com.qz.utils.IntentServiceUtil.action.FOO";
    private static final String ACTION_BAZ = "lins.com.qz.utils.IntentServiceUtil.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "lins.com.qz.utils.IntentServiceUtil.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "lins.com.qz.utils.IntentServiceUtil.extra.PARAM2";

    public BackService() {
        super("BackService");
    }

    public static void getMainNum(Context context) {
        Intent intent = new Intent(context, BackService.class);
        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void getAddressList(Context context, String param1, String param2) {
        Intent intent = new Intent(context, BackService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo();
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo() {
        BmobQuery<AddAdrMsg> query = new BmobQuery<>();
        query.count(AddAdrMsg.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    if (App.getHashData(Config.NUM_OF_MAIN) != integer) {
                        App.setHashData(Config.NUM_OF_MAIN, integer);
                        Log.e("backservice", "获取主页数据条目：" + App.getHashData(Config.NUM_OF_MAIN));
                    } else {
                        Log.e("backservice", "无新数据");
                    }
                } else {
                    handleActionFoo();
                }
            }
        });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        BmobQuery<AddAddress> shuoBmobQuery = new BmobQuery<>();
//        shuoBmobQuery.addWhereEqualTo("addr","all");
//        shuoBmobQuery.setLimit(App.getHashMainNum(Config.NUM_OF_MAIN));
        shuoBmobQuery.findObjects(new FindListener<AddAddress>() {
            @Override
            public void done(List<AddAddress> list, BmobException e) {
                if (e == null) {
                    Log.e("service","获取地址列表_success");
                    App.setHashData(Config.ADDRESS_LIST,list);
                    App.setSharedData(Config.AT_ADDRESS,list.get(0).getAddr());
                    //发送广播，使主页面的地址列表数据更新
                    Intent intent = new Intent(Config.MAIN_RECEIVER_ACTION);
                    intent.putExtra("content","0");
                    sendBroadcast(intent);
                } else {
                    Log.e("service","获取地址列表_error");
                }

            }
        });
    }
}
