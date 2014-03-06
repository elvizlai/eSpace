package com.huagai.eSpace.selfInfo;


import com.huagai.eSpace.sharedprefer.AccountShare;
import com.huagai.eSpace.sharedprefer.LoginShare;
import com.huagai.eSpace.util.ConstantsUtil;
import com.huawei.common.PersonalContact;
import com.huawei.common.Resource;
import com.huawei.contacts.StatusManager;
import com.huawei.data.StatusData;
import com.huawei.ecs.mip.msg.LoginAck.VideoCodec;
import com.huawei.ecs.mtk.log.Logger;
import com.huawei.utils.Base64;
import com.huawei.utils.DataEncryption;
import com.huawei.utils.StringUtil;

import java.io.*;

/**
 * 个人信息类
 */
public class SelfData {
    /**
     * svn默认端口号
     */
    private static final String SVN_DEFAULT_PORT = "443";
    /**
     * voip相关权限信息
     */
    private VoipAbility voipAbility = new VoipAbility();
    /**
     * 图像id
     */
    private String headId;
    /**
     * 自己的名字
     */
    private String selfName;
    /**
     * 本地名字
     */
    private String selfNativeName;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 是否保存密码
     */
    private Boolean savePassword;
    /**
     * 呼叫方式
     */
    private Integer callType;
    /**
     * 回拨号码
     */
    private String callbackNumber;
    /**
     * 签名
     */
    private String signatue;
    /**
     * 移动电话
     */
    private String mobile;
    /**
     * 工作
     */
    private String phone;
    /**
     * 短号
     */
    private String shortNum;
    /**
     * 终端号
     */
    private String bindNum;
    /**
     * IP电话
     */
    private String voipNum;
    /**
     * IP电话2
     */
    private String voipNum2;

    /**
     * 其他电话
     */
    private String officePhone;
    /**
     * 传真号码
     */
    private String fax;

    /**
     * 邮编号码
     */
    private String zipCode;
    /**
     * 邮箱
     */
    private String address;
    /**
     * 主页
     */
    private String homePage;
    /**
     * 性别
     */
    private String sex;
    /**
     * 数据会议中userid 唯一标识
     */
    private String dataConfUserId;
    /**
     * 服务器类型
     */
    private String imServerType;
    /**
     * 同步联系人方式
     */
    private Integer syncmode = null;
    /**
     * 消息最大限制
     */
    private Integer maxMsgSize = LoginShare.MAXMSGSIZE_INIT;
    /**
     * 图像最大限制
     */
    private Integer maxPictureSize = 20;
    /**
     * 图像地址
     */
    private String headAddr;
    /**
     * 邮件地址
     */
    private String mailAddr;
    /**
     * 职务
     */
    private String post;
    /**
     * 部门
     */
    private String deptment;
    /**
     * 是否支持CTC
     */
    private Boolean ctcFlag;
    /**
     * 是否支持CTD
     */
    private Boolean ctdFlag;
    /**
     * 是否支持VoIP
     */
    private Boolean voipFlag;
    /**
     * 是否支持预约会议
     */
    private Boolean mediaxFlag;
    /**
     * 是否支持公告
     */
    private Boolean bulletinFlag;
    /**
     * 是否支持国家码
     */
    private Boolean countryCodeFlag;
    /**
     * 是否支持本地匹配
     */
    private Boolean matchLocalPowerFlag;
    /**
     * voip功能注册是否加域名
     */
    private Boolean voipDomainFlag;
    /**
     * 是否支持数据会议
     */
    private Boolean dataConfFlag;
    /**
     * 是否支持创建预约会议
     */
    private Boolean bookConfFlag;
    /**
     * 是否支持呼叫前传
     */
    private Boolean callForwardFlag;
    /**
     * 是否支持呼叫转接
     */
    private Boolean transPhoneFlag;

    /**
     * 是否支持语音邮箱
     */
    private boolean voiceMailFlag;
    /**
     * 呼叫转接号码个数
     */
    private Integer transPhoneNum;
    /**
     * 本地号码
     */
    private String localPhone;
    /**
     * 企业通讯录密级
     */
    private String entvlevel;
    /**
     * 是否支持本地呼叫
     */
    private Boolean phoneCallFlag;

    /**
     * 是否支持固定群
     */
    private Boolean constGroupFlag;

    /**
     * 3G标志位
     */
    private Boolean login3GFlag;

    /**
     * CTC仅限工作提示
     */
    private boolean ctcUsePromptFlag = false;

    /**
     * CTD仅限工作提示
     */
    private boolean ctdUsePromptFlag = false;

    /**
     * MAA返回的位置值. 1 内网, 2 外网
     */
    private int location;

    /**
     * MAA的唯一标识,上报终端类型时使用;
     */
    private String maaDeployID;

    /**
     * 语音编解码
     */
    private String audioCodec;

    /**
     * 上次登录设定的位置信息
     */
    private String lastLocation;

    /**
     * 密码限呼类型 1: 限制国内呼叫 2: 限制国际呼叫 3: 限制国内和国际呼叫
     */
    private int callLimitType;

    /**
     * Password Call Limit 密码限呼功能，功能位开的时候，显示 一个提示，不能呼出。类型见callLimitType.
     */
    private boolean callLimit;

    /**
     * 密码限呼逾越功能码
     */
    private String passwordCallAccessCode;

    /**
     * 降噪参数 ANR, 取值为 1,2,3,4
     */
    private int anr;

    /**
     * 语音QOS(Quality of Service)标志位 0-64
     */
    private int audioDSCP;

    /**
     * 视频QOS(Quality of Service)标志位 0-64
     */
    private int vedioDSCP;

    /**
     * 数据会议组件通信数据QOS标志位
     */
    private int dataDSCP;

    /**
     * SIP信令QOS标志位
     */
    private int sipDSCP;

