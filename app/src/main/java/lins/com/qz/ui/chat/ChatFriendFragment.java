package lins.com.qz.ui.chat;

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

import io.rong.imkit.RongIM;
import lins.com.qz.R;
import lins.com.qz.adapter.FriendAdapter;
import lins.com.qz.databinding.FragmentMyFriendBinding;
import lins.com.qz.manager.FrdsManager;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

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
    public static ChatFriendFragment getInstance(){
        if (instance == null){
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
        Log.e(tag,"onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        frdsManager = new FrdsManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(tag,"onCreateView");

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_friend,container,false);
        //Find the +1 butto
        binding.ryFriendlist.setAdapter(adapter = new FriendAdapter(getActivity()));
        binding.ryFriendlist.setLayoutManager(new LinearLayoutManager(getActivity()));

        //查询数据库并添加好友信息
        adapter.addAll(frdsManager.queryAll());


        binding.ryFriendlist.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                adapter.add("很棒啊");
            }
        });

        binding.ryFriendlist.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //查询数据库并添加好友信息
                adapter.addAll(frdsManager.queryAll());
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.e("click",adapter.getAllData().get(position).getName());
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

    @Override
    public void onResume() {
        super.onResume();
        Log.e(tag,"onResume");
        // Refresh the state of the +1 button each time the activity recei
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(tag,"onActivityCreated");
    }
}
