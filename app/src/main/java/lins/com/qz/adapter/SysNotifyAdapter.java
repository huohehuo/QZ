package lins.com.qz.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import lins.com.qz.R;
import lins.com.qz.bean.SysNotify;

/**
 * Created by LINS on 2017/6/22.
 */

public class SysNotifyAdapter extends RecyclerArrayAdapter<SysNotify>{
    public SysNotifyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SysHolder(parent);
    }


    class SysHolder extends BaseViewHolder<SysNotify>{
        private TextView title,url;
        public SysHolder(ViewGroup parent) {
            super(parent, R.layout.item_sys_notify);

            title = $(R.id.tv_sys_title);
            url = $(R.id.tv_sys_word);
        }

        @Override
        public void setData(SysNotify data) {
            super.setData(data);
            title.setText(data.getTitle());
            url.setText(data.getUrl());

            //            Glide.with(getContext())
//                    .load(data.getPic())
//                    .placeholder(R.mipmap.ic_launcher)
//                    .centerCrop()
//                    .into(imageView);
        }
    }
}
