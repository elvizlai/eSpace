package com.huagai.eSpace.common;

/**
 * Created by huagai on 14-3-6.
 */

import android.app.Application;
import android.content.Context;
import com.huagai.eSpace.util.ConstantsUtil;
import com.huawei.ecs.mtk.log.Logger;

import java.lang.reflect.Method;

/**
 * 获取一个context句柄
 */
public final class ApplictionHandle {
    private static Context context;
    private static Application app;

    /**
     * 获取当前 Appliction
     *
     * @return Appliction
     */
    public synchronized static Application getAppliction() {
        try {
            if (app != null) {
                return app;
            } else {
                // TODO 如果context不存在 就获取安卓系统的context
                Class<?> activityThreadClass = Class
                        .forName("android.app.ActivityThread");
                Method method = activityThreadClass
                        .getMethod("currentApplication");
                app = (Application) method.invoke(null, (Object[]) null);
            }
        } catch (Exception e) {
            Logger.debug(ConstantsUtil.APPTAG, "getAppliction exception:" + e.getMessage());
        }

        return app;
    }

    /**
     * 获取当前 Appliction 的 Context
     *
     * @return Context
     */
    public synchronized static Context getContext() {
        try {
            // 首先后去ISV给我们设定的context
            if (context != null) {
                return context;
            } else {
                context = getAppliction().getApplicationContext();
            }
        } catch (Exception e) {
            Logger.debug(ConstantsUtil.APPTAG, "getContext exception:" + e.getMessage());
        }

        return context;
    }

}
