package lins.com.qz.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import org.w3c.dom.Text;

import lins.com.qz.R;

/**
 * Created by LINS on 2017/5/14.
 */

public class FriendAdapter extends RecyclerArrayAdapter<String>{
    public FriendAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendHolder(parent);
    }

    class FriendHolder extends BaseViewHolder<String>{

        private TextView textView;
        public FriendHolder(ViewGroup parent) {
            super(parent, R.layout.item_friend);
            textView = $(R.id.tv_fri);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            textView.setText(data);
        }
    }
}