    /**
     * MAA信令QOS标志位 0-64
     */
    private int maaDSCP;

    /**
     * 管理员控制无条件前转
     * <p/>
     * 1 由管理员控制，个人无权在界面上更改无条件前转号码，需要灰化。 如果管理员控制，终端需要知道并且在界面上提示您的电话已经无条件
     * 前转到另一个电话处。 0 由个人控制，可以在界面上更改无条件前转号码。
     */
    private int controlCFU = 0;

    /**
     * HOLD切换时长, 超过这个时长就要提醒用户还有一路在后台HOLD
     * <p/>
     * 0 表示不切换提醒
     * <p/>
     * 默认为0
     */
    private int holdDuration = 0;

    /**
     * 语音编码是否支持fec(前向纠错/Forward Error Correction)冗余
     * <p/>
     * 1 支持 0 不支持
     */
    private int fec = 0;

    /**
     * 无应答前转功能位
     */
    private boolean bIsCallForwardingNoReplay;

    /**
     * 离线前转功能位
     */
    private boolean bIsCallForwardingOffline;

    /**
     * 遇忙前转功能位
     */
    private boolean bIsCallForwardingOnBusy;

    /**
     * 无条件前转功能位
     */
    private boolean bIsCallForwardingUnconditional;

    /**
     * 变化后的功能位 和 Login 的 FUNCID 保持一致
     */
    private String funcId;

    /**
     * 账户配置信息 account : 登录用户账号，要用到eSpace账号的地方都从该字段获取
     */
    private String account;
    private String password;
    private String language;
    private String serverUrl;
    private String serverPort;
    private String notificationRing;
    private String version;
    private Boolean autoLoginFlag;
    private String callRing;
    private String svnIp;
    private String svnPort;
    private String svnAccount;
    private Boolean svnFlag;


    /**
     * 日志开关
     */
    private Boolean logFeedBack;

    private boolean connect = true;
    /**
     * 敏感词标志，不需要保存到配置文件，每次登录重新获取
     */
    private int sensitive;
    /**
     * 服务器支持的加密能力版本 1 加密
     */
    private Boolean encryptFlag;
    /**
     * 部门通知功能是否开启
     */
    private Boolean departMentNotify;
    /**
     * 是否浏览过教程
     */
    private Boolean scanedGuide;

    private Boolean crashExit;

    /**
     * 是否支持聊天
     */
    private boolean haveImAndPresenceAbility = true;

    /**
     * 是否即支持wifi,又支持3g拨打voip.(true 都支持; false 只支持wifi呼叫voip.)
     */
    private boolean voip3gAbility = false;

    /**
     * 支持云存储CALLLOG特性功能位,1支持,0不支持.
     */
    private boolean serverCallLogAbility = false;

    /**
     * 创建新分组权限;
     */
    private boolean createNewGroupAbility = false;

    /**
     * 显示免打扰权限
     */
    private boolean dndAbility = false;

    private boolean lbsAbility = false;

    private String deptDesc = null;

    private boolean umAbility = false;

    private boolean msgLogAbility = false;

    private boolean videoConfAbility = false;

    private boolean dynLabelAbility = false;

    private String umServerHttp;

    private String umServerHttps;

    private String backupUmServerHttp;

    private String backupUmServerHttps;

    private String digitMap;

    private String help;

    /**
     * 自动接听等待时间，-1为默认值(留作扩展使用)，即不自动接听 0～60*1000表示等待毫秒数
     */
    private long autoAnswerTime = -1;

    private PersonalContact self;

    /**
     * 呼叫等待
     */
    private boolean callWait;

    private boolean confUpdate;
    private String staffNo;
    private String notesMail;
    private String faxList;
    private String otherInfo;
    private String contact;
    private String assistantList;
    private String displayName;
    private String foreignName;
    private String voipList;
    private String room;
    private String interPhoneList;
    private String deptDescEnglish;
    private String mobileList;
    private String homePhone;
    private String phoneList;
    private VideoCodec videoCodec;

    public String getUmServerHttp() {
        return umServerHttp;
    }

    public void setUmServerHttp(String umServerHttp) {
        this.umServerHttp = umServerHttp;
    }

    public String getUmServerHttps() {
        return umServerHttps;
    }

    public void setUmServerHttps(String umServerHttps) {
        this.umServerHttps = umServerHttps;
    }

    public String getBackupUmServerHttp() {
        return backupUmServerHttp;
    }

    public void setBackupUmServerHttp(String backupUmServerHttp) {
        this.backupUmServerHttp = backupUmServerHttp;
    }

    public String getBackupUmServerHttps() {
        return backupUmServerHttps;
    }

    public void setBackupUmServerHttps(String backupUmServerHttps) {
        this.backupUmServerHttps = backupUmServerHttps;
    }

    public String getDigitMap() {
        return digitMap;
    }

    public void setDigitMap(String digitMap) {
        this.digitMap = digitMap;
    }

    public Boolean isSavePassword() {
        if (null == savePassword) {
            savePassword = AccountShare.getIns().isSavePassword();
        }

        return savePassword;
    }

    public void setSavePassword(Boolean s) {
        savePassword = s;
        AccountShare.getIns().setSavePassword(savePassword);
    }


    public int getCallType() {
        if (null == callType) {
            callType = LoginShare.getIns().getCallType();
        }

        return callType;
    }

    public void setCallType(int c) {
        callType = c;
        LoginShare.getIns().setCallType(callType);
    }

    public String getCallbackNumber() {
        if (StringUtil.isStringEmpty(callbackNumber)) {
            callbackNumber = LoginShare.getIns().getCallbackNumber();
        }

        // 用户配置表里没有取到主叫号码
        if (StringUtil.isStringEmpty(callbackNumber)) {
            // 默认选择 移动号码 作为回呼号码
            if (!StringUtil.isStringEmpty(getMobile())) {
                callbackNumber = getMobile();
            } else {
                callbackNumber = getBindNum();
            }

            // 保存主叫号码
            setCallbackNumber(callbackNumber);
        }

        return callbackNumber;
    }

