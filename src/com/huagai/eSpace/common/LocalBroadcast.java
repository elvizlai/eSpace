package com.huagai.eSpace.common;

import android.content.Intent;
import com.huawei.common.CustomBroadcastConst;
import com.huawei.data.BaseResponseData;
import com.huawei.service.HeartBeatConfig;

import java.util.*;


/**
 * SDK交互的广播
 */
final public class LocalBroadcast {
    private final static LocalBroadcast INS = new LocalBroadcast();

    private final Object BROADCASTLOCK = new Object();

    private static final Map<String, LinkedList<LocalBroadcastReceiver>> BROADCASTS
            = new HashMap<String, LinkedList<LocalBroadcastReceiver>>();

    private LocalBroadcastProc proc;

    static {
        BROADCASTS.put(CustomBroadcastConst.BACK_TO_LOGIN_VIEW,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.ACTION_LOGINOUT_SUCCESS,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.ACTION_CHECKVERSION_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.UPDATE_CONTACT_VIEW,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.ACTION_LOGIN_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.ACTION_CONNECT_TO_SERVER,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.ACTION_SEARCE_CONTACT_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.ACTION_SEND_MESSAGE_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.ACTION_SET_STATUS_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_CREATE_CONFERENCE_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_STOP_CONFERENCE_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_ADD_MEMBER_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_DEL_MEMBER_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_MODIFY_TALKMODE_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_GET_CONFLIST_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_UPDATE_CONF_STATUS_PUSH,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_CONF_NOTIFY_PUSH,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_UPDATE_MEMBERSTATUS_PUSH,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_GET_MEMBER_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_GET_CONF_INFO_RESPONSE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_JOIN_CONF,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.ACTION_VIEW_HEADPHOTO,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.ACTION_SET_HEADPHOTO,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_REPORT_TERMINAL,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_UPGRADE_CONF,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_PUSH_CONFINFO,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.CTC_PUSH_DATACONF_INFO,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(CustomBroadcastConst.ACTION_RECEIVE_MESSAGE,
                new LinkedList<LocalBroadcastReceiver>());
        BROADCASTS.put(HeartBeatConfig.ACTION_RECONNECT,
                new LinkedList<LocalBroadcastReceiver>());

    }

    private LocalBroadcast() {
    }

    public static LocalBroadcast getIns() {
        return INS;
    }

    public Set<String> getAllBroadcast() {
        return BROADCASTS.keySet();
    }

    public void registerBroadcastProc(LocalBroadcastProc p) {
        proc = p;
    }

    public boolean registerBroadcast(LocalBroadcastReceiver receiver,
                                     String[] actions) {
        if (receiver == null || actions == null) {
            return false;
        }

        List<LocalBroadcastReceiver> list;

        synchronized (BROADCASTLOCK) {
            for (String action : actions) {
                list = BROADCASTS.get(action);

                if (list == null) {
                    continue;
                }

                list.add(receiver);
            }
        }

        return true;
    }

    public boolean unRegisterBroadcast(LocalBroadcastReceiver receiver,
                                       String[] actions) {
        if (receiver == null || actions == null) {
            return false;
        }

        List<LocalBroadcastReceiver> list;

        synchronized (BROADCASTLOCK) {
            for (String action : actions) {
                list = BROADCASTS.get(action);

                if (list == null) {
                    continue;
                }

                list.remove(receiver);

            }
        }

        return true;
    }

    public void onBroadcastReceive(Intent intent) {
        String action = intent.getAction();
        ReceiveData rd = new ReceiveData();

        boolean procResult = true;

        if (proc != null) {
            procResult = proc.onProc(intent, rd);
        }

        // 如果预处理没有通过，不需要继续处理广播
        if (!procResult) {
            return;
        }

        notifyBroadcast(action, rd);
    }

    public void notifyBroadcast(String action, ReceiveData rd) {
        if (action == null) {
            return;
        }
        List<LocalBroadcastReceiver> receivers = BROADCASTS.get(action);

        if (receivers == null) {
            return;
        }
        synchronized (BROADCASTLOCK) {
            int size = receivers.size();

            for (int i = 0; i < size; i++) {
                receivers.get(i).onReceive(rd);
            }
        }
    }

    public interface LocalBroadcastReceiver {
        void onReceive(ReceiveData d);
    }

    public interface LocalBroadcastProc {
        /**
         * @return true:预处理通过 false:预处理没有通过
         */
        boolean onProc(Intent intent, ReceiveData rd);
    }

    public static class ReceiveData {
        public String action;
        public int result;

        public BaseResponseData data;
    }
}
