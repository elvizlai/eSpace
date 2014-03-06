package com.huagai.eSpace.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import com.huawei.common.PersonalContact;

import java.util.List;

/**
 * 弹出框参数
 */
public class NoticeParams {
    /**
     * 灰色背景
     */
    public static final int COLOR_GRAY_BUTTON_BG = 0;

    /**
     * 红色背景
     */
    public static final int COLOR_RED_BUTTON_BG = 1;

    /**
     * 对话框类型
     */
    private int dialogType;
    /**
     * 对话框标题文本
     */
    private String headerText;
    /**
     * 对话框内容
     */
    private String message;
    /**
     * 左边按钮文本
     */
    private String leftButtonText;
    /**
     * 右边按钮文本
     */
    private String rightButtonText;
    /**
     * 单一按钮文本
     */
    private String singleButtonText;
    /**
     * 左边按钮背景色, 默认为灰色， (0 - 灰色， 1 - 红色)
     */
    private int leftButtonBgColor;
    /**
     * 同上
     */
    private int rightButtonBgColor;
    /**
     * 同上
     */
    private int singleButtonBgColor;
    /**
     * 上下文环境
     */
    private Context context;
    /**
     * 左边按钮点击事件监听
     */
    private View.OnClickListener leftButtonListener;
    /**
     * /**
     * 右边按钮点击事件监听
     */
    private View.OnClickListener rightButtonListener;
    /**
     * 单一按钮点击事件监听
     */
    private View.OnClickListener singleButtonListener;
    /**
     * 弹出框键盘事件监听
     */
    private DialogInterface.OnKeyListener keyListener;
    /**
     * 对话框消失监听
     */
    private DialogInterface.OnDismissListener dialogDismissListener;
    /**
     * 以listview形式展现的白色风格对话框 --- item点击事件监听
     */
    private AdapterView.OnItemClickListener listViewItemListener;
    /**
     * listview形式弹出菜单的数据
     */
    private List<Object> data;

    /**
     * popupWindow显示所依赖的参照物
     */
    private View anchor;

    /**
     * popupWindow显示所依赖的参照物的高度
     */
    private int anchorHeight;

    /**
     * popupWindow消失监听
     */
    private PopupWindow.OnDismissListener popupDismissListener;

    /**
     * 标题栏popupWindow昵称
     */
    private String nickName;

    /**
     * 标题栏popupWindow签名
     */
    private String signature;

    /**
     * listview中的默认选项索引，用于预置默认选项
     */
    private int index = -1;

    /**
     * 对于含有编辑框EditText的弹出框，EditText初始文本
     */
    private String editTextInitText;

    /**
     * 编辑框EditText的输入类型
     */
    private int editTextInputType;

    /**
     * 设置EditText是否允许输入空字符串,默认不允许
     */
    private boolean notInputEmptyText;

    /**
     * 是否显示标题栏下方的提示信息
     */
    private boolean showHint;

    /**
     * 编辑框EditText最大输入长度
     */
    private int editTextMaxLength;

    /**
     * 当前用户选择的号码（仅用于号码选择对话框）
     */
    private String number;

    /**
     * 底部声明
     */
    private String bottomText;

    private String itemId;

    /**
     * 通用view单击事件监听
     */
    private View.OnClickListener commonListener;

    /**
     * 拨打联系人电话时，是否只显示eSpace拨打方式
     */
    private boolean onlyShowEspace;

    private int functionStyle = 34;// NoticeConstant.SINGLE_BTN_TOAST;  lq

    private OperateConfVideoTag videoTag;

    private PersonalContact personalContact;

    public PersonalContact getPersonalContact() {
        return personalContact;
    }

    public void setPersonalContact(PersonalContact personalContact) {
        this.personalContact = personalContact;
    }

    public OperateConfVideoTag getVideoTag() {
        return videoTag;
    }

    public void setVideoTag(OperateConfVideoTag videoTag) {
        this.videoTag = videoTag;
    }

    public int getFunctionStyle() {
        return functionStyle;
    }

    public void setFunctionStyle(int functionStyle) {
        this.functionStyle = functionStyle;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }

    public NoticeParams(Context context, int dialogType) {
        this.context = context;
        this.dialogType = dialogType;
    }

    public void setEditTextInitText(String editTextInitText) {
        this.editTextInitText = editTextInitText;
    }

