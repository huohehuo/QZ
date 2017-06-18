package lins.com.qz.ui.chat;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import lins.com.qz.App;
import lins.com.qz.R;
import lins.com.qz.databinding.ConversationBinding;

/**         聊天会话界面
 * Created by LINS on 2017/2/27.
 */

public class ConversationActivity extends FragmentActivity {
    ConversationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.conversation);
        binding.toolbar.ivTopArrow.setImageResource(R.drawable.back);

        Intent intent = getIntent();
        if (intent != null) {
//            Log.e("itent", intent.getData().getQueryParameter("targetId"));//聊天人的id
            if (!TextUtils.isEmpty(intent.getData().getQueryParameter("title"))) {
                binding.toolbar.tvTopTitle.setText(intent.getData().getQueryParameter("title"));
            }
//            Log.e("itent2",intent.getData().getQueryParameter("title"));//聊天标题，由启动当前页面的那里提供
        }

        binding.toolbar.ivTopArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
