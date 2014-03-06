package com.huagai.eSpace.sdk;

import android.content.*;
import android.os.IBinder;
import android.util.Log;
import com.huagai.eSpace.common.ApplictionHandle;
import com.huagai.eSpace.common.LocalBroadcast;
import com.huagai.eSpace.common.ThreadManager;
import com.huagai.eSpace.data.proc.DataProc;
import com.huagai.eSpace.selfInfo.SelfData;
import com.huagai.eSpace.selfInfo.SelfDataHandler;
import com.huagai.eSpace.util.ConstantsUtil;
import com.huagai.eSpace.util.DeviceUtil;
import com.huawei.common.Resource;
import com.huawei.ecs.mtk.log.Logger;
import com.huawei.module.ConnectInfo;
import com.huawei.module.SDKConfigParam;
import com.huawei.service.ServiceProxy;
import com.huawei.service.eSpaceService;
import com.huawei.utils.SVNUtil;
import com.huawei.utils.StringUtil;

import java.util.Set;


/**
 * 控件与SDK通信的模块
 * <p/>
 * 功能描述： 获取代理类serviceProxy 启动SDK服务，关闭SDK服务
 */
public final class EspaceSDK {

    private static EspaceSDK instance = new EspaceSDK();

    private static ServiceProxy serviceProxy;

    // 服务器启动flg true：启动 false：未启动
    private static boolean mServiceStarted;

    private static GlobalBoradCastReceiver receiver;

    /**
     * 全局的接受广播类
     */
    private static class GlobalBoradCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            ThreadManager.getInstance().addToBroadcastThread(new Runnable() {
                @Override
                public void run() {
                    LocalBroadcast.getIns().onBroadcastReceive(intent);
                }
            });
        }
    }

    public synchronized static EspaceSDK getIns() {
        // 当前没有初始化 或者 服务被停止的时候都去做初始化操作
        if (instance == null) {
            instance = new EspaceSDK();
        }

        return instance;
    }

    private EspaceSDK() {

    }

    public ServiceProxy getServiceProxy() {
        return serviceProxy;
    }

    protected static void configServiceParam(ServiceProxy mService) {
        SDKConfigParam param = new SDKConfigParam();
        param.addAbility(SDKConfigParam.Ability.CODE_OPOUS);
        param.addAbility(SDKConfigParam.Ability.VOIP_2833);
        param.addAbility(SDKConfigParam.Ability.VOIP_VIDEO);
        param.setBooadcastPermission("com.huawei.eSpaceMobileApp");
        param.setClientType(SDKConfigParam.ClientType.UC_MOBILE);
        param.setMegTypeVersion((short) 3);
        param.setHttpLogPath(DeviceUtil.getSdcardPath());

        mService.setSDKConfigparam(param);
    }

    public synchronized void startImService() {
        // 如果Im底层服务器已经开启则直接连接服务器
        if (!mServiceStarted) {
            Context context = ApplictionHandle.getContext();

            // 注册广播
            registerGlobalBroadcastReceiver(context);

            Intent intent = new Intent(context, eSpaceService.class);
            intent.putExtra(Resource.EXTRA_CHECK_AUTO_LOGIN, false);
            intent.putExtra(Resource.EXTRA_BROADCAST_RECEIVER_PERMISSION,
                    "com.huawei.eSpaceMobileApp");
            intent.putExtra(Resource.EXTRA_PROTOCOL_VERSION, 3);
            intent.putExtra(Resource.EXTRA_CHAT_NOTIFICATION, false);
            intent.putExtra(Resource.HTTP_LOG_PATH, DeviceUtil.getSdcardPath());

            context.startService(intent);
            context.bindService(intent, IMSERVICECONN, Context.BIND_AUTO_CREATE);

            Log.i(EspaceSDK.class.getName(), "startService");

            mServiceStarted = true;
        } else {
            connectIMServer();
        }
    }

    /**
     * 内部类建立与SDK服务的连接
     * *
     */
    private final ServiceConnection IMSERVICECONN = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mServiceStarted = true;

            if (!(service instanceof eSpaceService.ServiceBinder)) {
                return;
            }
            // 获取代理服务
            serviceProxy = ((eSpaceService.ServiceBinder) service).getService();

            configServiceParam(serviceProxy);

            /** 打印log Start */
            // data/data/com.huawei.esdk.uc.controller.files加下面的路径就是log的路径
            String path = "/controller_UI/voipLog";

            boolean status = true;
            serviceProxy.setLogSwitch(path, status);
            /** 打印log End */

            connectIMServer();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceStarted = false;
            serviceProxy = null;
            instance = null;
        }
    };

    /**
     * 停止SDK服务
     *
     * @param ：context 上下文获取操作句柄
     *                 *
     */
    public synchronized void stopSDKService() {

        if (mServiceStarted) {
            Log.d(ConstantsUtil.FRAMEWORKTAG,
                    "stop ImService because there's no active connections");

            if (serviceProxy != null) {
                serviceProxy.stopService();
                Context context = ApplictionHandle.getContext();
                unRegisterGlobalBroadcastReceiver(context);
                context.unbindService(IMSERVICECONN);
                serviceProxy = null;
                instance = null;
            }

            mServiceStarted = false;
        }
    }

    /**
     * 连服务器
     */
    private synchronized void connectIMServer() {
        // 连接服务器操作
        if (null != serviceProxy) {

            SelfData data = SelfDataHandler.getIns().getSelfData();
            ConnectInfo connectInfo = new ConnectInfo();
            connectInfo.setServerAddress(data.getServerUrl());
            connectInfo.setServerProt(StringUtil.stringToInt(data
                    .getServerPort()));
            connectInfo.setSvnServerAddress(data.getSvnIp());
            connectInfo.setSvnServerPort(StringUtil.stringToInt(
                    data.getSvnPort(), SVNUtil.SVN_PORT));
            connectInfo.setSVNEnable(data.isSvnFlag());
            connectInfo.setSvnAccount(data.getSvnAccount());
            connectInfo.setSvnPassword(data.getSvnPassword());

            boolean connectOK = serviceProxy.connectToServer(connectInfo);

            if (!connectOK) {
                Logger.debug("CommunicateWithSDKTAG",
                        "SDK connect MAA failed by serviceProxy in CommunicateSDK");
            }
        }
    }

    /**
     * 注册广播监听
     */
    public static void registerGlobalBroadcastReceiver(Context context) {
        LocalBroadcast.getIns().registerBroadcastProc(
                DataProc.getIns().getProc());
        receiver = new GlobalBoradCastReceiver();
        IntentFilter filter = new IntentFilter();

        Set<String> actions = LocalBroadcast.getIns().getAllBroadcast();

        for (String action : actions) {
            filter.addAction(action);
        }

        context.registerReceiver(receiver, filter);

        Logger.debug("CommunicateWithSDKTAG",
                "registerGlobalBroadcastReceiver() finished!");

    }

    public static void unRegisterGlobalBroadcastReceiver(Context context) {
        context.unregisterReceiver(receiver);
        receiver = null;
    }

}