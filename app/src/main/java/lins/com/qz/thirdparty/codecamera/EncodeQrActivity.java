package lins.com.qz.thirdparty.codecamera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import lins.com.qz.R;
import lins.com.qz.thirdparty.codecamera.utils.EncodingUtils;
import lins.com.qz.thirdparty.codecamera.utils.UIUtils;


public class EncodeQrActivity extends Activity {
	//push test
	private Bitmap mLogoBtm;	// login图标
	private ImageView mQrIv;	// 二维码图片控件
	public static String CONTENT = "安徽辛普信息科技有限公司";
	//push test 2
	//push test 3
	//push test 4
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encode_qr);
		
		mLogoBtm = BitmapFactory.decodeResource(getResources(), R.drawable.logo_anhuixinpu);
		mQrIv = (ImageView) findViewById(R.id.iv_encode);
		
		Bitmap qrCode = EncodingUtils.createQRCode(CONTENT, UIUtils.dip2px(200), mLogoBtm);
		mQrIv.setImageBitmap(qrCode);
	}

}
