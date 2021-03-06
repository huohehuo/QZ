package lins.com.qz.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import lins.com.qz.R;
import lins.com.qz.bean.locationBean.LMapCity;

/**
 * Created by LINS on 2017/5/14.
 */

public class DownCityAdapter extends RecyclerArrayAdapter<OfflineMapCity>{
    public DownCityAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendHolder(parent);
    }

    class FriendHolder extends BaseViewHolder<OfflineMapCity>{

        private TextView name;
        private ImageView head_icon;
        public FriendHolder(ViewGroup parent) {
            super(parent, R.layout.item_city);
            name = $(R.id.tv_name);
//            head_icon = $(R.id.iv_head_icon);
        }

        @Override
        public void setData(OfflineMapCity data) {
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
