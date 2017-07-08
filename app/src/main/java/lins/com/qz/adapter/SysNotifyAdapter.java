package lins.com.qz.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        if (viewType==1){//图文布局
            return new SysHolder(parent);
        }else{//图片布局
            return new SysHolderForPic(parent);
        }
    }

    @Override
    public int getViewType(int position) {
        return Integer.valueOf(getAllData().get(position).getType());
    }

    class SysHolder extends BaseViewHolder<SysNotify>{
        private TextView title;
        private ImageView txt_bg;
        public SysHolder(ViewGroup parent) {
            super(parent, R.layout.item_sys_notify);
            title = $(R.id.tv_sys_title);
            txt_bg = $(R.id.iv_sys_txt_bg);
        }

        @Override
        public void setData(SysNotify data) {
            super.setData(data);
            title.setText(data.getTitle());
            Glide.with(getContext())
                    .load(data.getBgurl())
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(txt_bg);
        }
    }

    class SysHolderForPic extends BaseViewHolder<SysNotify>{
        private ImageView txt_bg;
        public SysHolderForPic(ViewGroup parent) {
            super(parent, R.layout.item_sys_notify_for_only_pic);
            txt_bg = $(R.id.iv_sys_pic);
        }

        @Override
        public void setData(SysNotify data) {
            super.setData(data);
            Glide.with(getContext())
                    .load(data.getBgurl())
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(txt_bg);
        }
    }
}
