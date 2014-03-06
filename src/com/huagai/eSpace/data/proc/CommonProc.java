package com.huagai.eSpace.data.proc;

import android.content.Intent;
import com.huagai.eSpace.common.LocalBroadcast.LocalBroadcastProc;
import com.huagai.eSpace.common.LocalBroadcast.ReceiveData;
import com.huagai.eSpace.selfInfo.SelfData;
import com.huagai.eSpace.selfInfo.SelfDataHandler;
import com.huagai.eSpace.selfInfo.SelfInfoUtil;
import com.huawei.common.CustomBroadcastConst;
import com.huawei.common.Resource;
import com.huawei.common.ResponseCodeHandler;
import com.huawei.data.BaseResponseData;
import com.huawei.data.ConfigChangeNotifyData;
import com.huawei.service.HeartBeatConfig;
import com.huawei.utils.StringUtil;


public class CommonProc implements LocalBroadcastProc {
    @Override
    public boolean onProc(Intent intent, ReceiveData rd) {
        String action = intent.getAction();
        rd.action = action;

        if (HeartBeatConfig.ACTION_RECONNECT.equals(action)) {
            return onReconnect(intent, rd);
        } else if (CustomBroadcastConst.ACTION_CONFIG_CHANGE_NOTIFY
                .equals(action)) {
            return onConfigChangeNotify(intent, rd);
        }

        return true;
    }

    private boolean onReconnect(Intent intent, ReceiveData rd) {
        boolean connectStatus = intent.getBooleanExtra("connectStatus",
                false);

        if (connectStatus) {
            rd.result = 1;
            SelfInfoUtil.getIns().setToLoginStatus();
            SelfInfoUtil.getIns().restoreSavedStatus();
        } else {
            rd.result = 0;
            SelfInfoUtil.getIns().setToOfflineStatus();
        }

        // 设置连接状态，并把自己的状态设为离线状态
        SelfDataHandler.getIns().getSelfData().setConnect(connectStatus);

        return true;
    }

    private boolean onConfigChangeNotify(Intent intent, ReceiveData rd) {
        rd.result = intent.getIntExtra(Resource.SERVICE_RESPONSE_RESULT,
                Resource.REQUEST_OK);
        rd.data = (BaseResponseData) intent
                .getSerializableExtra(Resource.SERVICE_RESPONSE_DATA);

        if ((rd.result == Resource.REQUEST_OK)
                && ResponseCodeHandler.ResponseCode.REQUEST_SUCCESS.equals(rd.data.getStatus())
                && rd.data instanceof ConfigChangeNotifyData) {
            ConfigChangeNotifyData confChangeData = (ConfigChangeNotifyData) rd.data;
            //保存相关数据
            SelfData data = SelfDataHandler.getIns().getSelfData();

            int funcVar = confChangeData.getCallLimitType();

            if (funcVar != -1) {
                data.setCallLimitType(funcVar);
            }

            funcVar = confChangeData.getControlCFU();
            if (funcVar != -1) {
                data.setControlCFU(funcVar);
            }

            String snrNum = confChangeData.getSNRNumber();
            if (!StringUtil.isStringEmpty(snrNum)) {
                data.setSnrNumber(snrNum);
            }

            data.setCallHoldAbility(confChangeData.isCallHoldAbility());
            data.setCallLimit(confChangeData.isCallLimit());
            data.setCallWait(confChangeData.isCallWait());

            data.setCallForwardingNoReplay(confChangeData
                    .isCallForwardingNoReplay());
            data.setCallForwardingOffline(confChangeData
                    .isCallForwardingOffline());
            data.setCallForwardingOnBusy(confChangeData
                    .isCallForwardingOnBusy());
            data.setCallForwardingUnconditional(confChangeData
                    .isCallForwardingUnconditional());

            data.setHaveImAndPresenceAbility(confChangeData.isIMAbility()
                    && confChangeData.isPresenceAbility());
        }

        return true;
    }
}
