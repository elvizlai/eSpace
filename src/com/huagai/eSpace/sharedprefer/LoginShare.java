package com.huagai.eSpace.sharedprefer;


import android.content.Context;
import android.content.SharedPreferences;
import com.huagai.eSpace.common.ApplictionHandle;
import com.huagai.eSpace.util.ConstantsUtil;
import com.huawei.common.Resource;
import com.huawei.ecs.mtk.log.Logger;

import java.io.File;

/**
 * 登录权限信息
 */
public class LoginShare {
    public static final int MAXMSGSIZE_INIT = 1000;
    /**
     * 服务器类型常量
     */
    public static final String HUAWEI_UC = "HUAWEIUC";
    public static final String UC_2_0 = "UC2.0";
    public static final String UC_1_0 = "UC1.0";
    public static final String UC_1_1 = "UC1.1";
    /**
     * 登录权限配置文件名
     */
    private static final String USER_POWER = "share_common";
    /**
     * 登录相关权限
     * 依次为：是否支持CTC     是否支持CTD    是否支持VoIP   是否支持mediax   是否支持公告    是否支持国家码
     * 是否支持本地匹配   VOIP功能注册是否要加域名支持     是否支持多媒体会议   是否支持创建预约会议
     * 是否支持无缝切换   服务器类型      同步联系人方式   消息最大限制       图片最大限制
     * 移动电话        办公电话       短号         终端号码         本地呼叫       图像id
     * 部门通知功能     国家码
     */

    private static final String SELFCONTACT = "self_personalcontact";
    private static final String CTC_FLAG = "ctcFlag";
    private static final String CTD_FLAG = "ctdFlag";
    private static final String VOIP_FLAG = "voipFlag";
    private static final String MEDIAX_FLAG = "mediaxflag";
    private static final String NEWS_FLAG = "newsflag";
    private static final String COUNTRYCODE_FLAG = "countrycode";
    private static final String MATCHLOCALPOWER = "matchmobile";
    private static final String VOIPDOMAIN_FLAG = "voipDomainFlag";
    private static final String ALLOWDATACONF_FLAG = "allowdataconf";
    private static final String ALLOWBOOKCONF_FLAG = "allowbookconf";
    private static final String REFERTO_FLAG = "referto";
    private static final String IMSERVERTYPE = "imserver_type";
    private static final String SYNCMODE = "syncmode";
    private static final String MAXMSGSIZE = "maxmsgsize";
    private static final String MAXPICTURESIZE = "maxpicturesize";
    private static final String CALLTYPE = "callType";
    private static final String CALLBACKNUMBER = "callBackNumber";
    private static final String MOBILE = "mobile";
    private static final String PHONE = "phone";
    private static final String SHORTPHONE = "shortphone";
    private static final String BINDNUM = "bindnum";
    private static final String VOIPNUM = "voipnum";
    private static final String VOIPNUM_SECOND = "voipnum2";
    private static final String OFFICEPHONE = "officephone";
    private static final String ADDRESS = "address";
    private static final String SEX = "sex";
    private static final String CALLPHONE = "callPhone";
    private static final String SIGNATURE = "signature";
    private static final String LOCALPHONE = "localPhone";
    private static final String TRANSPHONEFLAG = "transPhoneFlag";
    private static final String TRANSPHONENUM = "transPhoneNum";
    private static final String CALLFORWARDFLAG = "callForwardFlag";
    private static final String ENTVLEVEL = "entvlevel";
    private static final String PHONECALL_FLAG = "phonecallflag";
    private static final String HEADADDR = "headaddr";
    private static final String MAILADDR = "mailaddr";
    private static final String DEPTMENT = "deptment";
    private static final String FAX = "fax";
    private static final String ZIPCODE = "zipcode";
    private static final String HOMEPAGE = "homepage";
    private static final String POST = "post";
    private static final String HEAD_ID = "headid";
    private static final String DEPARTMENTFLAG = "departmentflag";
    private static final String CONSTGROUP = "constgroupflag";
    private static final String LOGIN3G = "login3gflag";
    /**
     * 同步联系人方式、图像、消息默值
     */
    private static final int SYNCMODE_INIT = 2;
    private static final int MAXPICTURESIZE_INIT = 20;
    /**
     * 登录权限配置文件
     */
    private static SharedPreferences loginShare = null;
    private static LoginShare ins;

