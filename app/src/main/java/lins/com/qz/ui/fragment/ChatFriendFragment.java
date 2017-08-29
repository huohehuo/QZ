package lins.com.qz.ui.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import lins.com.qz.App;
import lins.com.qz.R;
import lins.com.qz.adapter.FriendAdapter;
import lins.com.qz.bean.PlanBean;
import lins.com.qz.bean.User;
import lins.com.qz.bean.locationBean.LChatFrds;
import lins.com.qz.databinding.FragmentMyFriendBinding;
import lins.com.qz.manager.FrdsManager;
import lins.com.qz.utils.BadgeUtil;
import lins.com.qz.utils.IntentServiceUtil.InitChatService;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {link ChatFriendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFriendFragment extends Fragment {
    private String tag = "ChatFriendFragment";
    private FrdsManager frdsManager;
    private FriendAdapter adapter;
    FragmentMyFriendBinding binding;
    public static ChatFriendFragment instance = null;

    public static ChatFriendFragment getInstance() {
        if (instance == null) {
            instance = new ChatFriendFragment();
        }
        return instance;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public ChatFriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFriendFragment newInstance(String param1, String param2) {
        ChatFriendFragment fragment = new ChatFriendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(tag, "onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        frdsManager = new FrdsManager();
    }

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(tag, "onCreateView");
        if (rootView == null) {
            // Inflate the layout for this fragment
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_friend, container, false);
            rootView = binding.getRoot();


            //Find the +1 butto
            binding.ryFriendlist.setAdapter(adapter = new FriendAdapter(getActivity()));
            binding.ryFriendlist.setLayoutManager(new LinearLayoutManager(getActivity()));


            getAllUser();

//        //查询数据库并添加好友信息
//        adapter.addAll(frdsManager.queryAll());

            //刷新
            binding.ryFriendlist.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //查询数据库并添加好友信息
//                adapter.addAll(frdsManager.queryAll());
                    getAllUser();
                    adapter.notifyDataSetChanged();
                }
            });

            //点击好友聊天
            adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Log.e("click", adapter.getAllData().get(position).getName());
//                App.whoTalk =adapter.getAllData().get(position);
                    /**
                     * 启动单聊界面。
                     *
                     * @param context      应用上下文。
                     * @param targetUserId 要与之聊天的用户 Id。
                     * @param title        聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
                     *                     获取该值, 再手动设置为聊天界面的标题。
                     */
                    RongIM.getInstance().startPrivateChat(getActivity(),
                            adapter.getAllData().get(position).getRongid(),
                            adapter.getAllData().get(position).getName());
                }
            });
            return binding.getRoot();

        }
        return rootView;
    }

    private void getAllUser() {
        binding.ryFriendlist.setRefreshing(true);
        BmobQuery<User> shuoBmobQuery = new BmobQuery<>();
//        shuoBmobQuery.addWhereEqualTo("username", "asdf");
//        shuoBmobQuery.setLimit(App.getHashMainNum(Config.NUM_OF_MAIN));
        shuoBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    adapter.clear();
                    binding.ryFriendlist.setRefreshing(false);
                    getUser(list);
                    ssss(list);
                    for (int i = 0; i < list.size(); i++) {
                        Log.e("getuser", list.get(i).toString());

                    }

                } else {
                    Log.e("queryUser", "查询用户失败");
                }
            }
        });
    }

    private void getUser(List<User> list) {
        List<LChatFrds> lChatFrdses = new ArrayList<>();
        for (User user : list) {
            if (App.userName.equals(user.getUsername())) {
                continue;
            }
            lChatFrdses.add(new LChatFrds(null, user.getUsername(), user.getRongid(), user.getIconpic()));
        }
        //查询数据库并添加好友信息
        adapter.addAll(lChatFrdses);
        new FrdsManager().insertList(lChatFrdses);
        InitChatService.initChatUser(getActivity());
        adapter.notifyDataSetChanged();
    }

    //查出相应的发布计划数据
    private void ssss(List<User> users) {
        User user = users.get(0);
        BmobQuery<PlanBean> query = new BmobQuery<>();
        query.addWhereEqualTo("author", user);// 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(new FindListener<PlanBean>() {
            @Override
            public void done(List<PlanBean> list, BmobException e) {
                if (e == null) {
                    Log.e("getUserList", list.get(0).toString());
                } else {
                    Log.e("getUserList", "错误。。。。。");
                }
            }
        });

    }


    private void getMessageNum() {
        // 设置未读消息监听数
        RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
            @Override
            public void onCountChanged(int i) {
                Log.e("数目：", "" + i);
                BadgeUtil.setBadgeCount(App.getContext(), i);
//                binding.content.toolbar.tvTopRight.setText(""+i);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(tag, "onResume");
        // Refresh the state of the +1 button each time the activity recei
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(tag, "onDestroy");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(tag, "onAttach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(tag, "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(tag, "onDestroyView");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(tag, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(tag, "onStop");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(tag, "onActivityCreated");
    }
}
