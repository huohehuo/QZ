package lins.com.qz;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.model.Conversation;
import lins.com.qz.adapter.MainAdapter;
import lins.com.qz.bean.PlanBean;
import lins.com.qz.bean.User;
import lins.com.qz.databinding.ActivityMainBinding;
import lins.com.qz.thirdparty.codecamera.CaptureActivity;
import lins.com.qz.thirdparty.codecamera.EncodeQrActivity;
import lins.com.qz.ui.account.AboutMeActivity;
import lins.com.qz.ui.AddPlanActivity;
import lins.com.qz.ui.ShowPlanActivity;
import lins.com.qz.ui.SysNotifyActivity;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.ui.chat.IMActivity;
import lins.com.qz.ui.login.LoginActivity;
import lins.com.qz.utils.BadgeUtil;
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
    private Tencent mTencent = App.getTencent();
    BmobUser bmobUser;
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
                    binding.content.ryMain.setRefreshing(true);
                    binding.content.ryMain.setRefreshing(false);

                    break;
                case 1:

                    break;
            }

        }
    };
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        binding.content.toolbar.ivTopArrow.setImageResource(R.drawable.bulb);
        binding.content.toolbar.tvTopTitle.setText("首页"+App.getSharedData(Config.USER_NAME));
        binding.content.toolbar.ivTopRight.setImageResource(R.drawable.add);

        String icon = App.getSharedData(Config.USER_HEAD_ICON);
        if (!"0".equals(icon)){
            Glide.with(this)
                    .load(icon)
                    .into(binding.content.toolbar.ivTopArrow);
        }else{
            Glide.with(this)
                    .load(R.drawable.dog).
                    into(binding.content.toolbar.ivTopArrow);
        }

        setDrawerLeftEdgeSize(this, binding.drawerLayout, 0.2f);//设置抽屉滑动响应范围

        map.put(Conversation.ConversationType.PRIVATE.getName(), false);
//        ServiceUtil.startServiceUtil(MainActivity.this);
        binding.content.ryMain.setAdapter(mainAdapter = new MainAdapter(MainActivity.this));
        binding.content.ryMain.setLayoutManager(new LinearLayoutManager(this));

        bmobUser = BmobUser.getCurrentUser();

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
        binding.content.ryMain.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.content.toolbar.pbTopRight.setVisibility(View.VISIBLE);
                getData();
//                showDialog("正在更新");
//                getMainData();
            }
        });
        mainAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ShowPlanActivity.start(
                        MainActivity.this,
                        mainAdapter.getAllData().get(position).getObjectId(),
                        mainAdapter.getAllData().get(position).getEssay(),
                        mainAdapter.getAllData().get(position).getCreatedAt());
            }
        });

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

        //选择日期（弃）
        binding.content.ivChooseAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adrrPick.show();
            }
        });

        //选项卡点击回调
        adrrPick.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                App.setSharedData(Config.AT_ADDRESS, addressList.get(options1));
                getMainData();
            }
        });

        //进入聊天Activity
        binding.content.toolbar.ivTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IMActivity.class);
                //默认的启动会话列表的方式，就是在清单文件写好data，
                // 在activity中不必做其他操作，只需在xml中设置相应的fragment
//                RongIM.getInstance().startConversationList(MainActivity.this,map);
            }
        });
    }

    //侧栏菜单事件
    private void navClick() {
        startActivityWith(AddPlanActivity.class, binding.layoutNav.llAdd);
        startActivityWith(AboutMeActivity.class, binding.layoutNav.llMe);
        startActivityWith(EncodeQrActivity.class, binding.layoutNav.ivNavHead);
        //设置侧滑栏的头像
        String icon = App.getSharedData(Config.USER_HEAD_ICON);
        if (!"0".equals(icon)){
            Glide.with(this)
                    .load(icon)
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
                //<a href="myapp://jp.app/openwith?name=zhangsan&age=26">启动应用程序</a>
//                String url = "gotoapp://apphost/openwith?name=zhangsan&age=26";
//                String scheme = Uri.parse(url).getScheme();//还需要判断host
//                if (TextUtils.equals("gotoapp", scheme)) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(intent);
//                }

                RongIM.getInstance().logout();//退出融云IM
                App.clearShareData();
                startActivity(LoginActivity.class);
                finish();
            }
        });
        //分享
        binding.layoutNav.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
                mTencent.shareToQQ(MainActivity.this, params, new QShareListener(qShareIO));
            }
        });
        binding.layoutNav.ivNavEwm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
            }
        });


        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void getData() {
        getMessageNum();
        binding.content.ryMain.setRefreshing(true);
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<PlanBean> query = new BmobQuery<>();
        query.addWhereEqualTo("author",user);// 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(new FindListener<PlanBean>() {
            @Override
            public void done(List<PlanBean> list, BmobException e) {
                if (e==null){
                    mainAdapter.clear();
                    mainAdapter.addAll(list);
                }else{
                    showToast("获取信息失败");
                }
                    binding.content.ryMain.setRefreshing(false);
            }
        });
        binding.content.toolbar.pbTopRight.setVisibility(View.GONE);
        //删除帖子
//        PlanBean p = new PlanBean();
//        p.remove("author");
//        p.update("ESIt3334", new UpdateListener() {
//
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    Log.i("bmob","成功");
//                }else{
//                    Log.i("bmob","失败："+e.getMessage());
//                }
//            }
//        });
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
//        BackService.getAddressList(MainActivity.this, bmobUser.getObjectId(), "addr");
        getMainData();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    private void getMainData() {
//        mainAdapter.clear();
//        BmobQuery<AddAdrMsg> shuoBmobQuery = new BmobQuery<>();
//        shuoBmobQuery.addWhereEqualTo("addr", App.getSharedData(Config.AT_ADDRESS));
////        shuoBmobQuery.setLimit(App.getHashMainNum(Config.NUM_OF_MAIN));
//        shuoBmobQuery.findObjects(new FindListener<AddAdrMsg>() {
//            @Override
//            public void done(List<AddAdrMsg> list, BmobException e) {
//                if (e == null) {
//                    mainAdapter.addAll(list);
//                } else {
//                    showToast("获取数据失败");
//                }
//                binding.content.toolbar.pbTopRight.setVisibility(View.GONE);
////                closeDialog();
//            }
//        });
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

    private void getMessageNum(){
        // 设置未读消息监听数
        RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
            @Override
            public void onCountChanged(int i) {
                Log.e("数目：",""+i);
                BadgeUtil.setBadgeCount(App.getContext(), i);
                binding.content.toolbar.tvTopRight.setText(""+i);
            }
        });
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

    private long firstTime = 0;

    //双击退出程序
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
