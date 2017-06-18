package lins.com.qz.utils;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import cn.bmob.v3.BmobUser;
import lins.com.qz.App;
import lins.com.qz.bean.User;
import lins.com.qz.bean.locationBean.LUser;

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
    private static final String ACTION3 ="lins.com.qz.utils.action.C";
    private static final String ACTION4 ="lins.com.qz.utils.action.D";
    private static final String ACTION5 ="lins.com.qz.utils.action.E";
    private static final String ACTION6 ="lins.com.qz.utils.action.F";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "lins.com.qz.utils.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "lins.com.qz.utils.extra.PARAM2";
    private static final String EXTRA_PARAM3 ="lins.com.qz.utils.extra.PARAM3";
    private static final String EXTRA_PARAM4 ="lins.com.qz.utils.extra.PARAM4";
    private static final String EXTRA_PARAM5 ="lins.com.qz.utils.extra.PARAM5";
    private static final String EXTRA_PARAM6 ="lins.com.qz.utils.extra.PARAM6";
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
    public static void startSaveLocationUser(Context context, String age,String sex,String note,String iconurl) {
        Intent intent = new Intent(context, SaveService.class);
        intent.setAction(ACTION1);
        intent.putExtra(EXTRA_PARAM1, age);
        intent.putExtra(EXTRA_PARAM2, sex);
        intent.putExtra(EXTRA_PARAM3, note);
        intent.putExtra(EXTRA_PARAM4, iconurl);
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
                handleActionFoo(age,sex,note,iconurl);
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
    private void handleActionFoo(String age,String sex,String note,String iconurl) {
        BmobUser bmobUser = BmobUser.getCurrentUser();
        LUser lUser = new LUser();
        lUser.setAge(age);
        lUser.setSex(sex);
        lUser.setNote(note);
        lUser.setIconurl(iconurl);
        lUser.setObjectid(bmobUser.getObjectId());
        lUser.setUsername(bmobUser.getUsername());
        lUser.setEmail(bmobUser.getEmail());
        lUser.setPhone(bmobUser.getMobilePhoneNumber());
        App.getDaoManager().insert(lUser);
        Log.e("后台保存的数据LUser",App.getDaoManager().lUserQuery().toString());
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