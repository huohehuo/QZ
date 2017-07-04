package lins.com.qz.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.R;
import lins.com.qz.adapter.CommentAdapter;
import lins.com.qz.adapter.MainAdapter;
import lins.com.qz.bean.PlanBean;
import lins.com.qz.bean.PlanComment;
import lins.com.qz.bean.User;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.bean.AddAdrMsg;
import lins.com.qz.databinding.ActivityShowBinding;

public class ShowActivity extends BaseActivity {
    ActivityShowBinding binding;
    String essay,time,planid;
    CommentAdapter adapter;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show);
        binding.toolbar.tvTopTitle.setText("详情");
        binding.toolbar.tvTopRight.setText("发表评论");

        Intent intent = getIntent();
        if (intent!=null){
            essay =intent.getStringExtra("essay");
            time = intent.getStringExtra("time");
            planid = intent.getStringExtra("planid");
            binding.tvEssay.setText(essay);
            binding.tvTime.setText(time);
        }

        binding.ryComment.setAdapter(adapter = new CommentAdapter(ShowActivity.this));
        binding.ryComment.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initEvent() {
        binding.toolbar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = BmobUser.getCurrentUser(User.class);
                PlanBean plan = new PlanBean();
                plan.setObjectId(planid);
                PlanComment comment = new PlanComment();
                comment.setContent("lskdjflkjas");
                comment.setPlan(plan);
                comment.setUser(user);
                comment.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null){
                            showToast("评论成功");
                        }else{
                            showToast("评论失败");
                        }
                    }
                });

            }
        });
    }

    @Override
    protected void getData() {
        adapter.clear();
        BmobQuery<PlanComment> query = new BmobQuery<>();
        PlanBean plan = new PlanBean();
        plan.setObjectId(planid);
        query.addWhereEqualTo("plan",new BmobPointer(plan));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,plan.author");

        query.findObjects(new FindListener<PlanComment>() {
            @Override
            public void done(List<PlanComment> list, BmobException e) {
                if (e==null){
                    adapter.addAll(list);
                }
            }
        });

    }

    public static void start(Context context,String planid,String essay,String time) {
        Intent starter = new Intent(context, ShowActivity.class);
        starter.putExtra("planid",planid);
        starter.putExtra("essay",essay);
        starter.putExtra("time",time);
        context.startActivity(starter);
    }
}
