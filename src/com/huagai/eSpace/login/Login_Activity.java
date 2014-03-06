package com.huagai.eSpace.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import com.huagai.eSpace.R;
import com.huagai.eSpace.dialog.NoticeConstant;
import com.huagai.eSpace.dialog.NoticeParams;
import com.huagai.eSpace.main.Main_Activity;
import com.huagai.eSpace.selfInfo.SelfDataHandler;
import com.huawei.common.PersonalContact;
import com.huawei.utils.StringUtil;


/**
 * Created by huagai on 14-2-24.
 */
public class Login_Activity extends Activity implements OnClickListener, RadioGroup.OnCheckedChangeListener, View.OnFocusChangeListener {

    //从配置文件中获取用户名
    private final String loginUserName = SelfDataHandler.getIns().getSelfData().getAccount();
    //从配置文件中获取是否保存密码
    private boolean isSavePassword = SelfDataHandler.getIns().getSelfData().isSavePassword();

    //状态
    private int status;// = SelfDataHandler.getIns().getSelfData().getLoginStatus();


    //本页面必用按钮集合
    private EditText edUsername;
    private EditText edPassword;
    private ImageView ivClearUsername;
    private ImageView ivClearPassword;

    //切换状态按钮 20140228
    private RelativeLayout llState;
    //状态选择RadioGroup
    private RadioGroup stateSelector;
    //弹出的窗口?此处是否要替换掉？直接用别的来处理此处的问题。不需要再做别的判别。20140228
    private PopupWindow popStateSelector;
    //用来判断窗口是否显示
    private boolean isPopupWindowShown = false;
    //状态及下拉菜单指示器
    private ImageView ivStatePointer;
    private ImageView ivState;


    //是否记住密码，本功能还未完善
    private CheckBox savePswCheckbox;
    private Button loginBtn;
    private LinearLayout settingBtn;


    //下面的不一定用到

    //private ImageView ivSetting;
    private Dialog dialog;
    /**
     * 登录消息处理Handler
     */
    private Handler loginHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LoginUtil.LOGIN_SUCCESS:

                    closeDialog();
                    Toast.makeText(getBaseContext(), "(R.string.label_login_success)", Toast.LENGTH_LONG).show();
                    Intent contactsIntent = new Intent(getBaseContext(), Main_Activity.class);
                    startActivity(contactsIntent);
                    break;

                case LoginUtil.LOGIN_FAILED:

                    closeDialog();
                    String error_msg = (String) msg.obj;
                    Toast.makeText(getBaseContext(), error_msg, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认不弹键盘，无titlebar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.login);

