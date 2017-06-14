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

    public static final String MAIN_RECEIVER_ACTION= "main_receiver_action";

    public static final String MAIN_SERVICE_ACTION = "main_service_action";

    public static final String RECEIVER_IN_SERVICE="receiver_in_service";
    //头像
    public static final String PATH_SELECT_AVATAR      = Environment.getExternalStorageDirectory().getAbsolutePath()+"/qz/avatar";

    public static final String PATH_SELECT_COVER     =Environment.getExternalStorageDirectory().getAbsolutePath()+"/live36G/cover";


    public static final String HAVE_RONG_TOKEN="have_the_token_of_rong";
}
