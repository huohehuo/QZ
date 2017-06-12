package lins.com.qz.ui;

import android.databinding.DataBindingUtil;

import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.R;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.bean.AddAdrMsg;
import lins.com.qz.databinding.ActivityShowBinding;

public class ShowActivity extends BaseActivity {
    ActivityShowBinding binding;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show);
        AddAdrMsg shuo = (AddAdrMsg) App.getHashData(Config.ACTIVITY_SEND_DATA);
        binding.tvTitile.setText(shuo.getTitle());
        binding.tvWord.setText(shuo.getAddr());
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void getData() {

    }
}
