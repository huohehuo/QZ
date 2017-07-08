package lins.com.qz.ui;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.MainActivity;
import lins.com.qz.R;
import lins.com.qz.adapter.MainAdapter;
import lins.com.qz.adapter.SysNotifyAdapter;
import lins.com.qz.bean.SysNotify;
import lins.com.qz.databinding.ActivitySysNotifyBinding;
import lins.com.qz.ui.base.BaseActivity;

import static lins.com.qz.R.id.toolbar;

public class SysNotifyActivity extends BaseActivity {
    ActivitySysNotifyBinding binding;
    private SysNotifyAdapter adapter;
    @Override
    protected void initView() {
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sys_notify);
        binding.toolbar.ivTopArrow.setImageResource(R.drawable.back);
        binding.toolbar.tvTopTitle.setText("系统活动");
        setToolbarBack(binding.toolbar.ivTopArrow);
        binding.rySysNotify.setAdapter(adapter = new SysNotifyAdapter(SysNotifyActivity.this));
        binding.rySysNotify.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initEvent() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                WebActivity.start(SysNotifyActivity.this,adapter.getAllData().get(position).getUrl());
            }
        });
        binding.rySysNotify.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    protected void getData() {
        binding.rySysNotify.setRefreshing(true);
        BmobQuery<SysNotify> query = new BmobQuery<>();
//        query.addWhereEqualTo("title",)
        query.findObjects(new FindListener<SysNotify>() {
            @Override
            public void done(List<SysNotify> list, BmobException e) {
                if (e==null){
                    Log.e("get",list.get(0).toString());
        adapter.clear();
                    adapter.addAll(list);
                }else{
                    showToast("error");
                }
                binding.rySysNotify.setRefreshing(false);
            }
        });
    }
}
