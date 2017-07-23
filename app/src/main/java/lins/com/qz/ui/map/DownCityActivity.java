package lins.com.qz.ui.map;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.amap.api.maps.offlinemap.OfflineMapManager;

import lins.com.qz.App;
import lins.com.qz.R;
import lins.com.qz.adapter.DownCityAdapter;
import lins.com.qz.adapter.MapCityAdapter;
import lins.com.qz.databinding.ActivityDownCityBinding;
import lins.com.qz.manager.MapCityManager;
import lins.com.qz.ui.base.BaseActivity;

public class DownCityActivity extends BaseActivity implements OfflineMapManager.OfflineMapDownloadListener{
    ActivityDownCityBinding binding;
    DownCityAdapter adapter;
    MapCityManager manager;
    OfflineMapManager offlineMapManager;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_down_city);

        binding.ryCitylist.setAdapter(adapter = new DownCityAdapter(this));
        binding.ryCitylist.setLayoutManager(new LinearLayoutManager(this));

        offlineMapManager=new OfflineMapManager(this,this);
        manager = new MapCityManager();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void getData() {
        adapter.addAll(offlineMapManager.getDownloadOfflineMapCityList());
        App.e(offlineMapManager.getDownloadOfflineMapCityList().get(0).getCity());

    }

    @Override
    public void onDownload(int i, int i1, String s) {

    }

    @Override
    public void onCheckUpdate(boolean b, String s) {

    }

    @Override
    public void onRemove(boolean b, String s, String s1) {

    }
}
