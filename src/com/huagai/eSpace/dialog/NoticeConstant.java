package com.huagai.eSpace.dialog;


import com.huagai.eSpace.R;

public interface NoticeConstant {
    /**
     * 常用对话框
     */
    int DIALOG_COMMON = 4;
    /**
     * 带编辑框的对话框
     */
    int DIALOG_EDITABLE = 1;
    /**
     * 通讯录匹配提示对话框
     */
    int DIALOG_ADDRESSBOOK_MATCH = 2;
    /**
     * 会议不支持分享屏幕提示对话框
     */
    int DIALOG_SINGLE_BUTTON = 3;
    /**
     * 以简单listview形式展现的对话框
     */
    int DIALOG_SIMPLE_LISTVIEW = 5;
    /**
     * 以简单listview形式展现的对话框(含对勾)
     */
    int DIALOG_SIMPLE_LISTVIEW_WITH_GOU = 23;

    /**
     *  带title的可以点击的对话框
     */
//    int DIALOG_TITLE_LISTVIEW = 6;
    /**
     * 普通toast
     */
    int DIALOG_TOAST_COMMON = 9;
    /**
     * toast 正在匹配通讯录
     */
    int DIALOG_PROCESSING = 13;
    /**
     * 设置国家码对话框
     */
//    int DIALOG_SET_COUNTRY_CODE = 10;
    /**
     * 聊天页面的popupWindow
     */
    int DIALOG_POPUPWINDOW_CHAT = 11;
    /**
     * 标题栏的popupWindow
     */
    int DIALOG_POPUPWINDOW_HEAD = 12;
    /**
     * 通话页面的popupWindow
     */
    int DIALOG_POPUPWINDOW_PHOTO = 19;
    /**
     * 视频会议页面的popupWindow
     */
    int DIALOG_POPUPWINDOW_VIDEO_CONFERENCE = 28;
    /**
     * 会议新建页面的对话框
     */
//    int DIALOG_MEETING_CREATE = 15;
    /**
     * 删除联系人的对话框
     */
//    int DIALOG_REMOVE_CONTACTER = 14;
    /**
     * 聊天页面添加好友与会议通知的对话框
     */
//    int DIALOG_CHAT_REMIND = 16;
    /**
     * 搜索页面添加联系人 弹出 选择标签对话框
     */
//    int DIALOG_SELECT_TAG = 17;

    /**
     * 设置系统铃声弹出框
     */
//    int DIALOG_SYSTEM_RING = 20;

    /**
     * CTD使用提示框
     */
    int DIALOG_CTD_PROMPT = 21;

    /**
     * 显示联系人所有号码弹出框
     */
//    int DIALOG_CONTACTER_NUMBER = 22;

    /**
     * 号码选择对话框（默认含对勾,  不含自定义选项）
     */
    int DIALOG_NUMBER_SELECT = 24;

    /**
     * 号码选择对话框（不含对勾,  不含自定义选项）
     */
    int DIALOG_NUMBER_SELECT_NO_GOU = 25;

    /**
     * 号码选择对话框（含对勾,  含自定义选项）
     */
    int DIALOG_NUMBER_SELECT_WITH_SELF_ITEM = 26;
    /**
     * 号码选择对话框（含标题栏, 含对勾, 含自定义选项）
     */
    int DIALOG_NUMBER_SELECT_WITH_HEADER = 27;
    /**
     * 号码选择对话框（含标题栏, 不含对勾, 不含自定义选项）
     */
    int DIALOG_NUMBER_SELECT_WITH_HEADER_ONLY = 29;

    /**
     * 会议退出框（主持人）
     */
    int DIALOG_CONF_EXIT_WITH_HOST = 30;

    /**
     * 设置国家码对话框
     */
//    int DIALOG_SET_LBS = 31;

    /**
     * LBS位置提示对话框
     */
//    int DIALOG_LBS_PROMPT = 32;

    /**
     * 摄像头选择
     */
    int DIALOG_CAMERA_SELECT = 33;

    /**
     * Toast默认显示时间
     */
//    int TOAST_SHOW_TIME = 3000;
    /**
     * lq
     * 弹出框左边按钮唯一id
     */
    int ID_LEFT_BUTTON = R.id.dialog_leftbutton;
    /**
     * /**
     * 弹出框右边按钮唯一id
     */
    int ID_RIGHT_BUTTON = R.id.dialog_rightbutton;

    /**
     * 当弹出框只有一个按钮时，此按钮的公开ID
     */
//    int ID_SINGLE_BUTTON = R.id.dialog_single_button;

    int SINGLE_BTN_TOAST = 34;

    int SINGLE_BTN_CANCEL_QUEST = 35;
    int DIALOG_POPUPWINDOW_DIAL_BUTTON = 30;
}
