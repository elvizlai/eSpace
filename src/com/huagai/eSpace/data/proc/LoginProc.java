package com.huagai.eSpace.data.proc;

import android.content.Intent;
import com.huagai.eSpace.common.ApplictionHandle;
import com.huagai.eSpace.common.LocalBroadcast.LocalBroadcastProc;
import com.huagai.eSpace.common.LocalBroadcast.ReceiveData;
import com.huagai.eSpace.sdk.EspaceSDK;
import com.huagai.eSpace.selfInfo.SelfData;
import com.huagai.eSpace.selfInfo.SelfDataHandler;
import com.huagai.eSpace.selfInfo.SelfInfoUtil;
import com.huagai.eSpace.sharedprefer.LoginShare;
import com.huagai.eSpace.util.LocalStringUtil;
import com.huawei.common.CustomBroadcastConst;
import com.huawei.common.Resource;
import com.huawei.common.ResponseCodeHandler;
import com.huawei.data.BaseResponseData;
import com.huawei.data.CheckVersionResp;
import com.huawei.data.LoginResp;
import com.huawei.service.ServiceProxy;
import com.huawei.voip.CallManager;
import com.huawei.voip.data.VideoCaps;

public class LoginProc implements LocalBroadcastProc {
    private static final String VEDIODSCP = "espace_vediodscp";
    private static final int INIT_VALUE = 0;

    @Override
    public boolean onProc(Intent intent, ReceiveData rd) {
        String action = intent.getAction();
        rd.action = action;

        if (CustomBroadcastConst.ACTION_CONNECT_TO_SERVER.equals(action)) {
            return onConnectServer(intent, rd);
        } else if (CustomBroadcastConst.BACK_TO_LOGIN_VIEW.equals(action)) {
            return onBackLoginView(intent, rd);
        } else if (CustomBroadcastConst.ACTION_CHECKVERSION_RESPONSE.equals(action)) {
            return onCheckVersion(intent, rd);
        } else if (CustomBroadcastConst.ACTION_LOGIN_RESPONSE.equals(action)) {
            return onLogin(intent, rd);
        }

        return true;
    }

    private boolean onConnectServer(Intent intent, ReceiveData rd) {
        boolean isConnect = intent.getBooleanExtra(Resource.SERVICE_RESPONSE_DATA,
                false);

        rd.result = isConnect ? 1 : 0;

        return true;
    }

    private boolean onBackLoginView(Intent intent, ReceiveData rd) {
//        EspaceApp.getIns().removeStickyBroadcast(intent);

        ResponseCodeHandler.ResponseCode code = (ResponseCodeHandler.ResponseCode) intent.getSerializableExtra("error");

        if (code != null) {
            rd.result = code.value();
        }

        return true;
    }

    private boolean onCheckVersion(Intent intent, ReceiveData rd) {
        rd.result = intent.getIntExtra(Resource.SERVICE_RESPONSE_RESULT,
                Resource.REQUEST_OK);
        rd.data = (BaseResponseData) intent
                .getSerializableExtra(Resource.SERVICE_RESPONSE_DATA);

        if ((rd.result == Resource.REQUEST_OK)
                && ResponseCodeHandler.ResponseCode.REQUEST_SUCCESS.equals(rd.data.getStatus())
                && rd.data instanceof CheckVersionResp) {
            CheckVersionResp resp = (CheckVersionResp) rd.data;

            SelfData data = SelfDataHandler.getIns().getSelfData();

            int iValue = resp.getUserDomain();
            data.setVoipDomain(1 == iValue ? true : false);

//            iValue = resp.getIsCallForward();
//            data.setCallForward(1 == iValue ? true : false);

            iValue = resp.getAllowPhoneCall();
            data.setPhoneCall(1 == iValue ? true : false);

            iValue = resp.getIsTransPhone();
            data.setTransPhone(1 == iValue ? true : false);

            iValue = resp.getEncrypt();
            data.setEncrypt(1 == iValue ? true : false);

            iValue = resp.getSensitive();
            data.setSensitive(iValue);

            iValue = resp.getTransPhoneNum();
            data.setTransPhoneNum(iValue);

            data.setCtc(resp.getIsCTC());
            data.setCtd(resp.getIsCTD());
            data.setVoip(resp.getIsVoip());

            data.setImServerType(LocalStringUtil.replaceNull(resp
                    .getServerType()));

            if ((resp.getLogin3G() == 0)
                    && LoginShare.UC_2_0.equalsIgnoreCase(data
                    .getImServerType())) {
                data.setLogin3GFlag(false);
            } else {
                data.setLogin3GFlag(true);
            }
        }

        return true;
    }

