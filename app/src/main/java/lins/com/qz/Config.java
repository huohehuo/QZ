package lins.com.qz;

import android.os.Environment;

/**
 * Created by LINS on 2017/5/3.
 */

public class Config {

    public static final String USER_NAME="user_name";
    public static final String USER_PWD="user_pwd";
    public static final String IS_AUTO_LOGIN="is_auto_login";
    public static String NUM_OF_MAIN="num_of_main";
    public static String ACTIVITY_SEND_DATA="activity_to_activity_send_data";
    public static String ADDRESS_LIST = "address_list";
    public static String AT_ADDRESS="at_address";

    public static final String USER_RONG_UUID="user_rong_uuid";



    public static final String MAIN_RECEIVER_ACTION= "main_receiver_action";

    public static final String MAIN_SERVICE_ACTION = "main_service_action";

    public static final String RECEIVER_IN_SERVICE="receiver_in_service";
    //头像
    public static final String PATH_SELECT_AVATAR      = Environment.getExternalStorageDirectory().getAbsolutePath()+"/qz/avatar.png";

    public static final String PATH_SELECT_COVER     =Environment.getExternalStorageDirectory().getAbsolutePath()+"/live36G/cover";


    public static final String HAVE_RONG_TOKEN="have_the_token_of_rong";



    //-------------------------------------------- QQ ----------------------------------
    public static String QQ_ID="1106232794";


    //-------------------------------------------- sINA ----------------------------------

    /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
    public static final String SINA_APP_KEY      = "3266491664";
    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     */
    public static final String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * WeiboSDKDemo 应用对应的权限，第三方开发者一般不需要这么多，可直接设置成空即可。
     * 详情请查看 Demo 中对应的注释。
     */
    public static final String SINA_SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";


    //-------------------------------------------- WeChat ----------------------------------
    public static final String WX_APP_ID="wx639f8c1b491a8b64";



}
