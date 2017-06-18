package lins.com.qz.manager;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import lins.com.qz.App;
import lins.com.qz.bean.User;
import lins.com.qz.bean.gen.LUserDao;
import lins.com.qz.bean.locationBean.LUser;

/**
 * Created by LINS on 2017/6/18.
 */

public class DaoManager {



        /**
     * 本地数据里添加一个User
     */
    public List<LUser> insert(LUser mainBean) {
        delete(mainBean);//若已存在，删除
        LUserDao mainBeanDao = GreenDaoManager.getInstance().getSession().getLUserDao();
        mainBeanDao.insert(mainBean);
        return mainBeanDao.queryBuilder().build().list();
//        list.clear();
//        list.addAll(mainBeanDao.queryBuilder().build().list());
//        mainAdapter.notifyDataSetChanged();
    }

    public LUser lUserQuery() {
        LUserDao mainBeanDao = GreenDaoManager.getInstance().getSession().getLUserDao();
//        for (LUser user:mainBeanDao.queryBuilder().build().list()) {
//            Log.e("getUser",user.toString());
//        }
        return mainBeanDao.queryBuilder().build().list().get(0);
//        list.clear();
//        list.addAll(mainBeanDao.queryBuilder().build().list());
//        mainAdapter.notifyDataSetChanged();
    }

    /**
     * 根据id删除某用户(根据id查找到用户，并通过id删除)
     */
    public List<LUser> delete(LUser mainBean){
        LUserDao userDao = GreenDaoManager.getInstance().getSession().getLUserDao();
        userDao.deleteAll();
//        LUser findUser = userDao.queryBuilder().where(LUserDao.Properties.Objectid.eq(mainBean.getObjectid())).build().unique();
//        if(findUser != null){
//            userDao.deleteByKey(findUser.getId());
//            return userDao.queryBuilder().build().list();
//        }
        return null;
    }












//    /**
//     * 本地数据里添加一个User
//     */
//    public List<MainBean> insert(MainBean mainBean) {
//        MainBeanDao mainBeanDao = GreenDaoManager.getInstance().getSession().getMainBeanDao();
//        mainBeanDao.insert(mainBean);
//        return mainBeanDao.queryBuilder().build().list();
////        list.clear();
////        list.addAll(mainBeanDao.queryBuilder().build().list());
////        mainAdapter.notifyDataSetChanged();
//    }
//    /**
//     * 根据名字更新某条数据的名字
//     */
//    public void updateUser(MainBean mainBean){
//        MainBean findUser = GreenDaoManager.getInstance().getSession().getMainBeanDao().queryBuilder()
//                .where(MainBeanDao.Properties.Name.eq(mainBean.getName())).build().unique();
//        if(findUser != null) {
//            GreenDaoManager.getInstance().getSession().getMainBeanDao().update(findUser);
//            Toast.makeText(App.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(App.getContext(), "用户不存在", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /**
//     * 根据id删除某用户(根据id查找到用户，并通过id删除)
//     */
//    public List<MainBean> delete(MainBean mainBean){
//        MainBeanDao userDao = GreenDaoManager.getInstance().getSession().getMainBeanDao();
//        MainBean findUser = userDao.queryBuilder().where(MainBeanDao.Properties.Id.eq(mainBean.getId())).build().unique();
//        if(findUser != null){
//            userDao.deleteByKey(findUser.getId());
//            return userDao.queryBuilder().build().list();
//        }
//        return null;
//    }

}
