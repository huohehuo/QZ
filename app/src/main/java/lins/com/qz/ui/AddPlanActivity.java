package lins.com.qz.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import lins.com.qz.Config;
import lins.com.qz.R;
import lins.com.qz.bean.PlanBean;
import lins.com.qz.bean.User;
import lins.com.qz.databinding.ActivityAddPlanBinding;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.utils.SelectPhotoDialog;
import lins.com.qz.utils.SelectPicUtil;

public class AddPlanActivity extends BaseActivity {
    ActivityAddPlanBinding binding;
    private static final int REQUEST_CHANGE_AVATAR=2;

    private SelectPhotoDialog selectPhotoDialog;
    private boolean hasPic=false;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_plan);
        binding.toolbar.tvTopTitle.setText("发布计划");
        binding.toolbar.tvTopRight.setText("PUSH");
        selectPhotoDialog=new SelectPhotoDialog(AddPlanActivity.this, R.style.CustomDialog);

    }

    @Override
    protected void initEvent() {
        binding.toolbar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("上传中...");
                if (checkView()){
                    pushData();
                }
            }
        });

        binding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPicUtil.showSelectPhoto(AddPlanActivity.this,REQUEST_CHANGE_AVATAR, Config.PATH_SELECT_PLAN,selectPhotoDialog);

            }
        });

    }

    @Override
    protected void getData() {

    }

    private boolean checkView(){
        if ("".equals(binding.etEssay.getText().toString())){
            showToast("请输入内容");
            return false;
        }
        return true;
    }

    private void pushData(){
        final User user = BmobUser.getCurrentUser(User.class);
        final PlanBean planBean = new PlanBean();
        if (hasPic){
            File files =new File(Config.PATH_SELECT_PLAN);
            final BmobFile file = new BmobFile(files);
            file.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e==null){
                        planBean.setEssay(binding.etEssay.getText().toString());
                        planBean.setBg_pic(file.getFileUrl());
                        planBean.setType("2");//类别：1：纯文字，2：图文
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
                    }else{
                        showToast("发送失败---图片文件丢失");
                        Log.e("pushPic","error pushPic:" + e.getMessage());
                    }
                    closeDialog();
                }
            });
        }else{
            planBean.setEssay(binding.etEssay.getText().toString());
            planBean.setBg_pic("");
            planBean.setType("1");//类别：1：纯文字，2：图文
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
                    closeDialog();
                }
            });
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
//                case REQUEST_NICKNAME:
////                    binding.tvNickName.setText(data.getStringExtra(RESULT));
//                    break;
//                case REQUEST_MOTTO:
////                    binding.tvMotto.setText(data.getStringExtra(RESULT));
//                    break;
                case REQUEST_CHANGE_AVATAR:
                    Log.e("pp","获取到图片");
                    // Glide.with(EditMyInfoActivity.this).load(new File(URL.PATH_SELECT_AVATAR)).into(binding.ciAvatar);
                    Bitmap bitmap = BitmapFactory.decodeFile(Config.PATH_SELECT_PLAN);
                    if (bitmap!=null){
                        hasPic =true;
                    }
                    binding.ivAdd.setImageBitmap(bitmap);
                    Log.e("icon",Config.PATH_SELECT_PLAN);

                    break;
            }
        }
    }
}