    public void setCallbackNumber(String number) {
        callbackNumber = number;
        LoginShare.getIns().setCallbackNumber(callbackNumber);
    }

    public Integer getStatus() {
        int myStatus = PersonalContact.DEF;
        StatusData data = StatusManager.getInstance().getStatusByAccount(
                account);
        if (data != null) {
            myStatus = data.getStatus();
        } else {
            if (status != null) {
                myStatus = status.intValue();
            }
        }

        if (myStatus == PersonalContact.DEF) {
            myStatus = PersonalContact.AWAY;
        }

        return myStatus;
    }

    public void setStatus(int sta) {
        StatusData data = StatusManager.getInstance().getStatusByAccount(
                account);
        if (data != null) {
            data.setStatus(sta);
        }

        status = sta;
    }

    /**
     * 登录返回信息
     *
     * @return
     */
    public String getSignatue() {
        if (StringUtil.isStringEmpty(signatue)) {
            signatue = LoginShare.getIns().getSignature();
        }
        return signatue;
    }

    public void setSignatue(String s) {
        signatue = s;
        LoginShare.getIns().setSignature(signatue);
    }

    public String getMobile() {
        if (StringUtil.isStringEmpty(mobile)) {
            mobile = LoginShare.getIns().getMobile();
        }
        return mobile;
    }

    public void setMobile(String m) {
        mobile = m;
        LoginShare.getIns().setMobile(mobile);
    }

    public String getPhone() {
        if (StringUtil.isStringEmpty(phone)) {
            phone = LoginShare.getIns().getPhone();
        }
        return phone;
    }

    public void setPhone(String p) {
        phone = p;
        LoginShare.getIns().setPhone(phone);
    }

    public String getShortNum() {
        if (StringUtil.isStringEmpty(shortNum)) {
            shortNum = LoginShare.getIns().getShortPhone();
        }
        return shortNum;
    }

    public void setShortNum(String s) {
        shortNum = s;
        LoginShare.getIns().setShortPhone(shortNum);
    }

    public String getBindNum() {
        if (StringUtil.isStringEmpty(bindNum)) {
            bindNum = LoginShare.getIns().getBindNum();
        }
        return bindNum;
    }

    public void setBindNum(String b) {
        bindNum = b;
        LoginShare.getIns().setBindNum(bindNum);
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
        LoginShare.getIns().setOfficePhone(officePhone);
    }

    public String getOfficePhone() {
        if (StringUtil.isStringEmpty(officePhone)) {
            officePhone = LoginShare.getIns().getOfficePhone();
        }
        return officePhone;
    }

    public void setAddress(String address) {
        this.address = address;
        LoginShare.getIns().setAddress(address);
    }

    public String getAddress() {
        if (StringUtil.isStringEmpty(address)) {
            address = LoginShare.getIns().getAddress();
        }
        return address;
    }

    public void setSex(String sex) {
        this.sex = sex;
        LoginShare.getIns().setSex(sex);
    }

    public String getSex() {
        if (StringUtil.isStringEmpty(sex)) {
            sex = LoginShare.getIns().getSex();
        }
        return sex;
    }

    public void setVoipNum(String voipNum) {
        this.voipNum = voipNum;
        LoginShare.getIns().setVoipNum(voipNum);
    }

    public String getVoipNum() {
        if (StringUtil.isStringEmpty(voipNum)) {
            voipNum = LoginShare.getIns().getVoipNum();
        }
        return voipNum;
    }

    public String getVoipNum2() {
        if (StringUtil.isStringEmpty(voipNum2)) {
            LoginShare.getIns().getVoipNum2();
        }
        return voipNum2;
    }

    public void setVoipNum2(String voipNum2) {
        this.voipNum2 = voipNum2;
        LoginShare.getIns().setVoipNum2(voipNum2);
    }

    public String getImServerType() {
        if (StringUtil.isStringEmpty(imServerType)) {
            imServerType = LoginShare.getIns().getImServerType();
        }
        return imServerType;
    }

    public void setImServerType(String type) {
        imServerType = type;
        LoginShare.getIns().setImServerType(imServerType);
    }

    public Integer getSyncmode() {
        if (null == syncmode) {
            syncmode = LoginShare.getIns().getSyncmode();
        }
        return syncmode;
    }

    public void setSyncmode(Integer mode) {
        syncmode = mode;
        LoginShare.getIns().setSyncmode(syncmode);
    }

    public Integer getMaxMsgSize() {
        if (null == maxMsgSize) {
            maxMsgSize = LoginShare.getIns().getMaxMsgSize();
        }
        return maxMsgSize;
    }

    public void setMaxMsgSize(int size) {
        maxMsgSize = size;
        LoginShare.getIns().setMaxMsgSize(maxMsgSize);
    }

    public Integer getMaxPictureSize() {
        if (null == maxPictureSize) {
            maxPictureSize = LoginShare.getIns().getMaxPictureSize();
        }
        return maxPictureSize;
    }

    public void setMaxPictureSize(int size) {
        maxPictureSize = size;
        LoginShare.getIns().setMaxPictureSize(maxPictureSize);
    }

    public String getHeadAddr() {
        if (StringUtil.isStringEmpty(headAddr)) {
            headAddr = LoginShare.getIns().getHeadAddr();
        }
        return headAddr;
    }

    public void setHeadAddr(String addr) {
        headAddr = addr;
        LoginShare.getIns().setHeadAddr(headAddr);
    }

    public String getMailAddr() {
        if (StringUtil.isStringEmpty(mailAddr)) {
            mailAddr = LoginShare.getIns().getMailAddr();
        }
        return mailAddr;
    }

