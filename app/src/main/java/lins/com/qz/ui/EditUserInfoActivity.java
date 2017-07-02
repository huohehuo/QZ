package lins.com.qz.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.R;
import lins.com.qz.bean.Friend;
import lins.com.qz.bean.Friends;
import lins.com.qz.bean.User;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.bean.PickerViewBean;
import lins.com.qz.databinding.ActivityEditUserInfoBinding;
import lins.com.qz.utils.SelectPhotoDialog;
import lins.com.qz.utils.SelectPicUtil;

import static lins.com.qz.R.drawable.close;
import static lins.com.qz.R.drawable.user;

public class EditUserInfoActivity extends BaseActivity {
    ActivityEditUserInfoBinding binding;
    private static final int    REQUEST_NICKNAME = 0;
    private static final int    REQUEST_MOTTO    = 1;
    private static final String RESULT           = "result";
    private static final int REQUEST_CHANGE_AVATAR=2;
    private SelectPhotoDialog selectPhotoDialog;
    ArrayList<String> constellationList;
    ArrayList<PickerViewBean>    provinces;
    ArrayList<ArrayList<String>> cities;
    OptionsPickerView locationPicker, constellationPicker;
    BmobUser bmobUser;
    private String iconUrl;
    private String userName;
    String sex = "1";
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_user_info);
        binding.toolbar.tvTopRight.setText("保存");
        binding.toolbar.tvTopTitle.setText("个人资料");
        //图片选择器
        selectPhotoDialog=new SelectPhotoDialog(EditUserInfoActivity.this, R.style.CustomDialog);
        //获取本地Bmob用户对象
        bmobUser = BmobUser.getCurrentUser();
        userName = bmobUser.getUsername();
        App.e("edit","更新前："+userName);
    }

    @Override
    protected void initEvent() {
        initLocation();
        initConstellation();
        initPicker();

        //图片选择
        binding.ivEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPicUtil.showSelectPhoto(EditUserInfoActivity.this,REQUEST_CHANGE_AVATAR, Config.PATH_SELECT_AVATAR,selectPhotoDialog);
            }
        });
        //星座
        binding.tvStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constellationPicker.show();
            }
        });
        //所在地
        binding.tvAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationPicker.show();
            }
        });
        //星座选择
        constellationPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                binding.tvStar.setText(constellationList.get(options1));
            }
        });
        //所在地选择
        locationPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                binding.tvAddr.setText(provinces.get(options1).getStr() + cities.get(options1).get(option2));
            }
        });

        binding.toolbar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("更新中...");
                if (checkOutInput()){
                    updataUser();
                }else{
                    showToast("请输入相应数据");
                }
            }
        });


//        Friends friends = new Friends();
//        friends.setObjectId(App.getObjectId());
//        friends.addUnique("friendlist",new Friend("5a57b5b19e","asdf",
//                "https://www.baidu.com/img/mother_483c0201c53a4bc3c2e15e968723b25a.png","nothing"));
//        friends.addUnique("friendlist",new Friend("6d6dc15ee1","ww",
//                "https://www.baidu.com/img/mother_483c0201c53a4bc3c2e15e968723b25a.png","nothing"));
//        friends.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    Log.e("bmob","保存成功");
//                }else{
//                    Log.e("bmob","保存失败："+e.getMessage());
//                }
//            }
//        });


    }

    @Override
    protected void getData() {
        binding.etName.setText(userName);//昵称
        String note = App.getDaoManager().query(bmobUser.getUsername()).getNote();
        if (note!=null){
            binding.etNote.setText(note);
        }
        //头像
        String icon = App.getDaoManager().query(bmobUser.getUsername()).getIconurl();
        if (icon!=null){
            Glide.with(this).load(icon)
                    .into(binding.ivEditIcon);
        }

    }

    //星座初始化
    void initConstellation() {
        constellationList = new ArrayList<>();
        constellationList.add(getString(R.string.aries));
        constellationList.add(getString(R.string.taurus));
        constellationList.add(getString(R.string.gemini));
        constellationList.add(getString(R.string.cancer));
        constellationList.add(getString(R.string.leo));
        constellationList.add(getString(R.string.virgo));
        constellationList.add(getString(R.string.libra));
        constellationList.add(getString(R.string.scorpio));
        constellationList.add(getString(R.string.sagittarius));
        constellationList.add(getString(R.string.capricorn));
        constellationList.add(getString(R.string.aquarius));
        constellationList.add(getString(R.string.pisces));
    }

    //所在地初始化
    void initLocation() {
        provinces = new ArrayList<>();
        cities = new ArrayList<>();
        List<String> provincesStrList = Arrays.asList(getString(R.string.provinces).split(","));
        for (String prov : provincesStrList) {
            provinces.add(new PickerViewBean(prov));
        }

        String[] citiesStrList = getResources().getStringArray(R.array.cities);
        for (String c : citiesStrList) {
            List<String> p_cities = Arrays.asList(c.split(","));
            ArrayList<String> pickerViewBeen = new ArrayList<>();
            for (String city : p_cities) {
                pickerViewBeen.add(city);
            }
            cities.add(pickerViewBeen);
        }
    }

    private void initPicker() {
        // 星座
        constellationPicker = new OptionsPickerView(this);
        constellationPicker.setPicker(constellationList);
        constellationPicker.setCyclic(false);
        constellationPicker.setSelectOptions(0);

        // 所在地
        locationPicker = new OptionsPickerView(this);
        locationPicker.setPicker(provinces, cities, true);
        locationPicker.setCyclic(false, false, false);
        locationPicker.setSelectOptions(0, 0);
    }


