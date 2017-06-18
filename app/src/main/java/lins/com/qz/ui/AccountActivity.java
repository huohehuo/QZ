package lins.com.qz.ui;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lins.com.qz.App;
import lins.com.qz.R;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.databinding.ActivityAccountBinding;
import lins.com.qz.utils.SelectPhotoDialog;

import static lins.com.qz.R.id.ivImage;

public class AccountActivity extends BaseActivity {
    ActivityAccountBinding binding;
    public static final int REQUEST_CHANGE_AVATAR=0;
    private SelectPhotoDialog selectPhotoDialog;
    private ViewPager mViewPager;
    View oldView;
    LayoutInflater inflater;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_account);
        binding.toolbar.ivTopArrow.setImageResource(R.drawable.back);
        binding.toolbar.tvTopTitle.setText("个人资料");
        binding.toolbar.tvTopRight.setText("修改");

        Glide.with(AccountActivity.this)
                .load("https://www.baidu.com/img/mother_483c0201c53a4bc3c2e15e968723b25a.png")
                .into(binding.ivImage);
        if (App.getDaoManager().lUserQuery().getIconurl()!=null
                &&!"".equals(App.getDaoManager().lUserQuery().getIconurl())){
            Glide.with(AccountActivity.this)
                    .load(App.getDaoManager().lUserQuery().getIconurl())
//                .placeholder(R.drawable.ic_account_circle)
                    .fitCenter()
                    .into(binding.ivicon);
        }else{
            Glide.with(AccountActivity.this)
                    .load("https://github.com/huohehuo/QZ/blob/master/app/src/main/res/drawable/mricon.png?raw=true")
//                .placeholder(R.drawable.ic_account_circle)
                    .fitCenter()
                    .into(binding.ivicon);
        }

//        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(binding.viewpager);
        final View view = LayoutInflater.from(this).inflate(R.layout.test,null);
        view.setBackground(getResources().getDrawable(R.color.error_red));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//        tabLayout.addTab(tabLayout.newTab().setText("个人简介").setIcon(R.drawable.about));
//        tabLayout.addTab(tabLayout.newTab().setText("周计划").setIcon(R.drawable.about));
//        tabLayout.addTab(tabLayout.newTab().setText("目录").setIcon(R.drawable.about));
        tabLayout.setupWithViewPager(binding.viewpager);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                tab.setIcon(R.drawable.about);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

//    private void initListener() {
//        binding.icBottom.btnMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeTab(binding.icBottom.btnMe);
//            }
//        });
//        binding.icBottom.btnMsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeTab(binding.icBottom.btnMsg);
//            }
//        });
//        binding.icHead.ivAccountIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SelectPicUtil.showSelectPhoto(AccountActivity.this,REQUEST_CHANGE_AVATAR, Config.PATH_SELECT_AVATAR,selectPhotoDialog);
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK){
//            switch (requestCode){
//                case REQUEST_CHANGE_AVATAR:
//                    // Glide.with(EditMyInfoActivity.this).load(new File(URL.PATH_SELECT_AVATAR)).into(binding.ciAvatar);
//                    Bitmap bitmap = BitmapFactory.decodeFile(Config.PATH_SELECT_AVATAR);
//                    binding.icHead.ivAccountIcon.setImageBitmap(bitmap);
//                    break;
//            }
//        }
//
//    }


    @Override
    protected void initEvent() {
        binding.toolbar.ivTopArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.toolbar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(EditUserInfoActivity.class);
            }
        });
    }

    @Override
    protected void getData() {

    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DetailFragment.newInstance("有没有很棒"),"内容简介");
        adapter.addFragment(new FriendFragment(),"作者简介");
        adapter.addFragment(DetailFragment.newInstance( "有有有"),"目录");
        mViewPager.setAdapter(adapter);
    }

    static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
