package lins.com.qz.ui;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import lins.com.qz.Config;
import lins.com.qz.R;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.bean.AddAddress;
import lins.com.qz.bean.AddAdrMsg;
import lins.com.qz.databinding.ActivityAddBinding;
import lins.com.qz.utils.PopupWindowUtil;

import static lins.com.qz.App.getHashData;

/**
 * Created by LINS on 2017/5/2.
 */

public class addActivity extends BaseActivity{
    ActivityAddBinding binding;
    ArrayList<String> addressList;
    OptionsPickerView adrrPick;
    private String addr="all";
    BmobUser bmobUser;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add);
        binding.toolbar.tvTopRight.setText("确定");
        binding.toolbar.tvTopTitle.setText("发布");
        bmobUser = BmobUser.getCurrentUser();
    }

    @Override
    protected void initEvent() {
        initAddrData();
        binding.toolbar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAdrMsg shuo = new AddAdrMsg(binding.etTitle.getText().toString(),addr);
                shuo.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null){
                            showToast("success");
                            finish();
                        }else{
                            showToast("error");
                        }
                    }
                });
            }
        });
        //弹出地址选择
        binding.tvAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adrrPick.show();
            }
        });
        //弹出对话框，添加地址
        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addActivity.this.isFinishing()){
                    PopupWindowUtil.getSystemUtils(addActivity.this).popupWindow(changeView);
                }
            }
        });
        //选项卡点击回调
        adrrPick.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                addr = addressList.get(options1);
                binding.tvAddr.setText(addressList.get(options1));
            }
        });
    }
    final ChangeView changeView = new ChangeView() {
        @Override
        public void changeData(String string) {
            AddAddress addAddress = new AddAddress(bmobUser.getObjectId(),string);
            addAddress.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e==null){
                        showToast("添加地址成功");
                    }else{
                        showToast("添加地址——失败，请重试");
                    }
                }
            });
        }
    };

    void initAddrData() {
        addressList = new ArrayList<>();
        adrrPick = new OptionsPickerView(this);
        if (getHashData(Config.ADDRESS_LIST)!=null){
            for (AddAddress adr: (List<AddAddress>)getHashData(Config.ADDRESS_LIST)
                 ) {
                addressList.add(adr.getAddr());
            }
        }else{
            addressList.add("nothing");
        }

        adrrPick.setPicker(addressList);
        adrrPick.setCyclic(false);
        adrrPick.setSelectOptions(0);
    }

    @Override
    protected void getData() {

    }
}
