package com.huagai.eSpace.util;

/**
 * Created by huagai on 14-3-6.
 */


public final class ConstantsUtil {

    public static final String APPTAG = "Controller";

    public static final String FRAMEWORKTAG = "FrameWork";

    public static final String LOG_PATH_RELATIVE = "/eSpaceAppLog";

    // 初始化SDK的广播权限
    public static final String EXTRA_BROADCAST_RECEIVER_PERMISSION =
            "com.huawei.eSpaceMobileApp";

    // 初始化SDK用的协议版本
    public static final int EXTRA_PROTOCOL_VERSION = 3;

    // 默认的时间戳
    public static final String TIMESTAMP = "00000000000000";

    // 正常登录拉服务传递参数
    public static final boolean NORMAL_LOGIN = false;

    // 低内存登录拉服务传递参数
    public static final boolean LOWMEM_LOGIN = true;

    // 安卓版本
    public static final String ANRIODVERSION = "V2.4.0";

    // SDK成功连接MAA后的广播通知
    public static final String ACTION_CONNECT_TO_SERVER =
            "com.huawei.espace.service.connecttoserver";

    // 调用被叫界面可序列化的参数标示
    public static final String EVENTDATA = "EventData";

    // checkVersion后延迟login的时间
    public static final int CHECK_VERSION_SLEEP_TIME = 2000;

}