    public void setMailAddr(String addr) {
        mailAddr = addr;
        LoginShare.getIns().setMailAddr(mailAddr);
    }

    public String getDeptment() {
        if (StringUtil.isStringEmpty(deptment)) {
            deptment = LoginShare.getIns().getDeptment();
        }
        return deptment;
    }

    public void setDeptment(String de) {
        deptment = de;
        LoginShare.getIns().setDeptment(deptment);
    }

    public String getFax() {
        if (StringUtil.isStringEmpty(fax)) {
            fax = LoginShare.getIns().getFax();
        }
        return fax;
    }

    public void setFax(String f) {
        fax = f;
        LoginShare.getIns().setFax(fax);
    }

    public String getZipCode() {
        if (StringUtil.isStringEmpty(zipCode)) {
            zipCode = LoginShare.getIns().getZipCode();
        }
        return zipCode;
    }

    public void setZipCode(String code) {
        zipCode = code;
        LoginShare.getIns().setZipCode(zipCode);
    }

    public String getHomePage() {
        if (StringUtil.isStringEmpty(homePage)) {
            homePage = LoginShare.getIns().getHomePage();
        }
        return homePage;
    }

    public void setHomePage(String page) {
        homePage = page;
        LoginShare.getIns().setHomePage(homePage);
    }

    public String getPost() {
        if (StringUtil.isStringEmpty(post)) {
            post = LoginShare.getIns().getPost();
        }
        return post;
    }

    public void setPost(String p) {
        post = p;
        LoginShare.getIns().setPost(post);
    }

    /**
     * *************************** 登录返回信息、权限 *******************************************
     */
    public Boolean isCtc() {
        if (null == ctcFlag) {
            ctcFlag = LoginShare.getIns().isCTCFlag();
        }
        return ctcFlag;
    }

    public void setCtc(Boolean ctc) {
        ctcFlag = ctc;
        LoginShare.getIns().setCTCFlag(ctcFlag);
    }

    public Boolean isCtd() {
        if (null == ctdFlag) {
            ctdFlag = LoginShare.getIns().isCTDFlag();
        }
        return ctdFlag;
    }

    public void setCtd(Boolean ctd) {
        ctdFlag = ctd;
        LoginShare.getIns().setCTDFlag(ctdFlag);
    }

    public Boolean isVoip() {
        if (null == voipFlag) {
            voipFlag = LoginShare.getIns().isVoIPFlag();
        }
        return voipFlag;
    }

    public void setVoip(Boolean voip) {
        voipFlag = voip;
        LoginShare.getIns().setVoIPFlag(voipFlag);
    }

    public Boolean isMediax() {
        if (null == mediaxFlag) {
            mediaxFlag = LoginShare.getIns().isMediaxFlag();
        }
        return mediaxFlag;
    }

    public void setMediax(Boolean mediax) {
        mediaxFlag = mediax;
        LoginShare.getIns().setMediaxFlag(mediaxFlag);
    }

    public Boolean isBulletin() {
        if (null == bulletinFlag) {
            bulletinFlag = LoginShare.getIns().isNewsFlag();
        }
        return bulletinFlag;
    }

    public void setBulletin(Boolean bulletin) {
        bulletinFlag = bulletin;
        LoginShare.getIns().setNewsFlag(bulletinFlag);
    }

    public Boolean isCountryCode() {
        if (null == countryCodeFlag) {
            countryCodeFlag = LoginShare.getIns().isCountryCodeFlag();
        }
        return countryCodeFlag;
    }

    public void setCountryCode(Boolean coun) {
        countryCodeFlag = coun;
        LoginShare.getIns().setCountryCodeFlag(countryCodeFlag);
    }

    public Boolean isMatchLocalPower() {
        if (null == matchLocalPowerFlag) {
            matchLocalPowerFlag = LoginShare.getIns().isMatchLocalPower();
        }
        return matchLocalPowerFlag;
    }

    public void setMatchLocalPower(Boolean matchLocal) {
        matchLocalPowerFlag = matchLocal;
        LoginShare.getIns().setMatchLocalPower(matchLocalPowerFlag);
    }

    public Boolean isVoipDomain() {
        if (null == voipDomainFlag) {
            voipDomainFlag = LoginShare.getIns().isVoipDomainFlag();
        }
        return voipDomainFlag;
    }

    public void setVoipDomain(Boolean domain) {
        voipDomainFlag = domain;
        LoginShare.getIns().setVoipDomainFlag(voipDomainFlag);
    }

    public Boolean isDataConf() {
        if (null == dataConfFlag) {
            dataConfFlag = LoginShare.getIns().isAllowDataConfFlag();
        }
        return dataConfFlag;
    }

    public void setDataConf(Boolean dc) {
        dataConfFlag = dc;
        LoginShare.getIns().setAllowDataConfFlag(dataConfFlag);
    }

    public Boolean isBookConf() {
        if (null == bookConfFlag) {
            bookConfFlag = LoginShare.getIns().isAllowBookConfFlag();
        }
        return bookConfFlag;
    }

    public void setBookConf(Boolean bc) {
        bookConfFlag = bc;
        LoginShare.getIns().setAllowBookConfFlag(bookConfFlag);
    }

    public boolean isReferTo() {
        return voipAbility.isReferTo();
    }

    public void setReferTo(Boolean rf) {
        voipAbility.setReferTo(rf);
    }

    public boolean isCallForward() {
        if (null == callForwardFlag) {
            callForwardFlag = LoginShare.getIns().isCallForward();
        }
        return callForwardFlag;
    }

    public void setCallForward(Boolean callForward) {
        callForwardFlag = callForward;
        LoginShare.getIns().setCallForward(callForwardFlag);
    }

    public boolean isVoiceMailFlag() {
        return voiceMailFlag;
    }

    public void setVoiceMailFlag(boolean voiceMailFlag) {
        this.voiceMailFlag = voiceMailFlag;
    }

