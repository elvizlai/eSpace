package com.huagai.eSpace.login;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.huagai.eSpace.R;
import com.huagai.eSpace.common.*;
import com.huagai.eSpace.common.LocalBroadcast.LocalBroadcastReceiver;
import com.huagai.eSpace.common.LocalBroadcast.ReceiveData;
import com.huagai.eSpace.sdk.EspaceSDK;
import com.huagai.eSpace.selfInfo.SelfDataHandler;
import com.huagai.eSpace.selfInfo.SelfInfoUtil;
import com.huagai.eSpace.sharedprefer.LoginShare;
import com.huagai.eSpace.util.ConstantsUtil;
import com.huagai.eSpace.util.DeviceUtil;
import com.huawei.common.CustomBroadcastConst;
import com.huawei.common.Resource;
import com.huawei.common.ResponseCodeHandler;
import com.huawei.data.BaseResponseData;
import com.huawei.data.CheckVersionResp;
import com.huawei.data.ExecuteResult;
import com.huawei.data.LoginResp;
import com.huawei.ecs.mtk.log.Logger;
import com.huawei.service.ServiceProxy;
import com.huawei.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huagai on 14-3-6.
 */


public class LoginUtil {
    /**
     * 提供给ISV第三方的消息
     */
    public static final int LOGIN_SUCCESS = 0;

    public static final int LOGIN_FAILED = 1;

    public static final int LOGOUT_SUCCESS = 2;

    public static final int LOGOUT_FAILED = 3;

    /**
     * 跳转到MainActivity
     */
    private static final int TO_MAINACTIVITY = 1;

    /**
     * 回登录界面初始状态
     */
    private static final int INIT_LOGINACTIVITY = 2;
    /**
     * 连接服务器错误
     */
    private static final int CONNECT_SERVER_ERRROR = 3;

    /**
     * 网络连接失败
     */
    private static final int CONNECT_FAILED = 1;

    /**
     * 帐号被踢
     */
    private static final int BE_KICKED_OUT = -6;

    /**
     * 已登出
     */
    private static final int TO_LOGINACTIVITY = 0;

    /**
     * 不允许3G网络登录
     */
    private static final int LOGIN_3G_DISABLED = 1000;

    /**
     * 部分服务器限制登录
     */
    private static final int LOGIN_LIMITED = 1001;

    /**
     * 已在其他终端登录
     */
    private static final int LOGIN_OTHER_TERMINAL = 1002;

    private static final int FORCE_EXIT_TIME = 20000;

