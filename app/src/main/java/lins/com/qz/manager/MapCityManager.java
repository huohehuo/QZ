package lins.com.qz.manager;

import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import lins.com.qz.App;
import lins.com.qz.bean.gen.LMapCityDao;
import lins.com.qz.bean.locationBean.LMapCity;

/**
 * Created by LINS on 2017/6/3.
 */

public class MapCityManager {

    /**
     * 本地数据里添加一个User
     */
    public List<LMapCity> insert(LMapCity mainBean) {
        LMapCityDao mainBeanDao = GreenDaoManager.getInstance().getSession().getLMapCityDao();
        mainBeanDao.insert(mainBean);
        return mainBeanDao.queryBuilder().build().list();
//        list.clear();
//        list.addAll(mainBeanDao.queryBuilder().build().list());
//        mainAdapter.notifyDataSetChanged();
    }

    public List<LMapCity> queryList(String LMapCityid) {
        List<LMapCity> list = new ArrayList<>();
        LMapCityDao userDao = GreenDaoManager.getInstance().getSession().getLMapCityDao();
//        LMapCity findUser = GreenDaoManager.getInstance().getSession().getLMapCityDao().queryBuilder()
//                .where(LMapCityDao.Properties.LMapCityId.eq(LMapCityid)).build().unique();
        list = userDao.queryBuilder()
                .where(LMapCityDao.Properties.Id.eq(LMapCityid))
                .build().list();
//        if(findUser != null) {
//            list.add(findUser);
//        }
        return list;
    }

    public List<LMapCity> queryAll() {
        List<LMapCity> list = new ArrayList<>();
        LMapCityDao userDao = GreenDaoManager.getInstance().getSession().getLMapCityDao();
//        LMapCity findUser = GreenDaoManager.getInstance().getSession().getLMapCityDao().queryBuilder()
//                .where(LMapCityDao.Properties.LMapCityId.eq(LMapCityid)).build().unique();
        list = userDao.queryBuilder()
                .build().list();
//        if(findUser != null) {
//            list.add(findUser);
//        }
        return list;
    }

    public LMapCity query(String LMapCityid) {
        List<LMapCity> list = new ArrayList<>();
        LMapCityDao userDao = GreenDaoManager.getInstance().getSession().getLMapCityDao();
        LMapCity findUser = GreenDaoManager.getInstance().getSession().getLMapCityDao().queryBuilder()
                .where(LMapCityDao.Properties.Id.eq(LMapCityid)).build().unique();
//        list = userDao.queryBuilder()
//                .where(LMapCityDao.Properties.LMapCityid.eq(LMapCityid))
//                .build().list();
        if(findUser != null) {
            return findUser;
        }
        return null;
    }

    /**
     * 根据名字更新某条数据的名字
     */
    public void updateUser(LMapCity mainBean){
        LMapCity findUser = GreenDaoManager.getInstance().getSession().getLMapCityDao().queryBuilder()
                .where(LMapCityDao.Properties.Id.eq(mainBean.getId())).build().unique();
        if(findUser != null) {
//            findUser.setName(newName);
            GreenDaoManager.getInstance().getSession().getLMapCityDao().update(findUser);
            Toast.makeText(App.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(App.getContext(), "用户不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据id删除某用户(根据id查找到用户，并通过id删除)
     */
    public List<LMapCity> delete(LMapCity mainBean){
        LMapCityDao userDao = GreenDaoManager.getInstance().getSession().getLMapCityDao();
        LMapCity findUser = userDao.queryBuilder().where(LMapCityDao.Properties.Id.eq(mainBean.getId())).build().unique();
        if(findUser != null){
            userDao.deleteByKey(findUser.getId());
            return userDao.queryBuilder().build().list();
        }
        return null;
    }

    public void deleteAll(){
        List<LMapCity> list = new ArrayList<>();
        LMapCityDao userDao = GreenDaoManager.getInstance().getSession().getLMapCityDao();
//        LMapCity findUser = userDao.queryBuilder().where(LMapCityDao.Properties.LMapCityId.eq(mainBean.getLMapCityId())).build().unique();
                list = userDao.queryBuilder()
                .build().list();
        for (LMapCity LMapCity:list) {
            userDao.deleteByKey(LMapCity.getId());
        }

//        if(findUser != null){
//            userDao.deleteByKey(findUser.getId());
//            return userDao.queryBuilder().build().list();
//        }
//        return null;
    }














//
//    List<User> userList = userDao.queryBuilder()
//            .where(UserDao.Properties.Id.notEq(999))
//            .orderAsc(UserDao.Properties.Id)
//            .limit(5)
//            .build().list();
}
