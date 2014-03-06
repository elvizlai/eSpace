package com.huagai.eSpace.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.huagai.eSpace.common.ApplictionHandle;

import java.io.File;
import java.util.Locale;


public class DeviceUtil {

    /**
     * 获取SD卡路径
     *
     * @return：String SD卡路径
     * *
     */
    public static String getSdcardPath() {
        File SDFile = android.os.Environment.getExternalStorageDirectory();

        if (SDFile == null) {
            return null;
        }

        return SDFile.getPath();
    }

    public static String getLocalLanguage() {

        String LAN_EN = "EN";
        String LAN_ZH = "ZH";
        String LAN_PT = "PT";
        String LAN_TR = "TR";

        /**
         * 国家码 -- 繁体中文(不存配置文件)
         */
        String CODE_COUNTRY_TW = "TW";

        /**
         * 国家码 -- 中文简体(不存配置文件)
         */
        String CODE_COUNTRY_CN = "CN";

        String currentLan = "";
        String lanCode = Locale.getDefault().getLanguage()
                .toUpperCase(Locale.getDefault());
        String countryCode = Locale.getDefault().getCountry()
                .toUpperCase(Locale.getDefault());
        if (LAN_ZH.equals(lanCode) && CODE_COUNTRY_CN.equals(countryCode)) {
            currentLan = LAN_ZH;
        } else if (LAN_ZH.equals(lanCode) && CODE_COUNTRY_TW.equals(countryCode)) {
            currentLan = LAN_TR;
        } else if (LAN_PT.equals(lanCode)) {
            currentLan = LAN_PT;
        } else {
            currentLan = LAN_EN;
        }
        return currentLan;
    }

    public static boolean isWifiConnect() {
        ConnectivityManager cm = (ConnectivityManager) ApplictionHandle.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (null != wifi && wifi.isConnected()) {
            return true;
        }

        return false;
    }

    /**
     * 获取设备序列号
     *
     * @param：context 上下文获取操作句柄
     * return：String 设备序列号
     * *
     */
    public static String getIMEI() {
        Context context = ApplictionHandle.getContext();

        String strIMEI = null;
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        strIMEI = telephonyManager.getDeviceId();
        return strIMEI;
    }
}
