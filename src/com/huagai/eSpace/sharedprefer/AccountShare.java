package com.huagai.eSpace.sharedprefer;

/**
 * Created by huagai on 14-3-6.
 */

import android.content.Context;
import android.content.SharedPreferences;
import com.huagai.eSpace.common.ApplictionHandle;
import com.huagai.eSpace.util.ConstantsUtil;
import com.huawei.common.PersonalContact;
import com.huawei.ecs.mtk.log.Logger;
import com.huawei.utils.StringUtil;

import java.io.File;

/**
 * 账户信息
 */
public class AccountShare {
    /**
     * 登录账户配置文件名
     */
    private static final String ACCOUNT_CFG = "eSpaceCfg";
    /**
     * 登录账户配置
     */
    private static final String LOGIN_USERNAME = "username";
    private static final String LOGIN_PSW = "loginpsw";

    private static final String LOGIN_USER = "loginuser";

    private static final String AUTOLOGIN = "autologin";
    private static final String LOGIN_LAN = "language";
    private static final String SERVERURL = "serviceurl";
    private static final String SERVER_PORT = "serviceport";
    private static final String VERSON = "version";
    private static final String NOTIFICATION_RING = "notification_ring";
    private static final String PHONE_INCOMING_RING = "phone_incoming_ring";
    private static final String SAVEPASSWORD = "savepassword";
    private static final String LOG_FEEDBACK = "log_feedback";
    private static final String SELFNAME = "user_name";
    private static final String SELFNATIVENAME = "native_name";
    private static final String SVN_IP = "svnip";
    private static final String SVN_PORT = "svnport";
    private static final String SVN_ACCOUNT = "svnaccount";
    private static final String SVN_PASSWORD = "svnpassword";
    private static final String SVN_FLAG = "svnflag";
    private static final String CRASHEXIT = "crashexit";
    private static final String GUIDEFLAG = "guideflag";
    private static final String SCANEDACCOUNTS = "scan_guide_accounts";
    private static final String LOGINSTATUS = "login_status";
    /**
     * 自动接听等待时间
     */
    private static final String AUTO_ANSWER_TIME = "autoAnswerTime";
    /**
     * 帮助url地址
     */
    private static final String HELP_URL = "helpUrl";
    /**
     * 登录账户配置文件
     */
    private static SharedPreferences accountShare = null;
    private static AccountShare ins;

    public static AccountShare getIns() {
        if (null == ins) {
            ins = new AccountShare();
        }
        return ins;
    }

    public void clearAccountShare() {
        getAccountShare().edit().clear().commit();
    }

    /**
     * 账户相关配置信息
     *
     * @return
     */
    public SharedPreferences getAccountShare() {
        if (null == accountShare) {
            accountShare = ApplictionHandle.getContext().getSharedPreferences(ACCOUNT_CFG, Context.MODE_PRIVATE);
        }
        return accountShare;
    }

//todo 这里的是不是跟下面的getLoginUser重复了？？
    public String getUserName() {
        return getAccountShare().getString(LOGIN_USERNAME, "");
    }

    public void setUserName(String userName) {
        getAccountShare().edit().putString(LOGIN_USERNAME, userName).commit();
    }
    //20140306 设置与获取登录界面下所设置的状态
    public void setLoginStatus(int status){
        getAccountShare().edit().putInt(LOGINSTATUS, status).commit();
    }

    public int getLoginStatus(){
        return getAccountShare().getInt(LOGINSTATUS, PersonalContact.ON_LINE);
    }

    public String getLoginPSW() {
        return getAccountShare().getString(LOGIN_PSW, "");
    }

    public void setLoginPSW(String loginPass) {
        getAccountShare().edit().putString(LOGIN_PSW, loginPass).commit();
    }

    public String getLoginUser() {
        return getAccountShare().getString(LOGIN_USER, "");
    }

    public void setLoginUser(String loginUser) {
        getAccountShare().edit().putString(LOGIN_USER, loginUser).commit();
    }

    public boolean isAutoLogin() {
        return getAccountShare().getBoolean(AUTOLOGIN, false);
    }

    public void setAutoLogin(boolean autoLogin) {
        getAccountShare().edit().putBoolean(AUTOLOGIN, autoLogin).commit();
    }

    public String getServerUrl() {
        return getAccountShare().getString(SERVERURL, "");
    }

    public void setServerUrl(String serverUrl) {
        getAccountShare().edit().putString(SERVERURL, serverUrl).commit();
    }

    public String getLan() {
        return getAccountShare().getString(LOGIN_LAN, "");
    }

    public void setLan(String lan) {
        getAccountShare().edit().putString(LOGIN_LAN, lan).commit();
    }

    public String getServerPort() {
        return getAccountShare().getString(SERVER_PORT, "");
    }

    public void setServerPort(String serverPort) {
        getAccountShare().edit().putString(SERVER_PORT, serverPort).commit();
    }

    public String getVersion() {
        return getAccountShare().getString(VERSON, "");
    }

