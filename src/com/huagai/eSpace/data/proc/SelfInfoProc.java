package com.huagai.eSpace.data.proc;

import android.content.Intent;
import com.huagai.eSpace.common.LocalBroadcast.LocalBroadcastProc;
import com.huagai.eSpace.common.LocalBroadcast.ReceiveData;
import com.huagai.eSpace.selfInfo.SelfInfoUtil;
import com.huawei.common.CustomBroadcastConst;
import com.huawei.common.Resource;
import com.huawei.data.BaseResponseData;


public class SelfInfoProc implements LocalBroadcastProc {
    @Override
    public boolean onProc(Intent intent, ReceiveData rd) {
        String action = intent.getAction();
        rd.action = action;

        if (CustomBroadcastConst.ACTION_SET_STATUS_RESPONSE.equals(action)) {
            return onSetStatus(intent, rd);
        }

        return true;
    }

    private boolean onSetStatus(Intent intent, ReceiveData rd) {
        rd.result = intent.getIntExtra(Resource.SERVICE_RESPONSE_RESULT,
                Resource.REQUEST_OK);
        rd.data = (BaseResponseData) intent
                .getSerializableExtra(Resource.SERVICE_RESPONSE_DATA);

        return SelfInfoUtil.getIns().onStatusRespProc(rd);
    }
}