    public boolean isTransPhone() {
        if (null == transPhoneFlag) {
            transPhoneFlag = LoginShare.getIns().isTransPhone();
        }
        return transPhoneFlag;
    }

    public void setTransPhone(Boolean transPhone) {
        transPhoneFlag = transPhone;
        LoginShare.getIns().setTransPhone(transPhoneFlag);
    }

    public Integer getTransPhneNum() {
        if (null == transPhoneNum) {
            transPhoneNum = LoginShare.getIns().getTransPhoneNum();
        }

        return transPhoneNum;
    }

    public void setTransPhoneNum(Integer num) {
        transPhoneNum = num;
        LoginShare.getIns().setTransPhoneNum(transPhoneNum);
    }

    public String getLocalPhone() {
        if (StringUtil.isStringEmpty(localPhone)) {
            localPhone = LoginShare.getIns().getLocalPhone();
        }
        return localPhone;
    }

    public void setLocalPhone(String l) {
        localPhone = l;
        LoginShare.getIns().setLocalPhone(l);
    }

    public String getEntvlevel() {
        if (StringUtil.isStringEmpty(entvlevel)) {
            entvlevel = LoginShare.getIns().getEntvlevel();
        }
        return entvlevel;
    }

    public void setEntvlevel(String en) {
        entvlevel = en;
        LoginShare.getIns().setEntvlevel(entvlevel);
    }

    public boolean isPhoneCall() {
        if (null == phoneCallFlag) {
            phoneCallFlag = LoginShare.getIns().isPhoneCallFlag();
        }

        return phoneCallFlag;
    }

    public void setPhoneCall(boolean p) {
        phoneCallFlag = p;
        LoginShare.getIns().setPhoneCallFlag(p);
    }

    /**
     * ****** 登录账户信息 *********
     */
    public String getAccount() {
        if (StringUtil.isStringEmpty(account)) {
            account = AccountShare.getIns().getLoginUser();
        }

        return account;
    }

    public void setAccount(String a) {
        account = a;
        AccountShare.getIns().setLoginUser(account);
    }

    public String getPassword() {
        String p = AccountShare.getIns().getLoginPSW();

        try {
            // 只有一个参数的unEncrypt是AES
            password = DataEncryption.unEncrypt(Base64.decode(p));
        } catch (UnsupportedEncodingException e) {
            Logger.error(ConstantsUtil.APPTAG,
                    "e.toString : " + e.toString());
        }

        return password;
    }

    public void setPassword(String psw) {
        // 只有一个参数的encrypt是AES加密
        password = Base64.encode(DataEncryption.encrypt(psw));
        AccountShare.getIns().setLoginPSW(password);
    }

    public String getLanguage() {
        if (StringUtil.isStringEmpty(language)) {
            language = AccountShare.getIns().getLan();
        }
        return language;
    }

    public void setLanguage(String lan) {
        language = lan;
        AccountShare.getIns().setLan(language);
    }

    public String getServerUrl() {
        if (StringUtil.isStringEmpty(serverUrl)) {
            serverUrl = AccountShare.getIns().getServerUrl();
        }

        if (StringUtil.isStringEmpty(serverUrl)) {
            serverUrl = Resource.RequestURL.LOGIN_SERVER;
        }
        return serverUrl;
    }

    public void setServerUrl(String u) {
        serverUrl = u;
        AccountShare.getIns().setServerUrl(serverUrl);
    }

    public String getServerPort() {
        if (StringUtil.isStringEmpty(serverPort)) {
            serverPort = AccountShare.getIns().getServerPort();
        }

        if (StringUtil.isStringEmpty(serverPort)) {
            serverPort = Resource.RequestURL.LOGIN_PORT;
        }
        return serverPort;
    }

    public void setServerPort(String p) {
        serverPort = p;
        AccountShare.getIns().setServerPort(serverPort);
    }

    public String getNotificationRing() {
        if (StringUtil.isStringEmpty(notificationRing)) {
            notificationRing = AccountShare.getIns().getNotificationRing();
        }
        return notificationRing;
    }

    public void setNotificationRing(String n) {
        notificationRing = n;
        AccountShare.getIns().setNotificationRing(notificationRing);
    }

    public String getVersion() {
        if (StringUtil.isStringEmpty(version)) {
            version = AccountShare.getIns().getVersion();
        }
        return version;
    }

    public void setVersion(String v) {
        version = v;
        AccountShare.getIns().setVersion(version);
    }

    public Boolean isAutoLogin() {
        if (null == autoLoginFlag) {
            autoLoginFlag = AccountShare.getIns().isAutoLogin();
        }
        return autoLoginFlag;
    }

    public void setAutoLogin(Boolean a) {
        autoLoginFlag = a;
        AccountShare.getIns().setAutoLogin(autoLoginFlag);
    }

    public String getCallRing() {
        if (null == callRing) {
            callRing = AccountShare.getIns().getPhoneIncomingRing();
        }

        return callRing;
    }

    public void setCallRing(String c) {
        callRing = c;
        AccountShare.getIns().setPhoneIncomingRing(callRing);
    }

    public Boolean isLogFeedBack() {
        if (null == logFeedBack) {
            logFeedBack = AccountShare.getIns().isLogFeedBack();
        }

        return logFeedBack;
    }

    public void setLogFeedBack(Boolean l) {
        logFeedBack = l;
        AccountShare.getIns().setLogFeedBack(logFeedBack);
    }

    public String getSelfName() {
        if (StringUtil.isStringEmpty(selfName)) {
            selfName = AccountShare.getIns().getSelfName();
        }

        return selfName;
    }

    public void setSelfName(String n) {
        selfName = n;
        AccountShare.getIns().setSelfName(selfName);
    }

    public void setSelfNativeName(String selfNativeName) {
        this.selfNativeName = selfNativeName;
        AccountShare.getIns().setSelfNativeName(selfNativeName);
    }