    public String getEditTextInitText() {
        return editTextInitText;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setEditTextInputType(int editTextInputType) {
        this.editTextInputType = editTextInputType;
    }

    public int getEditTextInputType() {
        return editTextInputType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setEditTextMaxLength(int editTextMaxLength) {
        this.editTextMaxLength = editTextMaxLength;
    }

    public int getEditTextMaxLength() {
        return editTextMaxLength;
    }

    public String getLeftButtonText() {
        return leftButtonText;
    }

    public void setLeftButtonText(String leftButtonText) {
        this.leftButtonText = leftButtonText;
    }

    public String getRightButtonText() {
        return rightButtonText;
    }

    public void setRightButtonText(String rightButtonText) {
        this.rightButtonText = rightButtonText;
    }

    public Context getContext() {
        return context;
    }

    public View.OnClickListener getLeftButtonListener() {
        return leftButtonListener;
    }

    public void setLeftButtonListener(View.OnClickListener leftButtonListener) {
        this.leftButtonListener = leftButtonListener;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getHeaderText() {
        return headerText;
    }

    public View.OnClickListener getRightButtonListener() {
        return rightButtonListener;
    }

    public void setRightButtonListener(View.OnClickListener rightButtonListener) {
        this.rightButtonListener = rightButtonListener;
    }

    public int getDialogType() {
        return dialogType;
    }

    public void setDialogDismissListener(
            DialogInterface.OnDismissListener dialogDismissListener) {
        this.dialogDismissListener = dialogDismissListener;
    }

    public DialogInterface.OnDismissListener getDialogDismissListener() {
        return dialogDismissListener;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public List<Object> getData() {
        return data;
    }

    public void setListViewItemListener(
            AdapterView.OnItemClickListener listViewItemListener) {
        this.listViewItemListener = listViewItemListener;
    }

    public AdapterView.OnItemClickListener getListViewItemListener() {
        return listViewItemListener;
    }

    public void setAnchor(View anchor) {
        this.anchor = anchor;
    }

    public View getAnchor() {
        return anchor;
    }

    public void setPopupDismissListener(
            PopupWindow.OnDismissListener popupDismissListener) {
        this.popupDismissListener = popupDismissListener;
    }

    public PopupWindow.OnDismissListener getPopupDismissListener() {
        return popupDismissListener;
    }

    public void setAnchorHeight(int anchorHeight) {
        this.anchorHeight = anchorHeight;
    }

    public int getAnchorHeight() {
        return anchorHeight;
    }

    /**
     * 方法名称：setNickName
     * 作者：ShaoPeng
     * 方法描述：设置nickName
     *
     * @param nickName the nickName to set
     *                 返回类型：@return void
     *                 备注：
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    /**
     * 方法名称：getNickName
     * 作者：ShaoPeng
     * 方法描述：获取nickName
     * 返回类型：@return the nickName
     * 备注：
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 方法名称：setSignature
     * 作者：ShaoPeng
     * 方法描述：设置signature
     *
     * @param signature the signature to set
     *                  返回类型：@return void
     *                  备注：
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 方法名称：getSignature
     * 作者：ShaoPeng
     * 方法描述：获取signature
     * 返回类型：@return the signature
     * 备注：
     */
    public String getSignature() {
        return signature;
    }

    public void setSingleButtonListener(View.OnClickListener singleButtonListener) {
        this.singleButtonListener = singleButtonListener;
    }

    public void setSingleButtonText(String singleButtonText) {
        this.singleButtonText = singleButtonText;
    }

    public View.OnClickListener getSingleButtonListener() {
        return singleButtonListener;
    }

    public String getSingleButtonText() {
        return singleButtonText;
    }

    public void setNotInputEmptyText(boolean notInputEmptyText) {
        this.notInputEmptyText = notInputEmptyText;
    }

    public boolean isNotInputEmptyText() {
        return notInputEmptyText;
    }

    public boolean isShowHint() {
        return showHint;
    }

    public void setShowHint(boolean showHint) {
        this.showHint = showHint;
    }

    public void setKeyListener(DialogInterface.OnKeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public DialogInterface.OnKeyListener getKeyListener() {
        return keyListener;
    }

    public void setCommonListener(View.OnClickListener commonListener) {
        this.commonListener = commonListener;
    }

    public View.OnClickListener getCommonListener() {
        return commonListener;
    }

    public boolean isOnlyShowEspace() {
        return onlyShowEspace;
    }

    public void setOnlyShowEspace(boolean onlyShowEspace) {
        this.onlyShowEspace = onlyShowEspace;
    }

    public void setLeftButtonBgColor(int leftButtonBgColor) {
        this.leftButtonBgColor = leftButtonBgColor;
    }

    public int getLeftButtonBgColor() {
        return leftButtonBgColor;
    }

    public void setRightButtonBgColor(int rightButtonBgColor) {
        this.rightButtonBgColor = rightButtonBgColor;
    }

    public int getRightButtonBgColor() {
        return rightButtonBgColor;
    }

    public void setSingleButtonBgColor(int singleButtonBgColor) {
        this.singleButtonBgColor = singleButtonBgColor;
    }

    public int getSingleButtonBgColor() {
        return singleButtonBgColor;
    }


    //lq
    public interface OperateConfVideoTag {
        void operateVideoTagListener(int position, ImageView operateView);
    }

}