    private static LoginUtil ins = new LoginUtil();
    private final Runnable forceExitRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(LoginUtil.class.getName(), "forceExitRunnable executed.");
            unRegVoipOrExit();
        }
    };
    /**
     * 处理相关请求错误信息
     */
    private final Handler reqErrorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            doLoginFailedOperation();
            String prompt = "";
            switch (msg.what) {
                case Resource.REQUEST_FAIL:
                    prompt = context.getString(R.string.request_failed);
                    sendErrorMsg(prompt);
                    break;
                case Resource.REQUEST_TIMEOUT:
                    prompt = context.getString(R.string.etimeout);
                    sendErrorMsg(prompt);
                    break;
                case Resource.REQUEST_ERROR:
                    prompt = context.getString(R.string.request_error);
                    sendErrorMsg(prompt);
                    break;
                default:
                    break;
            }
        }
    };
    private ServiceProxy serviceProxy;
    /**
     * 注册的登入广播名
     */
    private String[] actionNamesLogin;
    /**
     * 注册的登出广播名
     */
    private String[] actionNamesLogout;
    private boolean isLogin;
    private Context context;
    /**
     * 登录人的账号 *
     */
    private String userName;
    /**
     * 登录人的密码 *
     */
    private String password;
    /**
     * ISV登录消息处理Handler
     */
    private Handler loginHandler;
    /**
     * 消息处理Handler
     */
    private final Handler serviceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_MAINACTIVITY:

                    unRegisterLoginService();
                    registerBroadcast(actionNamesLogout);

                    if (loginHandler == null) {
                        return;
                    }

                    isLogin = true;
                    loginHandler.sendEmptyMessage(LOGIN_SUCCESS);
                    break;

                case INIT_LOGINACTIVITY:

                    doLoginFailedOperation();
                    if (msg.obj instanceof List<?>) {
                        List<?> respInfoList = (List<?>) msg.obj;
                        ResponseCodeHandler.ResponseCode rspCode = null;
                        try {
                            rspCode = (ResponseCodeHandler.ResponseCode) respInfoList.get(0);
                        } catch (Exception e) {
                            Logger.debug(ConstantsUtil.APPTAG, e.toString());
                        }
                        String desc = (String) respInfoList.get(1);
                        sendErrorMsg(ResErrorCodeForStringHandler.getIns()
                                .handleError(rspCode, desc));
                    }
                    break;

                case LOGIN_OTHER_TERMINAL:
                    onLoginOtherTerminal(msg);
                    break;

                case CONNECT_SERVER_ERRROR:
                    handleConnectError(CONNECT_FAILED);
                    break;

                case BE_KICKED_OUT:
                    doLoginFailedOperation();
                    sendErrorMsg(context.getString(R.string.account_login_otherplace));
                    break;

                case LOGIN_3G_DISABLED:
                    doLoginFailedOperation();
                    sendErrorMsg(context.getString(R.string.login_3g_disabled));
                    break;

                case LOGIN_LIMITED:
                    doLoginFailedOperation();
                    sendErrorMsg(context
                            .getString(R.string.uc_login_right_limit_android));
                    break;

                default:
                    break;
            }
        }
    };
    /**
     * ISV登出消息处理Handler
     */
    private Handler logoutHandler;
    private final Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TO_LOGINACTIVITY) {
                unRegisterLogoutService();

                isLogin = false;

                if (logoutHandler != null) {
                    logoutHandler.sendEmptyMessage(LOGOUT_SUCCESS);
                }
            }
        }
    };
    private LocalBroadcastReceiver receiver = new LocalBroadcastReceiver() {
        @Override
        public void onReceive(ReceiveData d) {
            onBroadcastReceive(d);
        }
    };


    private LoginUtil() {
        actionNamesLogin = new String[]{
                CustomBroadcastConst.ACTION_CONNECT_TO_SERVER,
                CustomBroadcastConst.ACTION_CHECKVERSION_RESPONSE,
                CustomBroadcastConst.ACTION_LOGIN_RESPONSE,
                CustomBroadcastConst.BACK_TO_LOGIN_VIEW,
                CustomBroadcastConst.ACTION_LOGINOUT_SUCCESS};

        actionNamesLogout = new String[]{
                CustomBroadcastConst.BACK_TO_LOGIN_VIEW,
                CustomBroadcastConst.ACTION_LOGINOUT_SUCCESS};
    }

    public static LoginUtil getIns() {

        if (null == ins) {
            ins = new LoginUtil();
        }

        return ins;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void onLogin(Context context, Handler loginHandler, String userName, String password) {
        this.context = context;
        this.loginHandler = loginHandler;

        if (isLogin()) {
            sendErrorMsg(context.getString(R.string.has_logined_error));
            return;
        }

        registerBroadcast(actionNamesLogin);

        this.userName = userName;
        this.password = password;

        EspaceSDK.getIns().startImService();
    }

    /**
     * 登陆
     *
     * @param userName 登陆用户名
     * @param password 登陆密码
     */
    private synchronized void doLogin(String userName, String password) {
        try {

            if (serviceProxy == null) {
                serviceProxy = EspaceSDK.getIns().getServiceProxy();
            }

            // 获取设备序列号
            String deviceId = DeviceUtil.getIMEI();

            serviceProxy.login(userName,
                    password, ConstantsUtil.TIMESTAMP, deviceId,
                    ConstantsUtil.TIMESTAMP);

            SelfDataHandler.getIns().getSelfData().setAccount(userName);
            SelfDataHandler.getIns().getSelfData().setPassword(password);

            Log.d("login", "SDK send login message to MAA success");

        } catch (Exception e) {
            Log.d("login", "login failed, err is " + e.toString());
            return;
        }
    }

    public void onLogout(Handler logoutHandler) {
        this.logoutHandler = logoutHandler;

        // 清除在线状态
        SelfInfoUtil.getIns().setToLogoutStatus();

        if (serviceProxy == null) {
            serviceProxy = EspaceSDK.getIns().getServiceProxy();
        }

        if (serviceProxy == null) {
            return;
        }

        if ("".equals(SelfDataHandler.getIns().getSelfData().getAccount())) {
            return;
        }
        // 注销SDK
        ExecuteResult result = serviceProxy.Logout(SelfDataHandler.getIns().getSelfData().getAccount());

        Log.d(LoginUtil.class.getName(), "logout executed, result:" + result.getResult());

        EventHandler.getIns().postDelayed(forceExitRunnable, FORCE_EXIT_TIME);
    }

    /**
     * 处理连服务器响应
     *
     * @param connectStatus 连接状态
     */
    private void handleConnectServerResp(boolean connectStatus) {
        if (connectStatus) {
            sendCheckVersionReq();
        } else {
            serviceHandler.sendEmptyMessage(CONNECT_SERVER_ERRROR);
        }
    }

    /**
     * 处理版本检测响应
     *
     * @param result
     * @param data
     */
    private void handleCheckVersionResp(int result, BaseResponseData data) {
        if (result == Resource.REQUEST_OK) {
            handleCheckVersionSuc(data);
        } else {
            reqErrorHandler.sendEmptyMessage(result);
        }
    }

    /**
     * 服务器连接成功后发检测版本请求
     *
     * @author Daihui Zhou
     */
    private boolean sendCheckVersionReq() {

        if (EspaceSDK.getIns().getServiceProxy() == null) {
            return false;
        }

        String lan = DeviceUtil.getLocalLanguage();
        // 服务器连接成功后发检测版本请求
        ExecuteResult executeResult = EspaceSDK.getIns().getServiceProxy()
                .checkVersion("V2.4.0", lan, userName, "Android", "", true, true, true, true);

        return executeResult.getResult();
    }

    /**
     * 检测版本成功后续操作
     *
     * @param data
     */
    private void handleCheckVersionSuc(BaseResponseData data) {
        if (data instanceof CheckVersionResp) {
            //CheckVersionResp resp = (CheckVersionResp) data;
            // 当前使用的网络，本地判断
            boolean is3G = !DeviceUtil.isWifiConnect();
            // 是否允许3G登录，Check Version 返回
            boolean is3GLoginEnable = SelfDataHandler.getIns().getSelfData()
                    .isLogin3GFlag();
            String serverType = SelfDataHandler.getIns().getSelfData()
                    .getImServerType();
            boolean isHuaweiUC = LoginShare.HUAWEI_UC
                    .equalsIgnoreCase(serverType);
            boolean isUC_2_0 = LoginShare.UC_2_0.equalsIgnoreCase(serverType);

            Log.d("handleCheckVersionSuc", "serverType:" + serverType);

            // uc1.0和uc1.1限制登录
            if (isHuaweiUC || isUC_2_0) {
                if (is3G && !is3GLoginEnable) {
                    serviceHandler.sendEmptyMessage(LOGIN_3G_DISABLED);
                } else {
                    doLogin(userName, password);
                }
            } else {
                serviceHandler.sendEmptyMessage(LOGIN_LIMITED);
            }
        }
    }

    /**
     * 处理登录响应
     *
     * @param result
     * @param data
     */
    private void handleLoginResp(int result, BaseResponseData data) {
        if (result == Resource.REQUEST_OK) {
            handleLoginRespSuc(serviceHandler, (LoginResp) data);
        }
    }

    private void handleBackToLoginActivity(final ReceiveData resp) {
        if (CustomBroadcastConst.ACTION_LOGINOUT_SUCCESS.equals(resp.action)) {
            handleLogoutResp();
        } else if (CustomBroadcastConst.BACK_TO_LOGIN_VIEW.equals(resp.action)) {
            if (!isLogin()) {
                return;
            }
            // 返回到登陆界面
            handleBackToLogin(resp.result);
        }
    }

    /**
     * 处理超时或网络失败
     *
     * @param type 类型 0:超时 1：网络失败
     */
    private void handleConnectError(int type) {
        doLoginFailedOperation();
        String msg = "";
        if (0 == type) {
            msg = context.getString(R.string.etimeout);
        } else {
            msg = context.getString(R.string.connect_error);
        }
        sendErrorMsg(msg);
    }

    /**
     * 登录成功后续操作
     *
     * @param handler Handler对象
     * @param resp    登录响应对象
     */
    private void handleLoginRespSuc(Handler handler, LoginResp resp) {

        ResponseCodeHandler.ResponseCode rspcode = resp.getStatus();
        Message msg = null;
        List<Object> rspInfoList = new ArrayList<Object>();

        if (ResponseCodeHandler.ResponseCode.SESSION_TIMEOUT.equals(rspcode)) {
            msg = new Message();
            msg.what = INIT_LOGINACTIVITY;
            rspInfoList.add(rspcode);
            rspInfoList.add(ApplictionHandle.getContext().getString(R.string.module_error_9));
            msg.obj = rspInfoList;
            handler.sendMessage(msg);
        } else if (ResponseCodeHandler.ResponseCode.REQUEST_SUCCESS.equals(rspcode)) {
            //保存登录响应相关参数 （已在框架中拦截，无需再做）
            if (resp.getOtherLoginType() == LoginResp.OtherLoginType.NULL) {
                continueLogin(handler);
            } else {
                // 弹框提示已在其他终端登录
                msg = new Message();
                msg.what = LOGIN_OTHER_TERMINAL;
                msg.obj = resp;
                handler.sendMessage(msg);
            }
        } else {
            msg = new Message();
            msg.what = INIT_LOGINACTIVITY;
            rspInfoList.add(rspcode);
            rspInfoList.add(resp.getDesc());
            msg.obj = rspInfoList;
            handler.sendMessage(msg);
        }
    }

    private void handleBackToLogin(int result) {
        // 清除在线状态
        SelfInfoUtil.getIns().setToLogoutStatus();
        unRegVoipOrExit();
    }

    /**
     * 处理注销响应
     */
    private void handleLogoutResp() {
        EventHandler.getIns().removeCallbacks(forceExitRunnable);
        unRegVoipOrExit();
    }

    private void continueLogin(Handler handler) {
        //跳转到主界面
        handler.sendEmptyMessage(TO_MAINACTIVITY);

        //设置登录状态，根据上次登录保存的登录状态设置在线状态
        SelfInfoUtil.getIns().setToLoginStatus();
    }

    private void onLoginOtherTerminal(Message msg) {
        if (msg.obj instanceof LoginResp) {
            LoginResp resp = (LoginResp) (msg.obj);

            String desc = null;
            LoginResp.OtherLoginType loginType = resp.getOtherLoginType();

            // 是否根据服务器返回错误码给相应提示语
            switch (loginType) {
                case PC:
                    desc = context.getString(R.string.login_on_pc_desc);
                    break;
                case MOBILE:
                    desc = context.getString(R.string.login_on_mobile_desc);
                    break;
                case WEB:
                    desc = context.getString(R.string.login_on_web_desc);
                    break;
                case PAD:
                    desc = context.getString(R.string.login_on_pad_desc);
                    break;
                case IPPHONE:
                    desc = context.getString(R.string.login_on_ipphone_desc);
                    break;
                default:
                    break;
            }

            if (desc == null) {
                return;
            }

            sendErrorMsg(desc);
        }
    }

    private void sendErrorMsg(String msg) {
        if (loginHandler == null) {
            return;
        }

        Message message = new Message();
        message.what = LOGIN_FAILED;
        message.obj = msg;
        loginHandler.sendMessage(message);
        unRegisterBroadcast(actionNamesLogin);
    }

    private void doLoginFailedOperation() {
        SelfInfoUtil.getIns().setToLogoutStatus();
    }

    private void backToLoginView() {
        Message msg = new Message();
        msg.what = TO_LOGINACTIVITY;
        uiHandler.sendMessage(msg);
    }

    private void unRegVoipOrExit() {
        backToLoginView();

        EspaceSDK.getIns().stopSDKService();

        // 清空和初始化缓存的数据
        clear();
    }

    private void clear() {
        //清除线程管理器
        ThreadManager.getInstance().clearThreadResource();

//    	关闭数据库
//    	DbVindicate.getIns().closeDb();

        SelfInfoUtil.getIns().clear();
    }

    private void unRegisterLoginService() {
        unRegisterBroadcast(actionNamesLogin);
    }

    private void unRegisterLogoutService() {
        unRegisterBroadcast(actionNamesLogout);
    }

    protected final boolean registerBroadcast(String[] broadcastNames) {
        return LocalBroadcast.getIns().registerBroadcast(receiver,
                broadcastNames);
    }

    protected final boolean unRegisterBroadcast(String[] broadcastNames) {
        return LocalBroadcast.getIns().unRegisterBroadcast(receiver,
                broadcastNames);
    }

    private void onBroadcastReceive(ReceiveData d) {
        String action = d.action;
        BaseResponseData data = d.data;
        int result = d.result;
        if (StringUtil.isStringEmpty(action)) {
            return;
        }

        Log.i(LoginUtil.class.getName(), "Broadcast : " + action
                + " received result : " + result);

        if (CustomBroadcastConst.ACTION_CONNECT_TO_SERVER.equals(action)) {
            boolean connectStatus = (result == Resource.REQUEST_OK);
            handleConnectServerResp(connectStatus);
        }
        if (CustomBroadcastConst.ACTION_CHECKVERSION_RESPONSE.equals(action)) {
            handleCheckVersionResp(result, data);
        }
        if (CustomBroadcastConst.ACTION_LOGIN_RESPONSE.equals(action)) {
            handleLoginResp(result, data);
        }
        if (CustomBroadcastConst.ACTION_LOGINOUT_SUCCESS.equals(d.action)
                || CustomBroadcastConst.BACK_TO_LOGIN_VIEW.equals(d.action)) {
            handleBackToLoginActivity(d);
        }
    }

}