    public String getSelfNativeName() {
        if (StringUtil.isStringEmpty(selfNativeName)) {
            selfNativeName = AccountShare.getIns().getSelfNativeName();
        }
        return selfNativeName;
    }

    public String getHeadId() {
        if (StringUtil.isStringEmpty(headId)) {
            headId = LoginShare.getIns().getHeadId();
        }

        return headId;
    }

    public void setHeadId(String h) {
        headId = h;
        LoginShare.getIns().setHeadId(headId);
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean c) {
        connect = c;
    }

    public void setSvnIp(String ip) {
        svnIp = ip;
        AccountShare.getIns().setSvnIp(svnIp);
    }

    public String getSvnIp() {
        if (StringUtil.isStringEmpty(svnIp)) {
            svnIp = AccountShare.getIns().getSvnIp();
        }

        return svnIp;
    }

    public void setSvnPort(String p) {
        svnPort = p;
        AccountShare.getIns().setSvnPort(svnPort);
    }

    public String getSvnPort() {
        if (StringUtil.isStringEmpty(svnPort)) {
            svnPort = AccountShare.getIns().getSvnPort();
        }

        if (StringUtil.isStringEmpty(svnPort)) {
            svnPort = SVN_DEFAULT_PORT;
        }

        return svnPort;
    }

    public String getSvnAccount() {
        if (StringUtil.isStringEmpty(svnAccount)) {
            svnAccount = AccountShare.getIns().getSvnAccount();
        }

        return svnAccount;
    }

    public void setSvnAccount(String sa) {
        svnAccount = sa;
        AccountShare.getIns().setSvnAccount(svnAccount);
    }

    public String getSvnPassword() {
        String sp = AccountShare.getIns().getSvnPassword();
        String svnPassword = null;
        try {
            svnPassword = DataEncryption.unEncrypt(Base64.decode(sp),
                    Resource.CFG_KEY);
        } catch (UnsupportedEncodingException e) {
            Logger.error(ConstantsUtil.APPTAG,
                    "e.toString : " + e.toString());
        }

        return svnPassword;
    }

    public void setSvnPassword(String sp) {
        byte[] byteSp = DataEncryption.encrypt(sp, Resource.CFG_KEY);

        if (null != byteSp && byteSp.length > 0) {
            AccountShare.getIns().setSvnPassword(Base64.encode(byteSp));
        }
    }

    public void setSvnFlag(boolean s) {
        svnFlag = s;
        AccountShare.getIns().setSvnFlag(svnFlag);
    }

    public Boolean isSvnFlag() {
        if (null == svnFlag) {
            svnFlag = AccountShare.getIns().isSvnFlag();
        }

        return svnFlag;
    }

    public int getSensitive() {
        return sensitive;
    }

    public void setSensitive(int s) {
        sensitive = s;
    }

    public Boolean isEncrypt() {
        return encryptFlag;
    }

    public void setEncrypt(boolean e) {
        encryptFlag = e;
    }

    public Boolean isDepartMentNotify() {
        if (null == departMentNotify) {
            departMentNotify = LoginShare.getIns().isDepartMentFlag();
        }

        return departMentNotify;
    }

    public void setDepartMentNotify(Boolean d) {
        departMentNotify = d;
        LoginShare.getIns().setDepartMentFlag(departMentNotify);
    }

    private void setScannedAccount(String account) {
        AccountShare.getIns().setScannedAccount(account);
    }

    public Boolean isScanedGuide() {
        if (null == scanedGuide) {
            scanedGuide = false;

            String[] accounts = AccountShare.getIns().getScanedAccount();
            if (accounts == null) {
                return scanedGuide;
            }

            for (int i = 0; i < accounts.length; i++) {
                if (getAccount().equalsIgnoreCase(accounts[i])) {
                    scanedGuide = true;
                    break;
                }
            }
        }

        return scanedGuide;
    }

    public void setScanedGuide(Boolean sg) {
        scanedGuide = sg;

        setScannedAccount(account);
        AccountShare.getIns().setGuideFlag(scanedGuide);
    }

    public Boolean isCrashExit() {
        if (null == crashExit) {
            crashExit = AccountShare.getIns().isCrashExit();
        }

        return crashExit;
    }

    public void setCrashExit(Boolean c) {
        crashExit = c;
        AccountShare.getIns().setCrashExit(crashExit);
    }

    public boolean isGroupEnable() {
        if (null == constGroupFlag) {
            constGroupFlag = LoginShare.getIns().isConstGroupFlag();
        }

        return constGroupFlag;
    }

    public void setGroupEnable(boolean groupEnable) {
        constGroupFlag = groupEnable;
        LoginShare.getIns().setConstGroupFlag(groupEnable);
    }

    public boolean isLogin3GFlag() {
        if (null == login3GFlag) {
            login3GFlag = LoginShare.getIns().isLogin3GFlag();
        }

        return login3GFlag;
    }

    public void setLogin3GFlag(boolean login3G) {
        login3GFlag = login3G;
        LoginShare.getIns().setLogin3GFlag(login3G);
    }

    public boolean isCTCUsePromptFlag() {
        return ctcUsePromptFlag;
    }

    public void setCTCUsePromptFlag(boolean cTCUsePromptFlag) {
        ctcUsePromptFlag = cTCUsePromptFlag;
    }

    public boolean isCTDUsePromptFlag() {
        return ctdUsePromptFlag;
    }

