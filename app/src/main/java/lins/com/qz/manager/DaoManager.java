package lins.com.qz.manager;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lins.com.qz.App;
import lins.com.qz.bean.User;
import lins.com.qz.bean.gen.LChatFrdsDao;
import lins.com.qz.bean.gen.LUserDao;
import lins.com.qz.bean.locationBean.LChatFrds;
import lins.com.qz.bean.locationBean.LUser;

/**
 * Created by LINS on 2017/6/18.
 */

public class DaoManager {



        /**
     * 本地数据里添加一个User
     */
    public List<LUser> insert(LUser LUser) {
        delete(LUser);//若已存在，删除
        LUserDao LUserDao = GreenDaoManager.getInstance().getSession().getLUserDao();
        LUserDao.insert(LUser);
        return LUserDao.queryBuilder().build().list();
//        list.clear();
//        list.addAll(LUserDao.queryBuilder().build().list());
//        mainAdapter.notifyDataSetChanged();
    }

    public LUser query(String name) {
        LUserDao userDao = GreenDaoManager.getInstance().getSession().getLUserDao();
        LUser findUser = userDao.queryBuilder()
                .where(LUserDao.Properties.Username.eq(name)).build().unique();
        if(findUser != null) {
            return findUser;
        }else{
            return new LUser("","","");
        }
    }

    public LUser lUserQuery() {
        LUserDao LUserDao = GreenDaoManager.getInstance().getSession().getLUserDao();
//        for (LUser user:LUserDao.queryBuilder().build().list()) {
//            Log.e("getUser",user.toString());
//        }
        return LUserDao.queryBuilder().build().list().get(0);
//        list.clear();
//        list.addAll(LUserDao.queryBuilder().build().list());
//        mainAdapter.notifyDataSetChanged();
    }

    /**
     * 根据id删除某用户(根据id查找到用户，并通过id删除)
     */
    public List<LUser> delete(LUser LUser){
        LUserDao userDao = GreenDaoManager.getInstance().getSession().getLUserDao();
        userDao.deleteAll();
//        LUser findUser = userDao.queryBuilder().where(LUserDao.Properties.Objectid.eq(LUser.getObjectid())).build().unique();
//        if(findUser != null){
//            userDao.deleteByKey(findUser.getId());
//            return userDao.queryBuilder().build().list();
//        }
        return null;
    }
    public List<LUser> queryAll() {
        List<LUser> list = new ArrayList<>();
        LUserDao userDao = GreenDaoManager.getInstance().getSession().getLUserDao();
        list = userDao.queryBuilder()
                .build().list();
//        if(findUser != null) {
//            list.add(findUser);
//        }
        return list;
    }
    /**
     * 根据名字更新某条数据的名字
     */
    public void updateUserIconNameNote(String name,String iconurl,String newname,String note){
        LUser findUser = GreenDaoManager.getInstance().getSession().getLUserDao().queryBuilder()
                .where(LUserDao.Properties.Username.eq(name)).build().unique();
        if(findUser != null) {
            findUser.setUsername(newname);
            if (!"".equals(iconurl)){
                findUser.setIconurl(iconurl);
            }
            findUser.setNote(note);
            GreenDaoManager.getInstance().getSession().getLUserDao().update(findUser);
            Toast.makeText(App.getContext(), "本地User，更新iconurl成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(App.getContext(), "用户不存在", Toast.LENGTH_SHORT).show();
        }
    }










//    /**
//     * 本地数据里添加一个User
//     */
//    public List<LUser> insert(LUser LUser) {
//        LUserDao LUserDao = GreenDaoManager.getInstance().getSession().getLUserDao();
//        LUserDao.insert(LUser);
//        return LUserDao.queryBuilder().build().list();
////        list.clear();
////        list.addAll(LUserDao.queryBuilder().build().list());
////        mainAdapter.notifyDataSetChanged();
//    }

//
//    /**
//     * 根据id删除某用户(根据id查找到用户，并通过id删除)
//     */
//    public List<LUser> delete(LUser LUser){
//        LUserDao userDao = GreenDaoManager.getInstance().getSession().getLUserDao();
//        LUser findUser = userDao.queryBuilder().where(LUserDao.Properties.Id.eq(LUser.getId())).build().unique();
//        if(findUser != null){
//            userDao.deleteByKey(findUser.getId());
//            return userDao.queryBuilder().build().list();
//        }
//        return null;
//    }

}
