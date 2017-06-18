package lins.com.qz.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;

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
    String sex = "1";
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_user_info);
        binding.toolbar.tvTopRight.setText("保存");
        binding.toolbar.tvTopTitle.setText("个人资料");
        selectPhotoDialog=new SelectPhotoDialog(EditUserInfoActivity.this, R.style.CustomDialog);
        bmobUser = BmobUser.getCurrentUser();
        bmobUser.getEmail();

    }

    @Override
    protected void initEvent() {
        initLocation();
        initConstellation();
        initPicker();

        binding.ivEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPicUtil.showSelectPhoto(EditUserInfoActivity.this,REQUEST_CHANGE_AVATAR, Config.PATH_SELECT_AVATAR,selectPhotoDialog);
            }
        });
        binding.tvStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constellationPicker.show();
            }
        });
        binding.tvAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationPicker.show();
            }
        });
        constellationPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                binding.tvStar.setText(constellationList.get(options1));
            }
        });

        locationPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                binding.tvAddr.setText(provinces.get(options1).getStr() + cities.get(options1).get(option2));
            }
        });


        Friends friends = new Friends();
        friends.setObjectId(App.getObjectId());
        friends.addUnique("friendlist",new Friend("5a57b5b19e","asdf",
                "https://www.baidu.com/img/mother_483c0201c53a4bc3c2e15e968723b25a.png","nothing"));
        friends.addUnique("friendlist",new Friend("6d6dc15ee1","ww",
                "https://www.baidu.com/img/mother_483c0201c53a4bc3c2e15e968723b25a.png","nothing"));
        friends.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Log.e("bmob","保存成功");
                }else{
                    Log.e("bmob","保存失败："+e.getMessage());
                }
            }
        });


    }

    @Override
    protected void getData() {

    }
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
                    final BmobFile bmobFile = new BmobFile(new File(Config.PATH_SELECT_AVATAR));
                    bmobFile.uploadblock(new UploadFileListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                User user = new User();
                                user.setIconpic(bmobFile.getFileUrl());
                                user.update(bmobUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e==null){
                                            Log.e("updata","更新头像成功");
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
