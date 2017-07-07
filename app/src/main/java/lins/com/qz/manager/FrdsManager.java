package lins.com.qz.manager;

import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import lins.com.qz.App;
import lins.com.qz.bean.gen.LChatFrdsDao;
import lins.com.qz.bean.locationBean.LChatFrds;

/**
 * Created by LINS on 2017/6/3.
 */

public class FrdsManager {

    /**
     * 本地数据里添加一个User
     */
    public List<LChatFrds> insert(LChatFrds mainBean) {
        LChatFrdsDao mainBeanDao = GreenDaoManager.getInstance().getSession().getLChatFrdsDao();
        //先查找是否存在
        LChatFrds findUser = mainBeanDao.queryBuilder()
                .where(LChatFrdsDao.Properties.Name.eq(mainBean.getName())).build().unique();
        //若存在，删除再添加
        if(findUser != null) {
            delete(mainBean);
            mainBeanDao.insert(mainBean);
        }else{
            mainBeanDao.insert(mainBean);
        }
        return mainBeanDao.queryBuilder().build().list();
//        list.clear();
//        list.addAll(mainBeanDao.queryBuilder().build().list());
//        mainAdapter.notifyDataSetChanged();
    }

    public void insertList(List<LChatFrds> mainBean) {
        LChatFrdsDao mainBeanDao = GreenDaoManager.getInstance().getSession().getLChatFrdsDao();
        for (int i=0;i<mainBean.size();i++){
            //先查找是否存在
            LChatFrds findUser = mainBeanDao.queryBuilder()
                    .where(LChatFrdsDao.Properties.Name.eq(mainBean.get(i).getName())).build().unique();
            //若存在，删除再添加
            if(findUser != null) {
                delete(mainBean.get(i));
                mainBeanDao.insert(mainBean.get(i));
            }else{
                mainBeanDao.insert(mainBean.get(i));
            }
        }

//        list.clear();
//        list.addAll(mainBeanDao.queryBuilder().build().list());
//        mainAdapter.notifyDataSetChanged();
    }

    public List<LChatFrds> queryList(String LChatFrdsid) {
        List<LChatFrds> list = new ArrayList<>();
        LChatFrdsDao userDao = GreenDaoManager.getInstance().getSession().getLChatFrdsDao();
//        LChatFrds findUser = GreenDaoManager.getInstance().getSession().getLChatFrdsDao().queryBuilder()
//                .where(LChatFrdsDao.Properties.LChatFrdsId.eq(LChatFrdsid)).build().unique();
        list = userDao.queryBuilder()
//                .where(LChatFrdsDao.Properties.LChatFrdsId.eq(LChatFrdsid))
                .build().list();
//        if(findUser != null) {
//            list.add(findUser);
//        }
        return list;
    }

    public List<LChatFrds> queryAll() {
        List<LChatFrds> list = new ArrayList<>();
        LChatFrdsDao userDao = GreenDaoManager.getInstance().getSession().getLChatFrdsDao();
        list = userDao.queryBuilder()
                .build().list();
//        if(findUser != null) {
//            list.add(findUser);
//        }
        return list;
    }

    public LChatFrds query(String name) {
        List<LChatFrds> list = new ArrayList<>();
        LChatFrdsDao userDao = GreenDaoManager.getInstance().getSession().getLChatFrdsDao();
        LChatFrds findUser = GreenDaoManager.getInstance().getSession().getLChatFrdsDao().queryBuilder()
                .where(LChatFrdsDao.Properties.Name.eq(name)).build().unique();
        if(findUser != null) {
            return findUser;
        }
        return null;
    }

    /**
     * 根据名字更新某条数据的名字
     */
    public void updateUser(LChatFrds mainBean){
        LChatFrds findUser = GreenDaoManager.getInstance().getSession().getLChatFrdsDao().queryBuilder()
                .where(LChatFrdsDao.Properties.Name.eq(mainBean.getName())).build().unique();
        if(findUser != null) {
//            findUser.setName(newName);
            GreenDaoManager.getInstance().getSession().getLChatFrdsDao().update(findUser);
            Toast.makeText(App.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(App.getContext(), "用户不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据id删除某用户(根据id查找到用户，并通过id删除)
     */
    public List<LChatFrds> delete(LChatFrds mainBean){
        LChatFrdsDao userDao = GreenDaoManager.getInstance().getSession().getLChatFrdsDao();
        LChatFrds findUser = userDao.queryBuilder().where(LChatFrdsDao.Properties.Name
                .eq(mainBean.getName())).build().unique();
        if(findUser != null){
            userDao.deleteByKey(findUser.getId());
            return userDao.queryBuilder().build().list();
        }
        return null;
    }

    public void deleteAll(){
        List<LChatFrds> list = new ArrayList<>();
        LChatFrdsDao userDao = GreenDaoManager.getInstance().getSession().getLChatFrdsDao();
//        LChatFrds findUser = userDao.queryBuilder().where(LChatFrdsDao.Properties.LChatFrdsId.eq(mainBean.getLChatFrdsId())).build().unique();
                list = userDao.queryBuilder()
                .build().list();
        for (LChatFrds LChatFrds:list) {
            userDao.deleteByKey(LChatFrds.getId());
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