    public static LoginShare getIns() {
        if (null == ins) {
            ins = new LoginShare();
        }
        return ins;
    }

    /**
     * 登录权限相关
     *
     * @return
     */
    public SharedPreferences getLoginShare() {
        if (null == loginShare) {
            loginShare = ApplictionHandle.getContext().getSharedPreferences(USER_POWER, Context.MODE_PRIVATE);
        }
        return loginShare;
    }

    /**
     * 清空登录权限配置文件信息
     */
    public void clearLoginShare() {
        getLoginShare().edit().clear().commit();
    }

    public boolean isCTCFlag() {
        return getLoginShare().getBoolean(CTC_FLAG, false);
    }

    public void setCTCFlag(boolean flag) {
        getLoginShare().edit().putBoolean(CTC_FLAG, flag).commit();
    }

    public boolean isCTDFlag() {
        return getLoginShare().getBoolean(CTD_FLAG, false);
    }

    public void setCTDFlag(boolean flag) {
        getLoginShare().edit().putBoolean(CTD_FLAG, flag).commit();
    }

    public boolean isVoIPFlag() {
        return getLoginShare().getBoolean(VOIP_FLAG, false);
    }

    public void setVoIPFlag(boolean voIPFlag) {
        getLoginShare().edit().putBoolean(VOIP_FLAG, voIPFlag).commit();
    }

    public boolean isMediaxFlag() {
        return getLoginShare().getBoolean(MEDIAX_FLAG, false);
    }

    public void setMediaxFlag(boolean mediaxFlag) {
        getLoginShare().edit().putBoolean(MEDIAX_FLAG, mediaxFlag).commit();
    }

    public boolean isNewsFlag() {
        return getLoginShare().getBoolean(NEWS_FLAG, false);
    }

    public void setNewsFlag(boolean newsFlag) {
        getLoginShare().edit().putBoolean(NEWS_FLAG, newsFlag).commit();
    }

    public boolean isCountryCodeFlag() {
        return getLoginShare().getBoolean(COUNTRYCODE_FLAG, false);
    }

    public void setCountryCodeFlag(boolean countryCodeFlag) {
        getLoginShare().edit().putBoolean(COUNTRYCODE_FLAG, countryCodeFlag)
                .commit();
    }

    public boolean isMatchLocalPower() {
        return getLoginShare().getBoolean(MATCHLOCALPOWER, false);
    }

    public void setMatchLocalPower(boolean matchlocal) {
        getLoginShare().edit().putBoolean(MATCHLOCALPOWER, matchlocal)
                .commit();
    }

    public boolean isVoipDomainFlag() {
        return getLoginShare().getBoolean(VOIPDOMAIN_FLAG, false);
    }

    public void setVoipDomainFlag(boolean voipDomainFlag) {
        getLoginShare().edit().putBoolean(VOIPDOMAIN_FLAG, voipDomainFlag)
                .commit();
    }

    public boolean isAllowDataConfFlag() {
        return getLoginShare().getBoolean(ALLOWDATACONF_FLAG, false);
    }

    public void setAllowDataConfFlag(boolean allowDataConfFlag) {
        getLoginShare().edit()
                .putBoolean(ALLOWDATACONF_FLAG, allowDataConfFlag).commit();
    }

    public boolean isAllowBookConfFlag() {
        return getLoginShare().getBoolean(ALLOWBOOKCONF_FLAG, false);
    }

    public void setAllowBookConfFlag(boolean allowBookConfFlag) {
        getLoginShare().edit()
                .putBoolean(ALLOWBOOKCONF_FLAG, allowBookConfFlag).commit();
    }

    public boolean isReferToFlag() {
        return getLoginShare().getBoolean(REFERTO_FLAG, false);
    }

    public void setReferToFlag(boolean referToFlag) {
        getLoginShare().edit().putBoolean(REFERTO_FLAG, referToFlag).commit();
    }

    public String getImServerType() {
        return getLoginShare().getString(IMSERVERTYPE, "");
    }

    public void setImServerType(String serverType) {
        getLoginShare().edit().putString(IMSERVERTYPE, serverType).commit();
    }

