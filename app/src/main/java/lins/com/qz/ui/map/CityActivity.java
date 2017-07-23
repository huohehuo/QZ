package lins.com.qz.ui.map;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.amap.api.maps.AMapException;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import lins.com.qz.App;
import lins.com.qz.R;
import lins.com.qz.adapter.MapCityAdapter;
import lins.com.qz.databinding.ActivityCityBinding;
import lins.com.qz.manager.MapCityManager;
import lins.com.qz.ui.base.BaseActivity;

public class CityActivity extends BaseActivity implements OfflineMapManager.OfflineMapDownloadListener{
    ActivityCityBinding binding;
    MapCityAdapter adapter;
    MapCityManager manager;
    OfflineMapManager offlineMapManager;


    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_city);

        binding.ryCitylist.setAdapter(adapter = new MapCityAdapter(this));
        binding.ryCitylist.setLayoutManager(new LinearLayoutManager(this));
        offlineMapManager=new OfflineMapManager(this,this);
        manager = new MapCityManager();

    }

    @Override
    protected void initEvent() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                downCity(adapter.getAllData().get(position).getCode());
            }
        });
    }

    @Override
    protected void getData() {
        if (manager.queryAll().size()!=0){
            adapter.addAll(manager.queryAll());
        }
        App.e(offlineMapManager.getDownloadOfflineMapCityList().get(0).getCity());

    }


    private void downCity(String code){
        //按照citycode下载
        //按照cityname下载
        try {
            offlineMapManager.downloadByCityCode(code);
            Log.e("down","ok");
//            amapManager.downloadByCityName("南宁市");
        } catch (AMapException e) {
            e.printStackTrace();
            Log.e("down","no--ok");

        }

        App.e(offlineMapManager.getDownloadOfflineMapCityList().get(0).getCity());

    }

    private void sss(){
        //通过updateOfflineCityByName方法判断离线地图数据是否存在更新
//        amapManager.updateOfflineCityByName(city);
//        删除某一城市的离线地图包
//        amapManager.remove(city);
    }

    //下载地图的更新回调------------------------------------------
    @Override
    public void onDownload(int i, int i1, String s) {
        //下载状态回调，在调用downloadByCityName 等下载方法的时候会启动
        App.e("down",s+"进度："+i1);
    }

    @Override
    public void onCheckUpdate(boolean b, String s) {
//当调用updateOfflineMapCity 等检查更新函数的时候会被调用
    }

    @Override
    public void onRemove(boolean b, String s, String s1) {
//当调用OfflineMapManager.remove(String)方法时，如果有设置监听，会回调此方法
//                当删除省份时，该方法会被调用多次，返回省份内城市删除情况。
    }

    //-----------------END
}
