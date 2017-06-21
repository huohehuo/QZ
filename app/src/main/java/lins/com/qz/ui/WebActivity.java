package lins.com.qz.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import lins.com.qz.R;
import lins.com.qz.databinding.ActivityWebBinding;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.ui.login.LoginActivity;

public class WebActivity extends BaseActivity {

    ActivityWebBinding binding;
    Intent intent;
    String url="www.baidu.com";
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_web);
        binding.toolbar.ivTopArrow.setImageResource(R.drawable.back);
        binding.toolbar.tvTopTitle.setText("网页浏览");
        intent = getIntent();
        if (intent!=null){
            url = intent.getStringExtra("geturl");
        }

    }

    @Override
    protected void initEvent() {
        binding.wbAction.loadUrl(url);
        closeActivity(binding.toolbar.ivTopArrow);



    }

    @Override
    protected void getData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.wbAction.canGoBack()) {
            binding.wbAction.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param context
     */
    public static void start(Context context, String url) {
        Intent starter = new Intent(context, WebActivity.class);
        starter.putExtra("geturl", url);
        context.startActivity(starter);
    }
}