    public void setCTDUsePromptFlag(boolean cTDUsePromptFlag) {
        ctdUsePromptFlag = cTDUsePromptFlag;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getMaaDeployID() {
        return maaDeployID;
    }

    public void setMaaDeployID(String maaDeployID) {
        this.maaDeployID = maaDeployID;
    }

    public String getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(String audioCodec) {
        this.audioCodec = audioCodec;
    }

    public String getVoiceMailAccessCode() {
        return voipAbility.getVoiceMailAccessCode();
    }

    public void setVoiceMailAccessCode(String voiceMailAccessCode) {
        voipAbility.setVoiceMailAccessCode(voiceMailAccessCode);
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public String getMailBoxNum() {
        return voipAbility.getMailBoxNum();
    }

    public void setMailBoxNum(String mailBoxNum) {
        voipAbility.setMailBoxNum(mailBoxNum);
    }

    public String getVoiceMailPassword() {
        return voipAbility.getMailBoxPassword();
    }

    public void setVoiceMailPassword(String password) {
        voipAbility.setMailBoxPassword(password);
    }

    public int getAnr() {
        return anr;
    }

    public void setAnr(int anr) {
        this.anr = anr;
    }

    public int getAudioDSCP() {
        return audioDSCP;
    }

    public void setAudioDSCP(int audioDSCP) {
        this.audioDSCP = audioDSCP;
    }

    public int getVedioDSCP() {
        return vedioDSCP;
    }

    public void setVedioDSCP(int vedioDSCP) {
        this.vedioDSCP = vedioDSCP;
    }

    public int getDataDSCP() {
        return dataDSCP;
    }

    public void setDataDSCP(int dataDSCP) {
        this.dataDSCP = dataDSCP;
    }

    public int getSipDSCP() {
        return sipDSCP;
    }

    public void setSipDSCP(int sipDSCP) {
        this.sipDSCP = sipDSCP;
    }

    public int getMaaDSCP() {
        return maaDSCP;
    }

    public void setMaaDSCP(int maaDSCP) {
        this.maaDSCP = maaDSCP;
    }

    public int getHoldDuration() {
        return holdDuration;
    }

    public void setHoldDuration(int holdDuration) {
        this.holdDuration = holdDuration;
    }

    public int getFec() {
        return fec;
    }

    public void setFec(int fec) {
        this.fec = fec;
    }

    public int getCallLimitType() {
        return callLimitType;
    }

    public void setCallLimitType(int callLimitType) {
        this.callLimitType = callLimitType;
    }

    public int getControlCFU() {
        return controlCFU;
    }

    public void setControlCFU(int controlCFU) {
        this.controlCFU = controlCFU;
    }

    public boolean isCallHoldAbility() {
        return voipAbility.isCallHoldAbility();
    }

    public void setCallHoldAbility(boolean isCallHoldAbility) {
        voipAbility.setCallHoldAbility(isCallHoldAbility);
    }

    public boolean isCallForwardingNoReplay() {
        return bIsCallForwardingNoReplay;
    }

    public void setCallForwardingNoReplay(boolean isCallForwardingNoReplay) {
        this.bIsCallForwardingNoReplay = isCallForwardingNoReplay;
    }

    public boolean isCallForwardingOffline() {
        return bIsCallForwardingOffline;
    }

    public void setCallForwardingOffline(boolean isCallForwardingOffline) {
        this.bIsCallForwardingOffline = isCallForwardingOffline;
    }

    public boolean isCallForwardingOnBusy() {
        return bIsCallForwardingOnBusy;
    }

    public void setCallForwardingOnBusy(boolean isCallForwardingOnBusy) {
        this.bIsCallForwardingOnBusy = isCallForwardingOnBusy;
    }

    public boolean isCallForwardingUnconditional() {
        return bIsCallForwardingUnconditional;
    }

    public void setCallForwardingUnconditional(
            boolean isCallForwardingUnconditional) {
        this.bIsCallForwardingUnconditional = isCallForwardingUnconditional;
    }

    public String getNewFuncId() {
        return funcId;
    }

    public void setNewFuncId(String newFuncId) {
        this.funcId = newFuncId;
    }

    public String getSnrNumber() {
        return voipAbility.getSnrNumber();
    }

    public void setSnrNumber(String snrNumber) {
        voipAbility.setSnrNumber(snrNumber);
    }

    public boolean isHaveImAndPresenceAbility() {
        return haveImAndPresenceAbility;
    }

    public void setHaveImAndPresenceAbility(boolean haveImAbility) {
        this.haveImAndPresenceAbility = haveImAbility;
    }

    public boolean isVoip3gAbility() {
        return voip3gAbility;
    }

    public void setVoip3gAbility(boolean voip3gAbility) {
        this.voip3gAbility = voip3gAbility;
    }

    public boolean isCallLimit() {
        return callLimit;
    }

    public void setCallLimit(boolean callLimit) {
        this.callLimit = callLimit;
    }

    public String getPasswordCallAccessCode() {
        return passwordCallAccessCode;
    }

    public void setPasswordCallAccessCode(String passwordCallAccessCode) {
        this.passwordCallAccessCode = passwordCallAccessCode;
    }

    public boolean isServerCallLogAbility() {
        return serverCallLogAbility;
    }

    public void setServerCallLogAbility(boolean serverCallLogAbility) {
        this.serverCallLogAbility = serverCallLogAbility;
    }

    public boolean isVideoCallAbility() {
        return voipAbility.isVideoCallAbility();
    }

    public void setVideoCallAbility(boolean isVideoCallAbility) {
        voipAbility.setVideoCallAbility(isVideoCallAbility);
    }

    public String getSnrAccessCode() {
        return voipAbility.getSnrAccessCode();
    }

    public void setSnrAccessCode(String accesscode) {
        voipAbility.setSnrAccessCode(accesscode);
    }

    public boolean isSnrAbility() {
        return voipAbility.isSnrAbility();
    }

    public void setSnrAbility(boolean isSnr) {
        voipAbility.setSnrAbility(isSnr);
    }

    public VoipAbility getVoipAbility() {
        return voipAbility;
    }

    public boolean isCreateNewGroupAbility() {
        return createNewGroupAbility;
    }

    public void setCreateNewGroupAbility(boolean createNewGroupAbility) {
        this.createNewGroupAbility = createNewGroupAbility;
    }

    public boolean isDndAbility() {
        return dndAbility;
    }

    public void setDndAbility(boolean dndAbility) {
        this.dndAbility = dndAbility;
    }

    public boolean isLbsAbility() {
        return lbsAbility;
    }

    public void setLbsAbility(boolean lbsAbility) {
        this.lbsAbility = lbsAbility;
    }

    public void setDynLabelAbility(boolean dynLabelAbility) {
        this.dynLabelAbility = dynLabelAbility;
    }

    public boolean isDynLabelAbility() {
        return dynLabelAbility;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public String getDataConfUserId() {
        return dataConfUserId;
    }

    public void setDataConfUserId(String dataConfUserId) {
        this.dataConfUserId = dataConfUserId;
    }

    public void setCallWait(boolean callWait) {
        this.callWait = callWait;
    }

    public boolean isCallWait() {
        return callWait;
    }

    public boolean isUmAbility() {
        return umAbility;
    }

    public void setUmAbility(boolean umAbility) {
        this.umAbility = umAbility;
    }

    public boolean isMsgLogAbility() {
        return msgLogAbility;
    }

    public void setMsgLogAbility(boolean msgLogAbility) {
        this.msgLogAbility = msgLogAbility;
    }

    public boolean isVideoConfAbility() {
        return videoConfAbility;
    }

    public void setVideoConfAbility(boolean videoConfAbility) {
        this.videoConfAbility = videoConfAbility;
    }

    /**
     * @param autoAnswerTime 毫秒数
     */
    public void setAutoAnswerTime(long autoAnswerTime) {
        this.autoAnswerTime = autoAnswerTime;
        AccountShare.getIns().setAutoAnswerTime(autoAnswerTime);
    }

    /**
     * @return 自动接听等待毫秒数, 1000～60*1000ms为自动接听等待时间，其他值表示不自动接听
     */
    public long getAutoAnswerTime() {
        if (autoAnswerTime == -1) {
            autoAnswerTime = AccountShare.getIns().getAutoAnswerTime();
        }
        return autoAnswerTime;
    }

    public void setHelp(String help) {
        if (help == null) {
            return;
        }
        this.help = help;
        AccountShare.getIns().setHelp(help);
    }

    public String getHelp() {
        if (StringUtil.isStringEmpty(help)) {
            help = AccountShare.getIns().getHelp();
        }
        return help;
    }

    public void setConfUpdate(boolean confUpdate) {
        this.confUpdate = confUpdate;
    }

    public boolean isConfUpdate() {
        return confUpdate;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setNotesMail(String notesMail) {
        this.notesMail = notesMail;
    }

    public String getNotesMail() {
        return notesMail;
    }

    public void setFaxList(String faxList) {
        this.faxList = faxList;
    }

    public String getFaxList() {
        return faxList;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setAssistantList(String assistantList) {
        this.assistantList = assistantList;
    }

    public String getAssistantList() {
        return assistantList;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setVoipList(String voipList) {
        this.voipList = voipList;
    }

    public String getVoipList() {
        return voipList;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRoom() {
        return room;
    }

    public void setInterPhoneList(String interPhoneList) {
        this.interPhoneList = interPhoneList;

    }

    public String getInterPhoneList() {
        return interPhoneList;
    }

    public void setDeptDescEnglish(String deptDescEnglish) {
        this.deptDescEnglish = deptDescEnglish;
    }

    public String getDeptDescEnglish() {
        return deptDescEnglish;
    }

    public void setMobileList(String mobileList) {
        this.mobileList = mobileList;
    }

    public String getMobileList() {
        return mobileList;
    }

    public String getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(String phoneList) {
        this.phoneList = phoneList;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setVideoCodec(VideoCodec videoCodec) {
        this.videoCodec = videoCodec;
    }

    public VideoCodec getVideoCodec() {
        return videoCodec;
    }

    private PersonalContact getSelfContact() {
        String selfContact = LoginShare.getIns().getSelfConact();

        if (selfContact == null) {
            return new PersonalContact();
        }

        PersonalContact pContact = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;

        try {
            bais = new ByteArrayInputStream(Base64.decode(selfContact));
            ois = new ObjectInputStream(bais);

            pContact = (PersonalContact) ois.readObject();
        } catch (IOException ioe) {
            Logger.debug(ConstantsUtil.APPTAG,
                    "get failed " + ioe.toString());
        } catch (ClassNotFoundException e) {
            Logger.debug(ConstantsUtil.APPTAG, "classNotFound failed "
                    + e.toString());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                    ois = null;
                }

                if (bais != null) {
                    bais.close();
                    bais = null;
                }
            } catch (Exception e) {
                Logger.debug(ConstantsUtil.APPTAG,
                        "close error! " + e.toString());
            }
        }

        return pContact;
    }

    public void saveSelfContact(PersonalContact pContact) {
        if (pContact == null) {
            Logger.debug(ConstantsUtil.APPTAG, "self contact is null!");
            return;
        }

        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(pContact);

            LoginShare.getIns().setSelfContact(
                    Base64.encode(baos.toByteArray()));
        } catch (IOException e) {
            Logger.debug(ConstantsUtil.APPTAG, "save failed");
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (Exception e) {
                Logger.debug(ConstantsUtil.APPTAG,
                        "close error! " + e.toString());
            }
        }
    }

    public PersonalContact getSelf() {
        if (self == null) {
            self = getSelfContact();
            if (self == null) {
                self = new PersonalContact();
            }
        }

        return self;
    }

    //设置与获取帐号的登录时的状态
    public int getLoginStatus() {
        return AccountShare.getIns().getLoginStatus();
    }

    public void setLoginStatus(int status){
        AccountShare.getIns().setLoginStatus(status);
    }
}
