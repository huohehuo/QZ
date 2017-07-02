package lins.com.qz.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lins.com.qz.R;
import lins.com.qz.adapter.FriendAdapter;
import lins.com.qz.databinding.FragmentFriendBinding;


/**
 * Created by LINS on 2017/2/22.
 */

public class FriendFragment extends Fragment {
    FragmentFriendBinding binding;
    private FriendAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend,container,false);

        binding.ryFriend.setAdapter(adapter = new FriendAdapter(getActivity()));
        binding.ryFriend.setLayoutManager(new LinearLayoutManager(getActivity()));


//        adapter.add("asdf");
//        adapter.add("asdf");
//        adapter.add("asdf");
//        adapter.add("asdf");
//        adapter.add("asdf");
//        adapter.add("asdf");
//        adapter.add("alskdjf");
//        adapter.add("alskdjf");
//        adapter.add("alskdjf");


        binding.ryFriend.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                adapter.add("很棒啊");
            }
        });

        return binding.getRoot();
    }
}
