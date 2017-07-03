package lins.com.qz.thirdparty.codecamera;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import lins.com.qz.R;
import lins.com.qz.databinding.ActivityEncodeQrBinding;
import lins.com.qz.thirdparty.codecamera.utils.EncodingUtils;
import lins.com.qz.thirdparty.codecamera.utils.UIUtils;
import lins.com.qz.ui.base.BaseActivity;

import static lins.com.qz.App.userName;
import static lins.com.qz.R.drawable.user;


public class EncodeQrActivity extends BaseActivity {
	//push test
	private Bitmap mLogoBtm;	// login图标
	public static String CONTENT = "www.baidu.com";
	ActivityEncodeQrBinding binding;
	//String url = "gotoapp://apphost/openwith?name=zhangsan&age=26";

	@Override
	protected void initView() {
		binding = DataBindingUtil.setContentView(this,R.layout.activity_encode_qr);
		binding.toolbar.tvTopTitle.setText("我的二维码");
		CONTENT = "gotoapp://apphost/openwith?name="+userName+"&age=100";
	}

	@Override
	protected void initEvent() {
		mLogoBtm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
		Bitmap qrCode = EncodingUtils.createQRCode(CONTENT, UIUtils.dip2px(400), mLogoBtm);
		binding.ivEncode.setImageBitmap(qrCode);
	}

	@Override
	protected void getData() {

	}

}