    private boolean onLogin(Intent intent, ReceiveData rd) {
        ApplictionHandle.getContext().removeStickyBroadcast(intent);

        rd.result = intent.getIntExtra(Resource.SERVICE_RESPONSE_RESULT,
                Resource.REQUEST_OK);

        rd.data = (BaseResponseData) intent
                .getSerializableExtra(Resource.SERVICE_RESPONSE_DATA);

        if ((rd.result == Resource.REQUEST_OK)
                && ResponseCodeHandler.ResponseCode.REQUEST_SUCCESS.equals(rd.data.getStatus())
                && rd.data instanceof LoginResp) {
            LoginResp resp = (LoginResp) rd.data;

            SelfData data = SelfDataHandler.getIns().getSelfData();

            //是否具有im和presence权限
            data.setHaveImAndPresenceAbility(resp.isIMAbility()
                    && resp.isPresenceAbility());

            //UserName放到登陆成功打开数据库后保存
            data.setSelfName(LocalStringUtil.replaceNull(resp.getName()));
            data.setSelfNativeName(LocalStringUtil.replaceNull(resp.getNativeName()));
            data.setSignatue(LocalStringUtil.replaceNull(resp.getSignature()));
            data.setHeadId(LocalStringUtil.replaceNull(resp.getHead()));
            data.setMailAddr(resp.getMailaddress());
            data.setSex(resp.getSex());
            data.setDeptment(LocalStringUtil.replaceNull(resp.getDeptment()));
            data.setDeptDesc(resp.getDeptDesc());
            data.setAddress(LocalStringUtil.replaceNull(resp.getAddress()));
            data.setHomePage(LocalStringUtil.replaceNull(resp.getHomePage()));
            data.setZipCode(LocalStringUtil.replaceNull(resp.getPostalcode()));
            data.setPost(LocalStringUtil.replaceNull(resp.getPosition()));
            data.setFax(LocalStringUtil.replaceNull(resp.getFax()));
            data.setVideoConfAbility(resp.isVideoConferenceAbility());

            data.setMobile(resp.getMobile());
            data.setPhone(resp.getPhone());
            data.setShortNum(resp.getShortPhone());
            data.setBindNum(resp.getBindnumber());
            data.setOfficePhone(resp.getOfficePhone());
            data.setVoipNum(resp.getVoipNumber());
            data.setVoipNum2(resp.getVoipNumber2());
            data.setLocalPhone(LocalStringUtil.replaceNull(resp.getCallno()));

            data.setSyncmode(Integer.valueOf(resp.getSyncMode()));
            data.setEntvlevel(String.valueOf(resp.getEntvlevel()));

            data.setCTCUsePromptFlag(resp.isCTCWarning());
            data.setCTDUsePromptFlag(resp.isCTDWarning());

            data.setLocation(resp.getLocation());
            data.setLastLocation(resp.getLastLocation());
            data.setMaaDeployID(resp.getMaaDeployID());
            data.setAudioCodec(resp.getAudioCode());
            data.setVoiceMailAccessCode(resp.getVoiceMailAccessCode());
            data.setVoiceMailFlag(resp.isVoiceMailAbility());
            data.setMailBoxNum(resp.getMailBoxNum());
            data.setVideoCallAbility(resp.isVideoCallAbility());
            data.setCreateNewGroupAbility(resp.isCreateNewGroupAbility());
            data.setDndAbility(resp.isDNDAbility());
            data.setLbsAbility(resp.isLBSAbility());
            data.setMsgLogAbility(resp.isMsgLogAbility());
            data.setDigitMap(resp.getDigitMap());

            //统一消息相关属性
            data.setUmAbility(resp.isUMAbility());
            data.setUmServerHttp(resp.getUmServerHttp());
            data.setUmServerHttps(resp.getUmServerHttps());
            data.setBackupUmServerHttp(resp.getBackupUmServerHttp());
            data.setBackupUmServerHttps(resp.getBackupUmServerHttps());

            if (resp.isCtcFlag()) {
                data.setCtc(resp.isCtcFlag());
            }
            if (resp.isCtdFlag()) {
                data.setCtd(resp.isCtdFlag());
            }
            if (resp.isVoipFlag()) {
                data.setVoip(resp.isVoipFlag());
            }
            data.setVoip3gAbility(resp.isVoip3GAbility());

            data.setMediax(resp.isMediaX());
            data.setBulletin(resp.isNews());
            data.setCountryCode(resp.isConfigCountryCode());
            data.setMatchLocalPower(resp.isMatchMobile());
            data.setDepartMentNotify(resp.isDepartmentNoticeAbility());

            data.setDataConf(resp.isPhoneDataConferenceAbility());
            data.setBookConf(resp.isAllowBookConference());
            data.setReferTo(resp.isReferAbility());
//            data.setGroupEnable(resp.isConstGroupAbility());
            data.setGroupEnable(false);

            if (resp.getMaxMessageSize() > 0) {
                data.setMaxMsgSize(resp.getMaxMessageSize());
            }

            if (resp.getMaxPictureSIze() > 0) {
                data.setMaxPictureSize(resp.getMaxPictureSIze());
            }

            data.setCallHoldAbility(resp.isCallHoldAbility());

            data.setCallForward(resp.isPresenceForwardAbility());

            data.setCallForwardingNoReplay(resp.isCallForwardingNoReplay());
            data.setCallForwardingOffline(resp.isCallForwardingOffline());
            data.setCallForwardingOnBusy(resp.isCallForwardingOnBusy());
            data.setCallForwardingUnconditional(resp.isCallForwardingUnconditional());
            data.setControlCFU(resp.getControlCFU());

            //密码呼叫限制功能位
            data.setCallLimitType(resp.getCallLimitType());
            data.setCallLimit(resp.isCallLimit());
            data.setPasswordCallAccessCode(resp.getPasswordCallAccessCode());

            data.setServerCallLogAbility(resp.isServerCallLogAbility());

            //2012-2-28新增功能位
            data.setDynLabelAbility(resp.isDynLabelAbility());

            //2012-2-28新增字段
            data.setConfUpdate(resp.isConfUpdate());
            data.setStaffNo(resp.getStaffNo());
            data.setNotesMail(resp.getNotesMail());
            data.setFaxList(resp.getFaxList());
            data.setOtherInfo(resp.getOtherInfo());
            data.setContact(resp.getContact());
            data.setAssistantList(resp.getAssistantList());
            data.setDisplayName(resp.getDisplayName());
            data.setForeignName(resp.getForeignName());
            data.setVoipList(resp.getVoipList());
            data.setRoom(resp.getRoom());
            data.setInterPhoneList(resp.getInterPhoneList());
            data.setDeptDescEnglish(resp.getDeptDescEnglish());
            data.setMobileList(resp.getMobileList());
            data.setHomePhone(resp.getHomePhone());
            data.setPhoneList(resp.getPhoneList());

            //视频参数
            data.setVideoCodec(resp.getVideoCodec());

            data.setSnrAccessCode(resp.getSNRAccessCode());
            data.setSnrNumber(resp.getSNRNumber());
            data.setSnrAbility(resp.isSNRAbility());
            data.setMailBoxNum(resp.getMailBoxNum());
            data.setVoiceMailPassword(resp.getVoiceMailBoxPassword());

            if (resp.getUserID() != 0) {
                data.setDataConfUserId(String.valueOf(resp.getUserID()));
            }

            SelfInfoUtil.getIns().saveSelf(data);

            // VedioDSCP
            data.setVedioDSCP(LoginShare.getIns().getLoginShare().
                    getInt(VEDIODSCP, INIT_VALUE));

            // 登陆成功后设置视频参数 否则不能发送视频请求
            ServiceProxy serviceProxy = EspaceSDK.getIns().getServiceProxy();
            CallManager callManager = serviceProxy.getCallManager();
            if (callManager != null) {
                callManager.setVideoCaps(new VideoCaps());
            }
        }

        return true;
    }

}
