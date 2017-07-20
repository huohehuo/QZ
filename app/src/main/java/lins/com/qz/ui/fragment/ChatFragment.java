package lins.com.qz.ui.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import lins.com.qz.R;
import lins.com.qz.adapter.AboutMeAdapter;
import lins.com.qz.adapter.MainAdapter;
import lins.com.qz.bean.PlanBean;
import lins.com.qz.bean.User;
import lins.com.qz.databinding.FgChatBinding;
import lins.com.qz.databinding.FgSquareBinding;
import lins.com.qz.ui.ShowPlanActivity;


/**
 * Created by LINS on 2017/2/22.
 */

public class ChatFragment extends Fragment {
    public static final String TAG = "three_fragment";
    FgChatBinding binding;
    private MainAdapter adapter;
    private View rootView;
    private Fragment mConversationList;
    private Fragment mConversationFragment = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null){
            binding = DataBindingUtil.inflate(inflater, R.layout.fg_chat,container,false);
            rootView = binding.getRoot();
            mConversationList  = initConversationList();//获取融云会话列表对象

            setupViewPager(binding.fgVg);
            binding.tabFgChat.setupWithViewPager(binding.fgVg);
            getData();
            initEven();
            return binding.getRoot();
        }


        Log.e("plan","onCreateView");
        return rootView;
    }

    //绑定事件
    private void initEven(){

    }
    //获取数据
    protected void getData() {

    }


    private void setupViewPager(ViewPager mViewPager) {
        AboutMeAdapter adapter = new AboutMeAdapter(getFragmentManager());
        adapter.addFragment(mConversationList,"消息");
        adapter.addFragment(ChatFriendFragment.getInstance(),"我的好友");
        mViewPager.setAdapter(adapter);
    }


    //动态加载会话列表，就不用在清单文件设置相应的data，启动会话列表也不用使用固定的instance启动
    private Fragment initConversationList() {
        if (mConversationFragment ==null){
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://"+getActivity().getApplicationInfo().packageName).buildUpon()
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











    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("plan","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();        Log.e("plan","onStart");

    }

    @Override
    public void onResume() {
        super.onResume();        Log.e("plan","onResume");

    }

    @Override
    public void onPause() {
        super.onPause();Log.e("plan","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();        Log.e("plan","onStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();        Log.e("plan","onDestroy");

    }

    @Override
    public void onDetach() {
        super.onDetach();        Log.e("plan","onDetach");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();        Log.e("plan","onDestroyView");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);Log.e("plan","onAttach");
    }
}
