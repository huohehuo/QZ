package lins.com.qz.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import lins.com.qz.R;
import lins.com.qz.bean.PlanBean;
import lins.com.qz.bean.User;
import lins.com.qz.databinding.ActivityAddPlanBinding;
import lins.com.qz.ui.base.BaseActivity;

public class AddPlanActivity extends BaseActivity {
    ActivityAddPlanBinding binding;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_plan);
        binding.toolbar.tvTopTitle.setText("发布计划");
        binding.toolbar.tvTopRight.setText("PUSH");



    }

    @Override
    protected void initEvent() {
        binding.toolbar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = BmobUser.getCurrentUser(User.class);
                PlanBean planBean = new PlanBean();
                planBean.setEssay(binding.etEssay.getText().toString());
                planBean.setAuthor(user);
                planBean.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null){
                            showToast("发布成功");
                            finish();
                        }else{
                            showToast("发布失败");
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void getData() {

    }
}
