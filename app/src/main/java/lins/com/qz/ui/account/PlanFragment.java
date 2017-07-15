package lins.com.qz.ui.account;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lins.com.qz.MainActivity;
import lins.com.qz.R;
import lins.com.qz.adapter.FriendAdapter;
import lins.com.qz.adapter.PlanAdapter;
import lins.com.qz.bean.PlanBean;
import lins.com.qz.bean.User;
import lins.com.qz.databinding.FragmentFriendBinding;
import lins.com.qz.thirdparty.rotatingtext.RotatingTextWrapper;
import lins.com.qz.thirdparty.rotatingtext.models.Rotatable;
import lins.com.qz.ui.ShowPlanActivity;


/**
 * Created by LINS on 2017/2/22.
 */

public class PlanFragment extends Fragment {
    FragmentFriendBinding binding;
    private PlanAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend,container,false);

        binding.ryPlan.setAdapter(adapter = new PlanAdapter(getActivity()));
        binding.ryPlan.setLayoutManager(new LinearLayoutManager(getActivity()));

        getData();
        initEven();
        Log.e("plan","onCreateView");
        return binding.getRoot();
    }

    //绑定事件
    private void initEven(){
        binding.ryPlan.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ShowPlanActivity.start(
                        getActivity(),
                        adapter.getAllData().get(position).getObjectId(),
                        adapter.getAllData().get(position).getEssay(),
                        adapter.getAllData().get(position).getCreatedAt());
            }
        });
    }
    //获取数据
    protected void getData() {
        binding.ryPlan.setRefreshing(true);
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<PlanBean> query = new BmobQuery<>();
        query.addWhereEqualTo("author",user);// 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(new FindListener<PlanBean>() {
            @Override
            public void done(List<PlanBean> list, BmobException e) {
                if (e==null){
                    adapter.clear();
                    adapter.addAll(list);
                }else{
                    Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                }
                binding.ryPlan.setRefreshing(false);
            }
        });
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
