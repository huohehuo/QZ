package lins.com.qz.utils;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.bean.User;
import lins.com.qz.bean.locationBean.LUser;

import static lins.com.qz.Config.USER_NAME;
import static lins.com.qz.R.drawable.user;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SaveService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION1 = "lins.com.qz.utils.action.FOO";
    private static final String ACTION2 = "lins.com.qz.utils.action.BAZ";
    private static final String ACTION3 = "lins.com.qz.utils.action.C";
    private static final String ACTION4 = "lins.com.qz.utils.action.D";
    private static final String ACTION5 = "lins.com.qz.utils.action.E";
    private static final String ACTION6 = "lins.com.qz.utils.action.F";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "lins.com.qz.utils.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "lins.com.qz.utils.extra.PARAM2";
    private static final String EXTRA_PARAM3 = "lins.com.qz.utils.extra.PARAM3";
    private static final String EXTRA_PARAM4 = "lins.com.qz.utils.extra.PARAM4";
    private static final String EXTRA_PARAM5 = "lins.com.qz.utils.extra.PARAM5";
    private static final String EXTRA_PARAM6 = "lins.com.qz.utils.extra.PARAM6";

    public SaveService() {
        super("SaveService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startSaveLocationUser(Context context, String age, String sex, String note
            , String iconurl, String rongid) {
        Intent intent = new Intent(context, SaveService.class);
        intent.setAction(ACTION1);
        intent.putExtra(EXTRA_PARAM1, age);
        intent.putExtra(EXTRA_PARAM2, sex);
        intent.putExtra(EXTRA_PARAM3, note);
        intent.putExtra(EXTRA_PARAM4, iconurl);
        intent.putExtra(EXTRA_PARAM5, rongid);
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
        Intent intent = new Intent(context, SaveService.class);
        intent.setAction(ACTION2);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION1.equals(action)) {
                final String age = intent.getStringExtra(EXTRA_PARAM1);
                final String sex = intent.getStringExtra(EXTRA_PARAM2);
                final String note = intent.getStringExtra(EXTRA_PARAM3);
                final String iconurl = intent.getStringExtra(EXTRA_PARAM4);
                final String rongid = intent.getStringExtra(EXTRA_PARAM5);
                handleActionFoo(age, sex, note, iconurl, rongid);
            } else if (ACTION2.equals(action)) {
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
    private void handleActionFoo(String age, String sex, String note, String iconurl, final String rongid) {
        final BmobUser bmobUser = BmobUser.getCurrentUser();
        final LUser lUser = new LUser();
        lUser.setAge(age);
        lUser.setSex(sex);
        lUser.setNote(note);
        lUser.setIconurl(iconurl);
        //如果获取的用户数据存在融云id，则存入数据库，否则新建一个uuid
        //此处以防注册时，更新融云IM账户id失败的情况，在此生成
        if (rongid != null && !"".equals(rongid)) {
            lUser.setRongid(rongid);
            App.setSharedData(Config.USER_RONG_UUID,rongid);
            lUser.setObjectid(bmobUser.getObjectId());
            lUser.setUsername(bmobUser.getUsername());
            lUser.setEmail(bmobUser.getEmail());
            lUser.setPhone(bmobUser.getMobilePhoneNumber());
            App.getDaoManager().insert(lUser);
            Log.e("后台保存的数据LUser", App.getDaoManager().query(bmobUser.getUsername()).toString());
        } else {
            Log.e("login","登录账户无融云IM，生成ing");
            final String rong=App.getUUID();
            lUser.setRongid(rong);
            App.setSharedData(Config.USER_RONG_UUID,rong);
            User user2 = new User();
            user2.setRongid(rong);
            user2.update(bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e==null){
                        Log.e("rong","生成融云IM账户id----更新成功");
                        lUser.setObjectid(bmobUser.getObjectId());
                        lUser.setUsername(bmobUser.getUsername());
                        lUser.setEmail(bmobUser.getEmail());
                        lUser.setPhone(bmobUser.getMobilePhoneNumber());
                        App.getDaoManager().insert(lUser);
                        Log.e("后台保存的数据LUser", App.getDaoManager().query(bmobUser.getUsername()).toString());
                        VolleyUtil.getInstance(App.getContext())
                                .getToken(rong, App.getSharedData(USER_NAME));
                    }else{
                        Log.e("rong","生成融云IM账户id----更新失败");
                        lUser.setObjectid(bmobUser.getObjectId());
                        lUser.setUsername(bmobUser.getUsername());
                        lUser.setEmail(bmobUser.getEmail());
                        lUser.setPhone(bmobUser.getMobilePhoneNumber());
                        App.getDaoManager().insert(lUser);
                        Log.e("后台保存的数据LUser", App.getDaoManager().query(bmobUser.getUsername()).toString());
                    }
                }
            });
            //注意，此处若是rongId上传至用户数据更新时失败，本地可能会不存在rongid
        }

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
