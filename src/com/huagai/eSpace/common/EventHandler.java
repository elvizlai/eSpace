package com.huagai.eSpace.common;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


/**
 * 类名称：EventHandler
 */
public class EventHandler extends Handler {
    private static EventHandler ins = new EventHandler();

    public EventHandler() {
        // TODO Auto-generated constructor stub
        super(Looper.getMainLooper());
    }

    public static EventHandler getIns() {
        return ins;
    }

    @Override
    public void handleMessage(Message msg) {
    }

    public void sendMessage(int what, Object object) {
        Message msg = obtainMessage(what, object);
        sendMessage(msg);
    }
}
