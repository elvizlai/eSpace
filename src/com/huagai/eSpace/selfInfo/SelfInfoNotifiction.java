package com.huagai.eSpace.selfInfo;

import com.huagai.eSpace.common.LocalBroadcast;
import com.huagai.eSpace.common.LocalBroadcast.LocalBroadcastReceiver;
import com.huagai.eSpace.common.LocalBroadcast.ReceiveData;
import com.huawei.common.CustomBroadcastConst;
import com.huawei.common.ResponseCodeHandler;
import com.huawei.data.BaseResponseData;

/**
 * Created by huagai on 14-3-6.
 */


public class SelfInfoNotifiction {

    private static SelfInfoNotifiction ins = new SelfInfoNotifiction();

    private selfStatusChange statusChange;

    public static SelfInfoNotifiction getIns() {
        return ins;
    }

    /**
     * 处理状态设置成功的响应接口 *
     */
    public interface selfStatusChange {
        /**
         * 处理状态设置成功的响应 *
         */
        public void handSelfStatusChange();
    }

    private SelfInfoNotifiction() {
    }

    private LocalBroadcastReceiver receiver = new LocalBroadcastReceiver() {
        @Override
        public void onReceive(ReceiveData d) {
            if (statusChange != null) {
                BaseResponseData responseData = d.data;
                if (null != responseData
                        && ResponseCodeHandler.ResponseCode.REQUEST_SUCCESS.equals(responseData
                        .getStatus())) {
                    statusChange.handSelfStatusChange();
                }
            }
        }
    };

    public void registerSelfStatus(selfStatusChange statusChange) {
        this.statusChange = statusChange;

        LocalBroadcast.getIns().registerBroadcast(receiver,
                new String[]{CustomBroadcastConst.ACTION_SET_STATUS_RESPONSE});
    }

    public void unRegisterSelfStatus() {
        LocalBroadcast.getIns().unRegisterBroadcast(receiver,
                new String[]{CustomBroadcastConst.ACTION_SET_STATUS_RESPONSE});
        this.statusChange = null;
    }

}
