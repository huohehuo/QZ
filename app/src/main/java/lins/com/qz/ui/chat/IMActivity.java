package lins.com.qz.ui.chat;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.model.Conversation;
import lins.com.qz.App;
import lins.com.qz.R;
import lins.com.qz.adapter.AboutMeAdapter;
import lins.com.qz.bean.locationBean.LChatFrds;
import lins.com.qz.databinding.ConversationlistBinding;
import lins.com.qz.manager.FrdsManager;
import lins.com.qz.ui.fragment.ChatFriendFragment;
import lins.com.qz.utils.BadgeUtil;


/**             会话列表
 * Created by LINS on 2017/2/27.
 */

public class IMActivity extends FragmentActivity {
    ConversationlistBinding binding;
    private AboutMeAdapter adapter;
    private Fragment mConversationList;
    private Fragment mConversationFragment = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.conversationlist);
        binding.toolbar.ivTopArrow.setImageResource(R.drawable.back);
        binding.toolbar.tvTopTitle.setText("IM");
        mConversationList  = initConversationList();//获取融云会话列表对象

        getMessageNum();
        setupViewPager(binding.viewpager);
        binding.tabChat.setupWithViewPager(binding.viewpager);

        binding.toolbar.ivTopArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupViewPager(ViewPager mViewPager) {
        AboutMeAdapter adapter = new AboutMeAdapter(getSupportFragmentManager());
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

    private void getMessageNum(){
        // 设置未读消息监听数
        RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
            @Override
            public void onCountChanged(int i) {
                Log.e("数目：",""+i);
                BadgeUtil.setBadgeCount(App.getContext(), i);
            }
        });
    }
    private void getFriendList(){
        new FrdsManager().insert(new LChatFrds(null,"ww","1e60f61ba78d4b8fa1b5c2886b662c0b",
                "http://bmob-cdn-12281.b0.upaiyun.com/2017/06/20/0c6667253a72463cacc8ddf923e399cb.png"));
    }
}
