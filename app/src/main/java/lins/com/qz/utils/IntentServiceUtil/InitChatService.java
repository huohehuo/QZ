package lins.com.qz.utils.IntentServiceUtil;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.bean.locationBean.LChatFrds;
import lins.com.qz.bean.locationBean.LFriend;
import lins.com.qz.bean.locationBean.LUser;
import lins.com.qz.manager.FrdsManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class InitChatService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "lins.com.qz.utils.IntentServiceUtil.action.FOO";
    private static final String ACTION_BAZ = "lins.com.qz.utils.IntentServiceUtil.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "lins.com.qz.utils.IntentServiceUtil.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "lins.com.qz.utils.IntentServiceUtil.extra.PARAM2";

    private static List<LChatFrds> partUsers = new ArrayList<>();

    public InitChatService() {
        super("InitChatService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void initChatUser(Context context) {
        Intent intent = new Intent(context, InitChatService.class);
        intent.setAction(ACTION_FOO);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, InitChatService.class);
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
                handleActionFoo();
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * //这里作用
     // 需要获取数据库的好友信息（如id，头像url之类的）遍历出来
     // 这样在聊天页面才会有相应的头像显示
     */
    private void handleActionFoo() {
        FrdsManager manager = new FrdsManager();
        partUsers.addAll(manager.queryAll());
        //把自身加进去
        partUsers.add(new LChatFrds(null,
                App.getSharedData(Config.USER_NAME),
                App.getSharedData(Config.USER_RONG_UUID),
                "http://bmob-cdn-12281.b0.upaiyun.com/2017/07/03/56e257a2c700445c8729bbfc877dec4a.png"));
//        partUsers.add(new LUser("1e60f61ba78d4b8fa1b5c2886b662c0b","ww","http://bmob-cdn-12281.b0.upaiyun.com/2017/06/20/0c6667253a72463cacc8ddf923e399cb.png"));
//        partUsers.add(new LUser("baa7b5acafb744b383fc9c5cb6aaee18","qq","http://bmob-cdn-12281.b0.upaiyun.com/2017/06/20/0c6667253a72463cacc8ddf923e399cb.png"));
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                for (LChatFrds i : partUsers) {
                        Log.e("initchat：","获得一个chat:"+i.toString());
                    if (i.getRongid().equals(s)) {
                        return new UserInfo(i.getRongid(), i.getName(), Uri.parse(i.getHead_icon()));
                    }
                }
                Log.e("initchat","UserId is "+s);
//                return new UserInfo("1e60f61ba78d4b8fa1b5c2886b662c0b", "ww", Uri.parse("http://bmob-cdn-12281.b0.upaiyun.com/2017/06/20/0c6667253a72463cacc8ddf923e399cb.png"));
                return null;
            }
        },true);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
