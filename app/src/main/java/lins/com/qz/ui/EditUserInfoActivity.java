package lins.com.qz.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobUser;
import lins.com.qz.Config;
import lins.com.qz.R;
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
