package lins.com.qz.ui.map;

import android.databinding.DataBindingUtil;

import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;

import lins.com.qz.App;
import lins.com.qz.R;
import lins.com.qz.bean.locationBean.LMapCity;
import lins.com.qz.databinding.ActivityMapSettingBinding;
import lins.com.qz.manager.MapCityManager;
import lins.com.qz.ui.base.BaseActivity;

public class MapSettingActivity extends BaseActivity implements OfflineMapManager.OfflineMapDownloadListener{
    ActivityMapSettingBinding binding;
    OfflineMapManager offlineMapManager;
    MapCityManager manager;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_map_setting);
        setToolbarBack(binding.toolbar.ivTopArrow);

        offlineMapManager=new OfflineMapManager(this,this);
        manager = new MapCityManager();

    }

    @Override
    protected void initEvent() {
        closeActivity(binding.toolbar.ivTopArrow);
        startActivityWith(CityActivity.class,binding.tvMaplist);
        startActivityWith(DownCityActivity.class,binding.tvDowned);


    }

    @Override
    protected void getData() {
        initMapListener();
    }


    protected void initMapListener() {
        if (manager.queryAll().size()==0){
            for (OfflineMapCity city:offlineMapManager.getOfflineMapCityList()) {
                manager.insert(new LMapCity(null,city.getCity(),city.getCode(),city.getJianpin(),city.getPinyin()));
            }
        }

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
