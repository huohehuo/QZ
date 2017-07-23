package lins.com.qz.ui.fragment;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.offlinemap.OfflineMapManager;

import lins.com.qz.App;
import lins.com.qz.Config;
import lins.com.qz.R;


/**
 * Created by LINS on 2017/2/22.
 */

public class MapFragment extends Fragment {
    public static final String TAG = "three_fragment";
    private AMap aMap;
    private MapView mapView;
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        if (rootView == null){
//            rootView =inflater.inflate(R.layout.fg_map,null);
//            mapView = (MapView) rootView.findViewById(R.id.mp_main);
//            mapView.onCreate(savedInstanceState);
//            Log.e("map","onCreateView");
//            aMap = mapView.getMap();
//
//            aMap.setTrafficEnabled(true);// 显示实时交通状况
//            //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
//            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
//            getData();
//            initEven();
//            return rootView;
//        }


        rootView =inflater.inflate(R.layout.fg_map,null);
        mapView = (MapView) rootView.findViewById(R.id.mp_main);
        mapView.onCreate(savedInstanceState);
        Log.e("map","onCreateView");
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
//            aMap.setCustomMapStylePath("file:///android_asset/tuyamap");
            aMap.setCustomMapStylePath(Config.PATH_MAP_STYLE+"tuyamap");
            aMap.setMapCustomEnable(true);
        }
//        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
//        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
//                App.e(location.toString());
            }
        });


        Log.e("plan","onCreateView");
        return rootView;



    }

    //绑定事件
    private void initEven(){

    }
    //获取数据
    protected void getData() {
        //构造OfflineMapManager对象
        OfflineMapManager amapManager = new OfflineMapManager(getActivity(),
                new OfflineMapManager.OfflineMapDownloadListener() {
            @Override
            public void onDownload(int i, int i1, String s) {
                //下载状态回调，在调用downloadByCityName 等下载方法的时候会启动
                App.e(s+"进度："+i1);
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
        });
        //按照citycode下载
        //按照cityname下载
        try {
                amapManager.downloadByCityCode("0771");
            Log.e("down","ok");
//            amapManager.downloadByCityName("南宁市");
        } catch (AMapException e) {
            e.printStackTrace();
            Log.e("down","no--ok");

        }

        App.e(amapManager.getOfflineMapCityList().toString());


//        User user = BmobUser.getCurrentUser(User.class);
//        BmobQuery<PlanBean> query = new BmobQuery<>();
//        query.addWhereEqualTo("author",user);// 查询当前用户的所有帖子
//        query.order("-updatedAt");
//        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
//        query.findObjects(new FindListener<PlanBean>() {
//            @Override
//            public void done(List<PlanBean> list, BmobException e) {
//                if (e==null){
//
//                }else{
//                    Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        //删除帖子
//        PlanBean p = new PlanBean();
//        p.remove("author");
//        p.update("ESIt3334", new UpdateListener() {
//
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    Log.i("bmob","成功");
//                }else{
//                    Log.i("bmob","失败："+e.getMessage());
//                }
//            }
//        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("plan","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();        Log.e("plan","onStart");

    }

    @Override
    public void onResume() {
        super.onResume();        Log.e("plan","onResume");

    }

    @Override
    public void onPause() {
        super.onPause();Log.e("plan","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();        Log.e("plan","onStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();        Log.e("plan","onDestroy");
        mapView.onDestroy();

    }

    @Override
    public void onDetach() {
        super.onDetach();        Log.e("plan","onDetach");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();        Log.e("plan","onDestroyView");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);Log.e("plan","onAttach");
    }
}
