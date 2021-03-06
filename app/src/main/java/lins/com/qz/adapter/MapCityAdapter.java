package lins.com.qz.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import lins.com.qz.R;
import lins.com.qz.bean.locationBean.LChatFrds;
import lins.com.qz.bean.locationBean.LMapCity;

/**
 * Created by LINS on 2017/5/14.
 */

public class MapCityAdapter extends RecyclerArrayAdapter<LMapCity>{
    public MapCityAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendHolder(parent);
    }

    class FriendHolder extends BaseViewHolder<LMapCity>{

        private TextView name;
        private ImageView head_icon;
        public FriendHolder(ViewGroup parent) {
            super(parent, R.layout.item_city);
            name = $(R.id.tv_name);
//            head_icon = $(R.id.iv_head_icon);
        }

        @Override
        public void setData(LMapCity data) {
            super.setData(data);
            name.setText(data.getCity());
            //            Glide.with(getContext())
//                    .load(data.getPic())
//                    .placeholder(R.mipmap.ic_launcher)
//                    .centerCrop()
//                    .into(imageView);
//            Glide.with(getContext())
//                    .load(data.getHead_icon())
//                    .placeholder(R.mipmap.ic_launcher)
//                    .into(head_icon);
        }
    }
}
