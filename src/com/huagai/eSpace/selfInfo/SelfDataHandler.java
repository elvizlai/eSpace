package com.huagai.eSpace.selfInfo;

/**
 * Created by huagai on 14-3-6.
 */

/**
 * 个人信息接口
 */
public class SelfDataHandler {
    private SelfData data;
    private static SelfDataHandler ins = new SelfDataHandler();


    public static SelfDataHandler getIns() {
        return ins;
    }

    public SelfData getSelfData() {
        if (null == data) {
            data = new SelfData();
        }
        return data;
    }

    public void clear() {
        data = null;
    }

}