        initView();
    }

    //初始化
    private void initView() {
        //获取ID
        edUsername = (EditText) findViewById(R.id.username);
        edPassword = (EditText) findViewById(R.id.password);
        ivClearUsername = (ImageView) findViewById(R.id.ivClearUsername);
        ivClearPassword = (ImageView) findViewById(R.id.ivClearPassword);
        llState = (RelativeLayout) findViewById(R.id.llState);
        //以后准备迁移到别处的
        ivStatePointer = (ImageView) findViewById(R.id.ivStatePointer);
        ivState = (ImageView) findViewById(R.id.ivState);
        savePswCheckbox = (CheckBox) findViewById(R.id.isRememberPassword);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        //settingBtn按钮太小，直接用其容器LinearLayout来判定是否点触
        settingBtn = (LinearLayout) findViewById(R.id.settingBtn);


        //设置状态密码
        edUsername.setText(loginUserName);
        if (isSavePassword) {
            savePswCheckbox.setChecked(true);
            String loginPsw = SelfDataHandler.getIns().getSelfData().getPassword();
            edPassword.setText(loginPsw);
        }
        //保存密码checkbox是否勾选
        savePswCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (true == b) {
                    isSavePassword = true;
                } else {
                    isSavePassword = false;
                }
            }
        });

        //获取状态，根据状态设置按钮
        status = SelfDataHandler.getIns().getSelfData().getLoginStatus();
        if (PersonalContact.ON_LINE == status) {
            ivState.setImageResource(R.drawable.recent_online_big);
            // ivOnlineSelected.setChecked(true);
        } else if (PersonalContact.BUSY == status) {
            ivState.setImageResource(R.drawable.recent_busy_big);
//            ivBusySelected.setChecked(true);
        } else if (PersonalContact.AWAY == status) {
            ivState.setImageResource(R.drawable.recent_away_big);
            // ivAwaySelected.setChecked(true);
        }

        //绑定事件
        edUsername.addTextChangedListener(new clearInput(R.id.ivClearUsername));
        edUsername.setOnFocusChangeListener(this);
        ivClearUsername.setOnClickListener(this);

        edPassword.addTextChangedListener(new clearInput(R.id.ivClearPassword));
        edPassword.setOnFocusChangeListener(this);
        ivClearPassword.setOnClickListener(this);

        llState.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText et = null;
        if (v instanceof EditText) {
            et = (EditText) v;
            et.setSelection(et.getText().toString().length());
        }

        if (null == et) {
            return;
        }

        if (hasFocus && !StringUtil.isStringEmpty(et.getText().toString())) {
            // handleHasFocus(et);
        } else {
            handleLostFocus(et);
        }
    }

    private void handleLostFocus(EditText et) {
        if (null == et) {
            return;
        }
        switch (et.getId()) {
            case R.id.username:
                ivClearUsername.setVisibility(View.GONE);
                break;
            case R.id.password:
                ivClearPassword.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    //判断onClick的行为
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击登录按钮
            case R.id.loginBtn:
                //此处要加上跳转判别。
                Log.d("登录按钮点击", "++++++++++++++++++++++++++++++++++++++++++++++'/n'+++++++++++++++++");
                toSave();
                login();
                break;

            //点击设置按钮
            //todo 这里稍作修改，临时
            case R.id.settingBtn:
                startActivity(new Intent(getBaseContext(), NetSetting_Activity.class));
                break;

            //点击状态切换按钮。
            case R.id.llState:
                getPopupWindowInstance();
                if (false == isPopupWindowShown) {
                    ivStatePointer.setImageResource(R.drawable.login_select_pointer_up);
                    isPopupWindowShown = true;
                    popStateSelector.showAsDropDown(view, 0, -8);
                } else {
                    ivStatePointer.setImageResource(R.drawable.login_select_pointer_down);
                    isPopupWindowShown = false;
                    popStateSelector.dismiss();
                }
                break;
            //清除用户名
            case R.id.ivClearUsername:
                edUsername.setText("");
                break;
            //清除用户密码
            case R.id.ivClearPassword:
                edPassword.setText("");
                break;
            default:
                break;
        }
    }


    private void toSave() {
        String userName = edUsername.getText().toString().replaceAll(" ", "");
        String password = edPassword.getText().toString().replaceAll(" ", "");

        if (saveInfoToXml(userName, status, isSavePassword, password)) {
            Log.d("帐号密码保存成功", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

    }

    private boolean saveInfoToXml(String usrname, int status, boolean savepsw, String psw) {
        int resId = -1;
        if (StringUtil.isStringEmpty(usrname)) {
            resId = R.string.pls_input_uname;
        } else if (StringUtil.isStringEmpty(psw)) {
            resId = R.string.pls_input_pwd;
        }
        if (-1 != resId) {
            Toast.makeText(getBaseContext(), getString(resId), 0).show();
            return false;
        }

        // 保存操作
        SelfDataHandler.getIns().getSelfData().setAccount(usrname);
        SelfDataHandler.getIns().getSelfData().setLoginStatus(status);
        SelfDataHandler.getIns().getSelfData().setSavePassword(savepsw);

        if (true == savepsw) {
            SelfDataHandler.getIns().getSelfData().setPassword(psw);
        }
        return true;
    }

    //登录状态这里是否要处理？？
    protected void login() {
        Log.d("开始加载登录", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        String userName = edUsername.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
//        showLoginDlg();
        //重写onLogin方法
        LoginUtil.getIns().onLogin(this, loginHandler, userName, password);
    }

    //
//    /**
//     * 等待登录对话框
//     *
//     * @param msg       提示消息.若为空，则不弹框
//     * @param buttonTxt 按钮文字.若为空，则显示默认文字
//     * @param listener  按钮监听器，为null则表示默认监听
//     */
    private void showLoginDlg() {
        NoticeParams param = new NoticeParams(this, NoticeConstant.DIALOG_PROCESSING);
        if (null == dialog || !dialog.isShowing()) {
            dialog = showProcessDialog(param);
        }
    }

    private Dialog showProcessDialog(NoticeParams params) {
        Dialog dialog = new Dialog(params.getContext(), R.style.Theme_dialog);
        dialog.setContentView(R.layout.diaglog_processing);
        if (null != params.getMessage()) {
            TextView textView = (TextView) dialog
                    .findViewById(R.id.dialog_message);
            textView.setText(params.getMessage());
        }
        dialog.setOnDismissListener(params.getDialogDismissListener());
        dialog.setOnKeyListener(params.getKeyListener());
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    private void closeDialog() {
        if (null != dialog) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.ivOnlineSelected) {
            ivState.setImageResource(R.drawable.recent_online_big);
            status = PersonalContact.ON_LINE;
        } else if (i == R.id.ivBusySelected) {
            ivState.setImageResource(R.drawable.recent_busy_big);
            status = PersonalContact.BUSY;
        } else if (i == R.id.ivAwaySelected) {
            ivState.setImageResource(R.drawable.recent_away_big);
            status = PersonalContact.AWAY;
        }

        ivStatePointer.setImageResource(R.drawable.login_select_pointer_down);
        isPopupWindowShown = false;
        popStateSelector.dismiss();
    }

    private void getPopupWindowInstance() {
        if (null != popStateSelector) {
            popStateSelector.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /*
  * 创建PopupWindow
  */
    private void initPopuptWindow() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        //加载popup的布局文件
        View popupWindow = layoutInflater.inflate(R.layout.login_state_selector, null);
        popStateSelector = new PopupWindow(popupWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popStateSelector.setContentView(popupWindow);
        popStateSelector.setFocusable(true);

        //一下代码实现下拉状态按钮后，自动判定状态
        RadioButton ivOnlineSelected = (RadioButton) popupWindow.findViewById(R.id.ivOnlineSelected);
        RadioButton ivBusySelected = (RadioButton) popupWindow.findViewById(R.id.ivBusySelected);
        RadioButton ivAwaySelected = (RadioButton) popupWindow.findViewById(R.id.ivAwaySelected);
        switch (status) {
            case PersonalContact.ON_LINE:
                ivOnlineSelected.setChecked(true);
                break;
            case PersonalContact.BUSY:
                ivBusySelected.setChecked(true);
                break;
            case PersonalContact.AWAY:
                ivAwaySelected.setChecked(true);
                break;
            default:
                break;
        }

        stateSelector = (RadioGroup) popupWindow.findViewById(R.id.stateSelector);
        stateSelector.setOnCheckedChangeListener(this);

    }

    private final class clearInput implements TextWatcher {
        private int key;

        private clearInput(int id) {
            this.key = id;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (key) {
                case R.id.ivClearUsername:
                    if (editable.length() > 0) {
                        ivClearUsername.setVisibility(View.VISIBLE);
                    } else {
                        ivClearUsername.setVisibility(View.GONE);
                    }
                    break;
                case R.id.ivClearPassword:
                    if (editable.length() > 0) {
                        ivClearPassword.setVisibility(View.VISIBLE);
                    } else {
                        ivClearPassword.setVisibility(View.GONE);
                    }
                default:
                    break;
            }

        }
    }

}