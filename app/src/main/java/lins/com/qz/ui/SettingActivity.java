package lins.com.qz.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.MainActivity;
import lins.com.qz.R;
import lins.com.qz.databinding.ActivitySettingBinding;
import lins.com.qz.ui.base.BaseActivity;
import lins.com.qz.utils.share.QShareIO;
import lins.com.qz.utils.share.QShareListener;

public class SettingActivity extends BaseActivity implements QShareIO  {
    ActivitySettingBinding binding;
    private Tencent mTencent = App.getTencent();


    private QShareIO qShareIO = new QShareIO() {
        @Override
        public void getShareData(String str) {
            showToast("分享成功");
            Log.e("share","分享成功");
        }
    };

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setting);
        setToolbarBack(binding.toolbar.ivTopArrow);
        binding.toolbar.tvTopLeft.setText("设置");


    }

    @Override
    protected void initEvent() {
        binding.rlUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Aria.download(this)
//                        .load(Config.UPDATA_APK)     //读取下载地址
//                        .setDownloadPath(Config.UPDATA_PATH)    //设置文件保存的完整路径
//                        .start();   //启动下载
            }
        });

        binding.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
                mTencent.shareToQQ(SettingActivity.this, params, new QShareListener(qShareIO));
            }
        });


    }

    @Override
    protected void getData() {

    }

    @Override
    public void getShareData(String str) {
        Log.e("back",str);
        showToast(str);
    }
}
