package com.huagai.eSpace.selfInfo;

import com.huagai.eSpace.common.LocalBroadcast.ReceiveData;
import com.huagai.eSpace.sdk.EspaceSDK;
import com.huagai.eSpace.selfInfo.SelfInfoNotifiction.selfStatusChange;
import com.huagai.eSpace.util.ConstantsUtil;
import com.huagai.eSpace.util.ContactCache;
import com.huawei.common.PersonalContact;
import com.huawei.common.Resource;
import com.huawei.common.ResponseCodeHandler;
import com.huawei.data.ExecuteResult;
import com.huawei.ecs.mtk.log.Logger;
import com.huawei.service.ServiceProxy;


/**
 * 个人设置接口
 */
public final class SelfInfoUtil {
    public final static String FIRSTLOGINTIME = "00000000000000";

    private static SelfInfoUtil ins = new SelfInfoUtil();

    private int settingStatus = PersonalContact.DEF;
    private int savedStatus = PersonalContact.DEF;

    private int toVoipStatusId;

    private PersonalContact self;

    private SelfInfoUtil() {
    }

    public static SelfInfoUtil getIns() {
        return ins;
    }

    /**
     * 获取用户自己的状态
     *
     * @return 用户状态
     */
    public int getStatus() {
        return SelfDataHandler.getIns().getSelfData().getStatus();
    }

    private void saveStatusToSelfData(int status) {
        Logger.debug(ConstantsUtil.APPTAG, "status : " + status);

        SelfDataHandler.getIns().getSelfData().setStatus(status);
    }

    /**
     * 设置状态
     *
     * @param status <p>PersonalContact.ON_LINE;
     *               <p>PersonalContact.BUSY;
     *               <p>PersonalContact.AWAY
     * @return
     */
    public ExecuteResult setStatus(int status) {
        if (status == PersonalContact.DEF) {
            return null;
        }

        // 低内存的时候可能会把状态设置回默认值away，然后在恢复的时候设置该状态到服务器，所以要做下拦截。
        if (status == PersonalContact.AWAY) {
            status = PersonalContact.ON_LINE;
        }

        settingStatus = status;

        ServiceProxy service = EspaceSDK.getIns().getServiceProxy();

        if (service != null) {
            ExecuteResult result = service.setStatus(status);

            return result;
        }

        return null;
    }

    /**
     * 设置临时状态
     * <p/>
     * // * @param statu
     */
    public void setToVoipStatus() {
        if (SelfDataHandler.getIns().getSelfData().getStatus() == PersonalContact.UNINTERRUPTABLE) {
            return;
        }

        if (savedStatus == PersonalContact.DEF) {
            savedStatus = SelfDataHandler.getIns().getSelfData().getStatus();
        }

        ExecuteResult result = setStatus(PersonalContact.BUSY);

        if (result != null) {
            toVoipStatusId = result.getID();
        }
    }

    public void setToOfflineStatus() {
        if (savedStatus == PersonalContact.DEF) {
            savedStatus = SelfDataHandler.getIns().getSelfData().getStatus();
        }

        setToLogoutStatus();
    }

    public void setToLoginStatus() {
        Logger.debug(ConstantsUtil.APPTAG, "LoginStatus");
        // 因为是登录操作后，默认是在线状态，所以不需要发请求，本地存储即可
        saveStatusToSelfData(PersonalContact.ON_LINE);
    }

    public void setToLogoutStatus() {
        Logger.debug(ConstantsUtil.APPTAG, "LogoutStatus");
        // 因为是注销操作后，要保证本地显示的是离线状态，所以只需要在本地存就可以
        saveStatusToSelfData(PersonalContact.AWAY);
    }

    /**
     * 恢复状态设置
     */
    public void restoreSavedStatus() {
        if (savedStatus != PersonalContact.DEF) {
            setStatus(savedStatus);
            // TODO 此处可能有问题，如果恢复状态没有成功怎么办？ 要不要继续恢复
            savedStatus = PersonalContact.DEF;
        }
    }

    public boolean onStatusRespProc(ReceiveData rd) {
        Logger.debug(ConstantsUtil.APPTAG, "settingStatus : "
                + settingStatus);

        int id = rd.data.getID();

        if (settingStatus == PersonalContact.DEF) {
            return false;
        }

        if (rd.result == Resource.REQUEST_OK
                && rd.data.getStatus() == ResponseCodeHandler.ResponseCode.REQUEST_SUCCESS) {
            saveStatusToSelfData(settingStatus);

            if (toVoipStatusId != id && savedStatus != PersonalContact.DEF) {
                savedStatus = settingStatus;
            }
        }

        return true;
    }

    /**
     * 清空该账号所有data数据
     */
    public void clear() {
        SelfDataHandler.getIns().clear();
    }

    /**
     * 保存自己
     */
    public void saveSelf(SelfData data) {
        if (null == self) {
            self = new PersonalContact();
        }
        self.setName(data.getSelfName());
        self.setNativeName(data.getSelfNativeName());
        self.setEspaceNumber(data.getAccount());
        self.setHead(data.getHeadId());
        self.setPhone(data.getPhone());
        self.setMobile(data.getMobile());
        self.setBinderNumber(data.getBindNum());
        self.setShortphone(data.getShortNum());
        self.setVoipNumber(data.getVoipNum());
        self.setVoipNumber2(data.getVoipNum2());
        self.setOfficephone(data.getOfficePhone());
        self.setSignature(data.getSignatue());
        self.setDepartmentName(data.getDeptment());
        self.setDeptDesc(data.getDeptDesc());
        self.setSex(data.getSex());
        self.setEmail(data.getMailAddr());
        self.setAddress(data.getAddress());
        self.setAllInfo(true);
        ContactCache.getIns().add(self);
    }

    public static void registerSelfStatus(selfStatusChange statusChange) {
        SelfInfoNotifiction.getIns().registerSelfStatus(statusChange);
    }

    public static void unRegisterSelfStatus() {
        SelfInfoNotifiction.getIns().unRegisterSelfStatus();
    }

}
