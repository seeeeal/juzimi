package com.example.dabutaizha.lines;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.dabutaizha.lines.mvp.BaseApplication;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public class NetStateUtils {

    public static boolean checkNetState(){
        //步骤1：通过Context.getSystemService(Context.CONNECTIVITY_SERVICE)获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        //步骤2：获取ConnectivityManager对象对应的NetworkInfo对象
        //NetworkInfo对象包含网络连接的所有信息
        //步骤3：根据需要取出网络连接信息
        //获取WIFI连接的信息
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Boolean isWifiConn = networkInfo.isConnected();

        //获取移动数据连接的信息
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        Boolean isMobileConn = networkInfo.isConnected();

        if(isWifiConn || isMobileConn) {
            return true;
        } else {
            return false;
        }
    }
}