    public int getSyncmode() {
        return getLoginShare().getInt(SYNCMODE, SYNCMODE_INIT);
    }

    public void setSyncmode(int syncmode) {
        getLoginShare().edit().putInt(SYNCMODE, syncmode).commit();
    }

    public int getMaxMsgSize() {
        return getLoginShare().getInt(MAXMSGSIZE, MAXMSGSIZE_INIT);
    }

    public void setMaxMsgSize(int maxMsgSize) {
        getLoginShare().edit().putInt(MAXMSGSIZE, maxMsgSize).commit();
    }

    public int getMaxPictureSize() {
        return getLoginShare().getInt(MAXPICTURESIZE, MAXPICTURESIZE_INIT);
    }

    public void setMaxPictureSize(int maxPictureSize) {
        getLoginShare().edit().putInt(MAXPICTURESIZE, maxPictureSize).commit();
    }

    public int getCallType() {
        return getLoginShare().getInt(CALLTYPE, 2);
    }

    public void setCallType(int type) {
        getLoginShare().edit().putInt(CALLTYPE, type).commit();
    }

    public String getCallbackNumber() {
        return getLoginShare().getString(CALLBACKNUMBER, getBindNum());
    }

    public void setCallbackNumber(String callBackNumber) {
        getLoginShare().edit().putString(CALLBACKNUMBER, callBackNumber).commit();
    }

    public String getMobile() {
        return getLoginShare().getString(MOBILE, "");
    }

    public void setMobile(String mobile) {
        getLoginShare().edit().putString(MOBILE, mobile).commit();
    }

    public String getPhone() {
        return getLoginShare().getString(PHONE, "");
    }

    public void setPhone(String phone) {
        getLoginShare().edit().putString(PHONE, phone).commit();
    }

    public String getShortPhone() {
        return getLoginShare().getString(SHORTPHONE, "");
    }

    public void setShortPhone(String shortPhone) {
        getLoginShare().edit().putString(SHORTPHONE, shortPhone).commit();
    }

    public String getBindNum() {
        return getLoginShare().getString(BINDNUM, "");
    }

    public void setBindNum(String bindNum) {
        getLoginShare().edit().putString(BINDNUM, bindNum).commit();
    }

    public String getOfficePhone() {
        return getLoginShare().getString(OFFICEPHONE, "");
    }

    public void setOfficePhone(String officePhone) {
        getLoginShare().edit().putString(OFFICEPHONE, officePhone).commit();
    }

    public String getAddress() {
        return getLoginShare().getString(ADDRESS, "");
    }

    public void setAddress(String address) {
        getLoginShare().edit().putString(ADDRESS, address).commit();
    }

    public String getSex() {
        return getLoginShare().getString(SEX, "");
    }

    public void setSex(String sex) {
        getLoginShare().edit().putString(SEX, sex).commit();
    }

    public String getVoipNum() {
        return getLoginShare().getString(VOIPNUM, "");
    }

    public void setVoipNum(String voipNum) {
        getLoginShare().edit().putString(VOIPNUM, voipNum).commit();
    }

    public String getVoipNum2() {
        return getLoginShare().getString(VOIPNUM_SECOND, "");
    }

    public void setVoipNum2(String voipNum2) {
        getLoginShare().edit().putString(VOIPNUM_SECOND, voipNum2).commit();
    }

    public String getCallPhone() {
        return getLoginShare().getString(CALLPHONE, "");
    }

    public void setCallPhone(String callPhone) {
        getLoginShare().edit().putString(CALLPHONE, callPhone).commit();
    }

    public String getSignature() {
        return getLoginShare().getString(SIGNATURE, "");
    }

    public void setSignature(String sig) {
        getLoginShare().edit().putString(SIGNATURE, sig).commit();
    }

    public String getHeadAddr() {
        return getLoginShare().getString(HEADADDR, "");
    }

    public void setHeadAddr(String headAddr) {
        getLoginShare().edit().putString(HEADADDR, headAddr).commit();
    }

    public String getMailAddr() {
        return getLoginShare().getString(MAILADDR, null);
    }

    public void setMailAddr(String mailAddr) {
        getLoginShare().edit().putString(MAILADDR, mailAddr).commit();
    }

