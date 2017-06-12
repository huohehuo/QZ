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

import lins.com.qz.R;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.databinding.ActivityAccountBinding;
import lins.com.qz.utils.SelectPhotoDialog;

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
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//        initListener();
//        oldView = binding.icBottom.btnMe;
//        oldView.setSelected(true);
//        selectPhotoDialog=new SelectPhotoDialog(AccountActivity.this, R.style.MyDialog);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("标题");

        ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
        Glide.with(ivImage.getContext())
                .load("https://www.baidu.com/img/mother_483c0201c53a4bc3c2e15e968723b25a.png")
                .fitCenter()
                .into(ivImage);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);
        final View view = LayoutInflater.from(this).inflate(R.layout.test,null);
        view.setBackground(getResources().getDrawable(R.color.error_red));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("内容简介"));
        tabLayout.addTab(tabLayout.newTab().setText("作者简介"));
        tabLayout.addTab(tabLayout.newTab().setText("目录"));
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setCustomView(view);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(R.drawable.about);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    private void changeTab(View v){
        oldView.setSelected(false);
        oldView = v;
        oldView.setSelected(true);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void getData() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
