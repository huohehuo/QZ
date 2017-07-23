package lins.com.qz;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.view.KeyEvent;
import android.view.View;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;

import lins.com.qz.databinding.ActivityHomeBinding;
import lins.com.qz.ui.AddPlanActivity;
import lins.com.qz.ui.map.MapActivity;
import lins.com.qz.ui.SysNotifyActivity;
import lins.com.qz.ui.account.AboutMeActivity;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.ui.chat.IMActivity;
import lins.com.qz.ui.setting.SettingActivity;
import lins.com.qz.utils.IntentServiceUtil.InitChatService;

public class HomeActivity extends BaseActivity {

    ActivityHomeBinding binding;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);

        binding.layoutNav.tvNickname.setText(user.getNickname()==null?"":user.getNickname());
        binding.layoutNav.tvEssay.setText(user.getNote()==null?"":user.getNote());

        setDrawerLeftEdgeSize(this, binding.drawerLayout, 0.2f);//设置抽屉滑动响应范围

        InitChatService.initChatUser(this);

    }

    @Override
    protected void initEvent() {
        navClick();
        binding.content.ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        startActivityWith(IMActivity.class,binding.content.ivRight);

    }

    @Override
    protected void getData() {

    }



    //侧栏菜单事件
    private void navClick() {
        startActivityWith(AddPlanActivity.class, binding.layoutNav.llAdd);
        startActivityWith(AboutMeActivity.class, binding.layoutNav.llMe);
        startActivityWith(SettingActivity.class,binding.layoutNav.llSet);
        //设置侧滑栏的头像
//        String icon = App.getSharedData(Config.USER_HEAD_ICON);
        if (!"".equals(user.getIconpic())){
            Glide.with(this)
                    .load(user.getIconpic())
                    .into(binding.layoutNav.ivNavHead);
        }else{
            Glide.with(this)
                    .load(R.drawable.dog).
                    into(binding.layoutNav.ivNavHead);
        }
        //关于
        binding.layoutNav.llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SysNotifyActivity.class);


//                BmobQuery<User> innerQuery = new BmobQuery<User>();
//                String[] friendIds={"asdf","ww"};//好友的objectId数组
//                innerQuery.addWhereContainedIn("objectId", Arrays.asList(friendIds));
////查询帖子
//                BmobQuery<AddAddress> query = new BmobQuery<AddAddress>();
//                query.addWhereMatchesQuery("author", "_User", innerQuery);
//                query.findObjects(new FindListener<AddAddress>() {
//                    @Override
//                    public void done(List<AddAddress> object,BmobException e) {
//                        if(e==null){
//                            for (AddAddress user:object){
//                                Log.e(">>>",user.toString());
//                            }
//
//                            Log.i("bmob","成功");
//                        }else{
//                            Log.i("bmob","失败："+e.getMessage());
//                        }
//                    }
//                });



//                BmobQuery<User> shuoBmobQuery = new BmobQuery<>();
//                shuoBmobQuery.addWhereEqualTo("username","asdf");
////        shuoBmobQuery.setLimit(App.getHashMainNum(Config.NUM_OF_MAIN));
//                shuoBmobQuery.findObjects(new FindListener<User>() {
//                    @Override
//                    public void done(List<User> list, BmobException e) {
//                        if (e == null) {
//                            Log.e("getuser",list.get(0).toString());
//
//                        } else {
//                            showToast("获取数据失败");
//                        }
//                        binding.content.toolbar.pbTopRight.setVisibility(View.GONE);
////                closeDialog();
//                    }
//                });
            }
        });

        startActivityWith(MapActivity.class,binding.layoutNav.llExit);

        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * 设置全屏滑动
     */
    private void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    //双击退出程序
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
                showBottomToast(binding.getRoot(), "再按一次退出程序");
                firstTime = System.currentTimeMillis();
            } else {
//                BmobUser.logOut();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