    public String getDeptment() {
        return getLoginShare().getString(DEPTMENT, "");
    }

    public void setDeptment(String deptment) {
        getLoginShare().edit().putString(DEPTMENT, deptment).commit();
    }

    public String getZipCode() {
        return getLoginShare().getString(ZIPCODE, "");
    }

    public void setZipCode(String zipCode) {
        getLoginShare().edit().putString(ZIPCODE, zipCode).commit();
    }

    public String getFax() {
        return getLoginShare().getString(FAX, "");
    }

    public void setFax(String fax) {
        getLoginShare().edit().putString(FAX, fax).commit();
    }

    public String getHomePage() {
        return getLoginShare().getString(HOMEPAGE, "");
    }

    public void setHomePage(String homePage) {
        getLoginShare().edit().putString(HOMEPAGE, homePage).commit();
    }

    public String getPost() {
        return getLoginShare().getString(POST, "");
    }

    public void setPost(String post) {
        getLoginShare().edit().putString(POST, post).commit();
    }

    public String getLocalPhone() {
        return getLoginShare().getString(LOCALPHONE, "");
    }

    public void setLocalPhone(String localPhone) {
        getLoginShare().edit().putString(LOCALPHONE, localPhone).commit();
    }

    public boolean isCallForward() {
        return getLoginShare().getBoolean(CALLFORWARDFLAG, false);
    }

    public void setCallForward(boolean callForward) {
        getLoginShare().edit().putBoolean(CALLFORWARDFLAG, callForward).commit();
    }

    public boolean isTransPhone() {
        return getLoginShare().getBoolean(TRANSPHONEFLAG, false);
    }

    public void setTransPhone(boolean transPhone) {
        getLoginShare().edit().putBoolean(TRANSPHONEFLAG, transPhone).commit();
    }

    public int getTransPhoneNum() {
        return getLoginShare().getInt(TRANSPHONENUM, Resource.Num.THREE);
    }

    public void setTransPhoneNum(int num) {
        getLoginShare().edit().putInt(TRANSPHONENUM, num).commit();
    }

    public String getEntvlevel() {
        return getLoginShare().getString(ENTVLEVEL, "");
    }

    public void setEntvlevel(String e) {
        getLoginShare().edit().putString(ENTVLEVEL, e).commit();
    }

    public boolean isPhoneCallFlag() {
        return getLoginShare().getBoolean(PHONECALL_FLAG, false);
    }

    public void setPhoneCallFlag(boolean phoneCall) {
        getLoginShare().edit().putBoolean(PHONECALL_FLAG, phoneCall).commit();
    }

    public String getHeadId() {
        return getLoginShare().getString(HEAD_ID, "");
    }

    public void setHeadId(String id) {
        getLoginShare().edit().putString(HEAD_ID, id).commit();
    }

    public boolean isDepartMentFlag() {
        return getLoginShare().getBoolean(DEPARTMENTFLAG, false);
    }

    public void setDepartMentFlag(boolean d) {
        getLoginShare().edit().putBoolean(DEPARTMENTFLAG, d).commit();
    }

    public boolean isConstGroupFlag() {
        return getLoginShare().getBoolean(CONSTGROUP, false);
    }

    public void setConstGroupFlag(boolean cg) {
        getLoginShare().edit().putBoolean(CONSTGROUP, cg).commit();
    }

    public boolean isLogin3GFlag() {
        return getLoginShare().getBoolean(LOGIN3G, true);
    }

    public void setLogin3GFlag(boolean login3G) {
        getLoginShare().edit().putBoolean(LOGIN3G, login3G).commit();
    }

    public void delLoginShareFile() {
        File file = new File(ApplictionHandle.getContext().getFilesDir().getParent()
                + "/shared_prefs",
                loginShare + ".xml");
        if (file.exists()) {
            if (!file.delete()) {
                Logger.error(ConstantsUtil.APPTAG, "delete " + loginShare
                        + " fail");
            }
        }
    }

    public void setSelfContact(String contact) {
        getLoginShare().edit().putString(SELFCONTACT, contact).commit();
    }

    public String getSelfConact() {
        return getLoginShare().getString(SELFCONTACT, null);
    }

}

