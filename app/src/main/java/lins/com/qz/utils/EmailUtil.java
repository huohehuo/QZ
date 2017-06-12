package lins.com.qz.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sky on 2017/3/21.
 * 判断是否邮箱
 */

public class EmailUtil {
    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
    /**
     * 验证手机号是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    /**
     * 验证密码
     *
     * @param pwd
     * @return
     */
    public static final boolean isRightPwd(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }
        Pattern p = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$)[0-9a-zA-Z]{8,16}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }


}