    public void setVersion(String version) {
        getAccountShare().edit().putString(VERSON, version).commit();
    }

    public String getNotificationRing() {
        return getAccountShare().getString(NOTIFICATION_RING, "");
    }

    public void setNotificationRing(String notificationRing) {
        getAccountShare().edit().putString(NOTIFICATION_RING, notificationRing)
                .commit();
    }

    public String getPhoneIncomingRing() {
        return getAccountShare().getString(PHONE_INCOMING_RING, "");
    }

    public void setPhoneIncomingRing(String phoneIncomingRing) {
        getAccountShare().edit().putString(PHONE_INCOMING_RING,
                phoneIncomingRing).commit();
    }

    public boolean isSavePassword() {
        return getAccountShare().getBoolean(SAVEPASSWORD, false);
    }

    public void setSavePassword(boolean s) {
        getAccountShare().edit().putBoolean(SAVEPASSWORD, s).commit();
    }

    public boolean isLogFeedBack() {
        return getAccountShare().getBoolean(LOG_FEEDBACK, true);
    }

    public void setLogFeedBack(boolean l) {
        getAccountShare().edit().putBoolean(LOG_FEEDBACK, l).commit();
    }

    public String getSelfName() {
        return getAccountShare().getString(SELFNAME, "");
    }

    public void setSelfName(String n) {
        getAccountShare().edit().putString(SELFNAME, n).commit();
    }

    public String getSelfNativeName() {
        return getAccountShare().getString(SELFNATIVENAME, "");
    }

    public void setSelfNativeName(String nativeName) {
        getAccountShare().edit().putString(SELFNATIVENAME, nativeName).commit();
    }

    public String getSvnIp() {
        return getAccountShare().getString(SVN_IP, "");
    }

    public void setSvnIp(String ip) {
        getAccountShare().edit().putString(SVN_IP, ip).commit();
    }

    public String getSvnPort() {
        return getAccountShare().getString(SVN_PORT, "");
    }

    public void setSvnPort(String port) {
        getAccountShare().edit().putString(SVN_PORT, port).commit();
    }

    public boolean isSvnFlag() {
        return getAccountShare().getBoolean(SVN_FLAG, false);
    }

    public void setSvnFlag(boolean s) {
        getAccountShare().edit().putBoolean(SVN_FLAG, s).commit();
    }

    public String getSvnAccount() {
        return getAccountShare().getString(SVN_ACCOUNT, "");
    }

    public void setSvnAccount(String acc) {
        getAccountShare().edit().putString(SVN_ACCOUNT, acc).commit();
    }

    public String getSvnPassword() {
        return getAccountShare().getString(SVN_PASSWORD, "");
    }

    public void setSvnPassword(String sp) {
        getAccountShare().edit().putString(SVN_PASSWORD, sp).commit();
    }

    //20140306 此处判断是否是第一次安装，第一次安装为true，进入netsetting设置完成后，变为false。
    public boolean isGuidedFlag() {
        return getAccountShare().getBoolean(GUIDEFLAG, true);
    }

    public void setGuideFlag(boolean g) {
        getAccountShare().edit().putBoolean(GUIDEFLAG, g).commit();
    }

    public boolean isCrashExit() {
        return getAccountShare().getBoolean(CRASHEXIT, false);
    }

    public void setCrashExit(boolean c) {
        getAccountShare().edit().putBoolean(CRASHEXIT, c).commit();
    }

    public void delAccountShareFile() {
        File file = new File(ApplictionHandle.getContext().getFilesDir().getParent()
                + "/shared_prefs", ACCOUNT_CFG + ".xml");

        if (file.exists()) {
            if (!file.delete()) {
                Logger.debug(ConstantsUtil.APPTAG, "delete " + ACCOUNT_CFG + " fail");
            }
        }
    }

    public String[] getScanedAccount() {
        String accounts = getAccountShare().getString(SCANEDACCOUNTS, "");
        if (StringUtil.isStringEmpty(accounts)) {
            return null;
        } else {
            return accounts.split(",");
        }

    }

    public void setScannedAccount(String account) {
        String accounts = getAccountShare().getString(SCANEDACCOUNTS, "");
        if (StringUtil.isStringEmpty(accounts)) {
            accounts = account;
        } else {
            accounts = accounts + "," + account;
        }
        getAccountShare().edit().putString(SCANEDACCOUNTS, accounts).commit();
    }

    public long getAutoAnswerTime() {
        return getAccountShare().getLong(AUTO_ANSWER_TIME, -1);
    }

    /**
     * @param time 单位ms
     */
    public void setAutoAnswerTime(long time) {
        getAccountShare().edit().putLong(AUTO_ANSWER_TIME, time).commit();
    }

    public String getHelp() {
        return getAccountShare().getString(HELP_URL, "");
    }

    public void setHelp(String help) {
        getAccountShare().edit().putString(HELP_URL, help).commit();
    }
}
