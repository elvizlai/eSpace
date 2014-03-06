package com.huagai.eSpace.common;

import com.huagai.eSpace.R;
import com.huagai.eSpace.util.ConstantsUtil;
import com.huawei.common.ResponseCodeHandler;
import com.huawei.ecs.mtk.log.Logger;
import com.huawei.utils.StringUtil;


/**
 * 响应错误码处理
 */
public final class ResErrorCodeForStringHandler {
    private static ResErrorCodeForStringHandler ins = new ResErrorCodeForStringHandler();

    public static synchronized ResErrorCodeForStringHandler getIns() {
        if (null == ins) {
            ins = new ResErrorCodeForStringHandler();
        }

        return ins;
    }

    public String handleError(ResponseCodeHandler.ResponseCode errorCode, String desc) {
        Logger.debug(ConstantsUtil.APPTAG, "errorCode : " + errorCode + " desc : " + desc);

        if (null == errorCode) {
            return desc;
        }

        switch (errorCode) {
//            case COMMON_ERROR:
//            case DISABLED_ACCOUNT:
//                //处理错误码：-1 、-3
//                handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_1));
//                break;
//            case SESSION_OVERDUE:
//                //处理错误码：-2
//                break;
            case TERMINAL_REQUEST_ERROR:
                //处理错误码：-4
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_4));

            case SERVER_INTERFACE_ERROR:
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_5));

            case BE_KICKED_OUT:
                //处理错误码：-6  返回登录界面
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_6));
            case BAD_PAGENUMBER:
                //处理错误码：-7
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_7));
            case NO_MSG_SERVER:
                //处理错误码：-8
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_8));
            case SESSION_TIMEOUT:
                //处理错误码：-9  返回登录界面
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_9));
            case NO_MOBILE_PERMISSIONS:
                //处理错误码：-10
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_10));
            case INCORRECT_DEVICEID:
                //处理错误码：-11
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_11));

            /*****************以下错误描述MAA有返回，提示用返回的，如果没有返回则用默认的****************/

            case JOINCONF_FAILED:
                //处理错误码：-20   您请求的号码无法接通    / 会议已经结束或会议尚未召开   / 入会失败
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_20));
            case CONF_ID_NOT_EXIST:
                //处理错误码：-30   会议不存
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_30));
            case INVITE_ATTENDEE_FAILED:
                //处理错误码：-31   与会人邀请失败
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_31));
            case ATTENDEE_NOT_IN_CONF:
                //处理错误码：-35    与会人不在会议中
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_35));
            case NO_NEED_TO_MODIFY:
                //处理错误码：-43   无需修改话语权
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_43));
            case SERVER_FORWARD:
                //处理错误码：-50   MAA地址错误
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_50));
            default:
                return handleCode(desc, ApplictionHandle.getContext().getString(R.string.module_error_1));
        }
    }

    private String handleCode(String desc, String msg) {
        if (!StringUtil.isStringEmpty(desc)) {
            return desc;
        } else {
            return msg;
        }

    }
}
