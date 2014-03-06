package com.huagai.eSpace.data.proc;

import android.content.Intent;
import com.huagai.eSpace.common.LocalBroadcast.LocalBroadcastProc;
import com.huagai.eSpace.common.LocalBroadcast.ReceiveData;
import com.huagai.eSpace.util.ConstantsUtil;
import com.huawei.common.CustomBroadcastConst;
import com.huawei.common.Resource;
import com.huawei.data.BaseResponseData;
import com.huawei.ecs.mtk.log.Logger;
import com.huawei.service.HeartBeatConfig;

import java.util.HashMap;
import java.util.Map;


final public class DataProc {
    private final static DataProc INS = new DataProc();

    private static final Map<String, LocalBroadcastProc> PROCESSOR =
            new HashMap<String, LocalBroadcastProc>();

    static {

        LoginProc loginProc = new LoginProc();
//        ContactProc    contactProc    = new ContactProc();
        SelfInfoProc selfInfoProc = new SelfInfoProc();
        CommonProc commonProc = new CommonProc();
        ConferenceProc conferenceProc = new ConferenceProc();

        PROCESSOR.put(CustomBroadcastConst.ACTION_CONNECT_TO_SERVER, loginProc);
        PROCESSOR.put(CustomBroadcastConst.BACK_TO_LOGIN_VIEW, loginProc);
        PROCESSOR.put(CustomBroadcastConst.ACTION_CHECKVERSION_RESPONSE,
                loginProc);
        PROCESSOR.put(CustomBroadcastConst.ACTION_LOGIN_RESPONSE, loginProc);
//        PROCESSOR.put(CustomBroadcastConst.UPDATE_CONTACT_VIEW, contactProc);
        PROCESSOR.put(CustomBroadcastConst.ACTION_SET_STATUS_RESPONSE,
                selfInfoProc);
        PROCESSOR.put(CustomBroadcastConst.CTC_UPDATE_MEMBERSTATUS_PUSH,
                conferenceProc);
        PROCESSOR.put(HeartBeatConfig.ACTION_RECONNECT, commonProc);
        PROCESSOR.put(CustomBroadcastConst.ACTION_CONFIG_CHANGE_NOTIFY,
                commonProc);
    }


    private final LocalBroadcastProc proc = new LocalBroadcastProc() {
        @Override
        public boolean onProc(Intent intent, ReceiveData rd) {
            LocalBroadcastProc proc = PROCESSOR.get(intent.getAction());

            if (proc != null) {
                return proc.onProc(intent, rd);
            } else {
                return onCommonProc(intent, rd);
            }
        }
    };

    private DataProc() {
    }

    public static DataProc getIns() {
        return INS;
    }

    public LocalBroadcastProc getProc() {
        return proc;
    }

    private boolean onCommonProc(Intent intent, ReceiveData rd) {
        rd.action = intent.getAction();
        rd.result = intent.getIntExtra(Resource.SERVICE_RESPONSE_RESULT,
                Resource.REQUEST_OK);
        try {
            rd.data = (BaseResponseData) intent
                    .getSerializableExtra(Resource.SERVICE_RESPONSE_DATA);
        } catch (Exception e) {
            Logger.debug(ConstantsUtil.APPTAG, "onCommonProc error ——> " + e.toString());
        }
        return true;
    }
}