//    setUnselected("0");//设置性别选项
//    void setUnselected(String sexNow){
//        if (sex.equals(sexNow)) {
//            return;
//        }
//        switch (sex) {
//            case "0":
//                // female
//                binding.tvFemale.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.female_unselected),null,null,null);
//                break;
//            case "1":
//                // male
//                binding.tvMale.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.male_unselected),null,null,null);
//                break;
//            case "2":
//                // keep secret
//                binding.tvKeepSecret.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.keep_secret),null,null,null);
//                break;
//        }
//        sex = sexNow;
//    }

    private boolean checkOutInput(){
        if (TextUtils.isEmpty(binding.etName.getText().toString())){
            showToast("请输入昵称");
            return false;
        }
//        if (TextUtils.isEmpty(binding.etNote.getText().toString())){
//            showToast("请输入");
//            return false;
//        }
        return true;
    }

    //更新用户数据
    private void updataUser(){
        //更新
        File file =new File(Config.PATH_SELECT_AVATAR);
        //若上一步获取图片失败（或者本地历史存过的地址的缓存图片被删除），则不更新头像，只更新昵称签名之类的
        if (file==null){
            User user = new User();
            user.setUsername(binding.etName.getText().toString());
            user.setNote("".equals(binding.etNote.getText().toString())?"":binding.etNote.getText().toString());
            user.update(bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e==null){
                        //由于更新前后的昵称可能会有变化，所以本地数据库也要更新相应的昵称,签名，头像
                        App.getDaoManager().updateUserIconNameNote(userName,"",
                                BmobUser.getCurrentUser().getUsername(),
                                binding.etNote.getText().toString());
                        App.e("updata","用户数据更新成功");
                        closeDialgWithActivity();
                    }else{
                        closeDialog();
                        showToast("更新失败，请重试");
                    }
                }
            });
            return;
        }else{
            final BmobFile bmobFile = new BmobFile(file);
            bmobFile.uploadblock(new UploadFileListener() {

                @Override
                public void done(BmobException e) {
                    if(e==null){
                        User user = new User();
                        user.setUsername(binding.etName.getText().toString());
                        user.setNote("".equals(binding.etNote.getText().toString())?"":binding.etNote.getText().toString());
                        user.setIconpic(bmobFile.getFileUrl());
                        user.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    //由于更新前后的昵称可能会有变化，所以本地数据库也要更新相应的昵称,签名，头像
                                    App.getDaoManager().updateUserIconNameNote(userName,bmobFile.getFileUrl(),
                                            BmobUser.getCurrentUser().getUsername(),
                                            binding.etNote.getText().toString());
                                    App.e("updata","用户数据更新成功");
                                    closeDialgWithActivity();
                                }else{
                                    closeDialog();
                                    showToast("更新失败，请重试");
                                }
                            }
                        });
                        Log.e("updataIcon","success updata"+ bmobFile.getFileUrl());
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    }else{
                        Log.e("updataIcon","error updata" + e.getMessage());

                    }

                }

                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_NICKNAME:
//                    binding.tvNickName.setText(data.getStringExtra(RESULT));
                    break;
                case REQUEST_MOTTO:
//                    binding.tvMotto.setText(data.getStringExtra(RESULT));
                    break;
                case REQUEST_CHANGE_AVATAR:
                    Log.e("pp","获取到图片");
                    // Glide.with(EditMyInfoActivity.this).load(new File(URL.PATH_SELECT_AVATAR)).into(binding.ciAvatar);
                    Bitmap bitmap = BitmapFactory.decodeFile(Config.PATH_SELECT_AVATAR);
                    binding.ivEditIcon.setImageBitmap(bitmap);
                    Log.e("icon",Config.PATH_SELECT_AVATAR);


                    /*Qiniu.uploadFile(URL.PATH_SELECT_AVATAR, new Qiniu.Callback() {
                        @Override
                        public void uploadResult(String remoteUrl, boolean ok, String error) {
                            if (!ok) {
                                Log.e("EditmyInfoActivity-----",error);
                                return;
                            }else{
                                Log.e("EditMyInfoActivity-----","成功上传。。。。");
                            }
                            selectPhotoDialog.hide();
                            avatar = remoteUrl;
                        }
                    });*/
                    break;
            }
        }
    }
}
