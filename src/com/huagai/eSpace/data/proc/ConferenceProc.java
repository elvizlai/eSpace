package com.huagai.eSpace.data.proc;

import android.content.Intent;
import com.huagai.eSpace.common.LocalBroadcast.LocalBroadcastProc;
import com.huagai.eSpace.common.LocalBroadcast.ReceiveData;
import com.huagai.eSpace.conference.ConferenceDataResp;
import com.huawei.common.CustomBroadcastConst;
import com.huawei.common.Resource;
import com.huawei.data.CtcMemberEntity;

import java.util.ArrayList;

public class ConferenceProc implements LocalBroadcastProc {
    @Override
    public boolean onProc(Intent intent, ReceiveData rd) {
        String action = intent.getAction();
        rd.action = action;

        if (CustomBroadcastConst.CTC_UPDATE_MEMBERSTATUS_PUSH.equals(action)) {
            return onUpdateMemberStatus(intent, rd);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private boolean onUpdateMemberStatus(Intent intent, ReceiveData rd) {
        rd.result = intent.getIntExtra(Resource.SERVICE_RESPONSE_RESULT,
                Resource.REQUEST_OK);

        ConferenceDataResp dataResp = new ConferenceDataResp(null);

        ArrayList<CtcMemberEntity> list = (ArrayList<CtcMemberEntity>) intent
                .getSerializableExtra(Resource.SERVICE_RESPONSE_DATA);

        dataResp.setConfMembers(list);

        rd.data = dataResp;

        return true;
    }
}
