package lins.com.qz.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import lins.com.qz.R;
import lins.com.qz.databinding.ActivityAboutMeBinding;
import lins.com.qz.ui.base.BaseActivity;

public class AboutMeActivity extends BaseActivity {
    ActivityAboutMeBinding binding;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_about_me);
        binding.toolbar.ivTopArrow.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        binding.toolbar.tvTopTitle.setText("个人信息");

    }

    @Override
    protected void initEvent() {
        //<a href="myapp://jp.app/openwith?name=zhangsan&age=26">启动应用程序</a>
        //通过下列方式，在其他activity中启动本acitivity，接收相应数据
        /*String url = "gotoapp://apphost/openwith?name=zhangsan&age=26";
        String scheme = Uri.parse(url).getScheme();//还需要判断host
        if (TextUtils.equals("gotoapp", scheme)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }*/
        Intent i_getvalue = getIntent();
        String action = i_getvalue.getAction();

        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = i_getvalue.getData();
            if(uri != null){
                String name = uri.getQueryParameter("name");
                String age= uri.getQueryParameter("age");
                binding.tvMe.setText(name+"\n"+age);
            }
        }

    }

    @Override
    protected void getData() {

    }
}
