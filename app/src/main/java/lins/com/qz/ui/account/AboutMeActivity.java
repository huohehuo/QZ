package lins.com.qz.ui.account;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bumptech.glide.Glide;

import lins.com.qz.App;
import lins.com.qz.R;
import lins.com.qz.adapter.AboutMeAdapter;
import lins.com.qz.databinding.ActivityAboutMeBinding;
import lins.com.qz.ui.EditUserInfoActivity;
import lins.com.qz.ui.base.BaseActivity;

import static lins.com.qz.App.userName;

public class AboutMeActivity extends BaseActivity {
    ActivityAboutMeBinding binding;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_about_me);
        binding.toolbar.ivTopArrow.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        binding.toolbar.tvTopTitle.setText("个人信息");
        binding.toolbar.tvTopRight.setText("修改");

        setupViewPager(binding.viewpager);
        binding.tabAboutMe.setupWithViewPager(binding.viewpager);
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
        Intent getAction = getIntent();
        if (getAction!=null){
            String action = getAction.getAction();
            if(Intent.ACTION_VIEW.equals(action)){
                Uri uri = getAction.getData();
                if(uri != null){
                    String name = uri.getQueryParameter("name");
                    String age= uri.getQueryParameter("age");
                    binding.tvName.setText(name);
                    binding.tvSay.setText(age);
//                binding.tvMe.setText(name+"\n"+age);
                }
            }
        }else{
            binding.tvName.setText(App.userName);
            binding.tvSay.setText(App.userName);
        }

        binding.toolbar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(EditUserInfoActivity.class);
            }
        });
    }
    private void setupViewPager(ViewPager mViewPager) {
        AboutMeAdapter adapter = new AboutMeAdapter(getSupportFragmentManager());
        adapter.addFragment(DetailFragment.newInstance("有没有很棒"),"详细资料");
        adapter.addFragment(new PlanFragment(),"我的计划");
//        adapter.addFragment(DetailFragment.newInstance( "有有有"),"关注");
        mViewPager.setAdapter(adapter);
    }
    @Override
    protected void getData() {
        App.e("dd",userName);
        String icon= App.getDaoManager().query(userName).getIconurl();
        App.e("About",icon);
        if (icon!=null &&!"".equals(icon)){
            App.e("About__have",icon);
            Glide.with(AboutMeActivity.this)
                    .load(icon)
//                .placeholder(R.drawable.ic_account_circle)
                    .fitCenter()
                    .into(binding.ivicon);
            Glide.with(AboutMeActivity.this)
                    .load(icon)
//                .placeholder(R.drawable.ic_account_circle)
                    .fitCenter()
                    .into(binding.ivImage);
        }else{
            App.e("About__nothing",icon);
            Glide.with(AboutMeActivity.this)
                    .load("https://github.com/huohehuo/QZ/blob/master/app/src/main/res/drawable/mricon.png?raw=true")
//                .placeholder(R.drawable.ic_account_circle)
                    .fitCenter()
                    .into(binding.ivicon);
            Glide.with(AboutMeActivity.this)
                    .load("https://github.com/huohehuo/QZ/blob/master/app/src/main/res/drawable/mricon.png?raw=true")
//                .placeholder(R.drawable.ic_account_circle)
                    .fitCenter()
                    .into(binding.ivImage);
        }
    }
}
