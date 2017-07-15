package lins.com.qz;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import lins.com.qz.adapter.MainAdapter;
import lins.com.qz.adapter.MainPagerAdapter;
import lins.com.qz.databinding.ActivityMainBinding;
import lins.com.qz.thirdparty.codecamera.CaptureActivity;
import lins.com.qz.thirdparty.codecamera.EncodeQrActivity;
import lins.com.qz.ui.AddPlanActivity;
import lins.com.qz.ui.SettingActivity;
import lins.com.qz.ui.SquareFragment;
import lins.com.qz.ui.SysNotifyActivity;
import lins.com.qz.ui.account.AboutMeActivity;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.ui.chat.ChatFriendFragment;
import lins.com.qz.ui.login.LoginActivity;
import lins.com.qz.utils.IntentServiceUtil.InitChatService;
import lins.com.qz.utils.share.QShareIO;
import lins.com.qz.utils.share.QShareListener;

public class MainActivity extends BaseActivity implements QShareIO {
    ActivityMainBinding binding;
    private MainAdapter mainAdapter;
    private ArrayList<String> addressList;
    private OptionsPickerView adrrPick;
    public static boolean isForeground = false;
    private Map<String, Boolean> map = new HashMap<String, Boolean>();
//    BmobUser bmobUser;
    private String atadrs = App.getSharedData(Config.AT_ADDRESS);
    private QShareIO qShareIO = new QShareIO() {
        @Override
        public void getShareData(String str) {
            showToast("分享成功");
            Log.e("share","分享成功");
        }
    };


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://更新地址列表

                    break;
                case 1:

                    break;
            }

        }
    };
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.content.toolbar.ivTopArrow.setImageResource(R.drawable.beiyong);
        binding.content.toolbar.tvTopTitle.setText("首页");
        binding.content.toolbar.ivTopRight.setImageResource(R.drawable.add);

        binding.layoutNav.tvNickname.setText(user.getNickname()==null?"":user.getNickname());
        binding.layoutNav.tvEssay.setText(user.getNote()==null?"":user.getNote());

        setDrawerLeftEdgeSize(this, binding.drawerLayout, 0.2f);//设置抽屉滑动响应范围
        mConversationList  = initConversationList();//获取融云会话列表对象

        setupViewPager(binding.content.vpMain);
        binding.content.tabMain.setupWithViewPager(binding.content.vpMain);


//        map.put(Conversation.ConversationType.PRIVATE.getName(), false);
//        ServiceUtil.startServiceUtil(MainActivity.this);

        addressList = new ArrayList<>();
        adrrPick = new OptionsPickerView(this);
        //注册广播监听器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.MAIN_RECEIVER_ACTION);
        registerReceiver(receiver, intentFilter);

//        sendBroadcast(new Intent(Config.RECEIVER_IN_SERVICE).putExtra("content","0"));

        InitChatService.initChatUser(this);

    }

    @Override
    protected void initEvent() {
        navClick();
        initAddrData();


        binding.content.toolbar.ivTopArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

//        //选择日期（弃）
//        binding.content.ivChooseAddr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                adrrPick.show();
//            }
//        });
//
//        //选项卡点击回调
//        adrrPick.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3) {
//                App.setSharedData(Config.AT_ADDRESS, addressList.get(options1));
//                getMainData();
//            }
//        });


    }

    //侧栏菜单事件
    private void navClick() {
        startActivityWith(AddPlanActivity.class, binding.layoutNav.llAdd);
        startActivityWith(AboutMeActivity.class, binding.layoutNav.llMe);
        startActivityWith(EncodeQrActivity.class, binding.layoutNav.ivNavHead);
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
        //退出登录（将会取消自动登录）
        binding.layoutNav.llExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().logout();//退出融云IM
                App.clearShareData();
                startActivity(LoginActivity.class);
                finish();
            }
        });

        binding.layoutNav.llDoublecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
            }
        });


        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void getData() {
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
//        BackService.getAddressList(MainActivity.this, bmobUser.getObjectId(), "addr");
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    void initAddrData() {
//        if (App.getHashData(Config.ADDRESS_LIST) != null) {
//            for (AddAddress adr : (List<AddAddress>) getHashData(Config.ADDRESS_LIST)
//                    ) {
//                addressList.add(adr.getAddr());
//            }
//        } else {
//            addressList.add("nothing");
//        }
//
//        adrrPick.setPicker(addressList);
//        adrrPick.setCyclic(false);
//        adrrPick.setSelectOptions(0);
    }

    //广播：用于接收相应相应的事件
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getStringExtra("content");
            Log.e("reciver", "广播获得数据：" + str);
            switch (str) {
                case "0":
                    Log.e("reciver", "广播：执行更新地址列表");
                    handler.sendEmptyMessage(0);
                    break;
                case "1":
                    Log.e("reciver", "收到jpush数据");
                    handler.sendEmptyMessage(1);
            }
        }
    };

    @Override
    protected void onDestroy() {
//        handler.removeCallbacks(updataNum);
        unregisterReceiver(receiver);
//        ServiceUtil.stopServiceUtil(MainActivity.this);
        super.onDestroy();
    }


    private Fragment mConversationList;
    private Fragment mConversationFragment = null;

    private void setupViewPager(ViewPager mViewPager) {
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SquareFragment(),"广场");
        adapter.addFragment(mConversationList,"消息");
        adapter.addFragment(ChatFriendFragment.getInstance(),"我的好友");
        mViewPager.setAdapter(adapter);
    }

    //动态加载会话列表，就不用在清单文件设置相应的data，启动会话列表也不用使用固定的instance启动
    private Fragment initConversationList() {
        if (mConversationFragment ==null){
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://"+getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(),"false")//设置私聊绘画是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(),"true")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(),"false")//设置私聊绘画是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(),"false")//设置私聊会话是否聚合显示
                    .build();
            listFragment.setUri(uri);
            return listFragment;
        }else{
            return mConversationFragment;
        }
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



    @Override
    public void getShareData(String str) {
        Log.e("back",str);
            showToast(str);
    }
}
