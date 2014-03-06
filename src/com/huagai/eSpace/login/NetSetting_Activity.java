package com.huagai.eSpace.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.huagai.eSpace.R;
import com.huagai.eSpace.selfInfo.SelfDataHandler;
import com.huagai.eSpace.sharedprefer.AccountShare;
import com.huawei.common.Resource;
import com.huawei.utils.StringUtil;

import java.util.regex.Pattern;

/**
 * Created by huagai on 14-2-24.
 */
public class NetSetting_Activity extends Activity implements OnClickListener, View.OnFocusChangeListener {


    /**
     * 端口号码EditText允许的最大输入值
     */
    private static final int MAX_INPUT_VALUE_PORT_EDITTEXT = 65535;
    private static final int MAX_IP_LENGTH = 255;

    //地址与端口默认引用的是华为eSDK中的内容.
    private final String ip_addr = SelfDataHandler.getIns().getSelfData().getServerUrl();
    private final String ip_port = SelfDataHandler.getIns().getSelfData().getServerPort();
    //本地获取svn各项地址
    private final String svn_gate = SelfDataHandler.getIns().getSelfData().getSvnIp();
    private final String svn_port = SelfDataHandler.getIns().getSelfData().getSvnPort();
    //是否显示svn的标志，true为显示.
    boolean svnFlag = SelfDataHandler.getIns().getSelfData().isSvnFlag();
    //不定义final的原因是错误操作svn帐号后正确输回svn帐号可以自动输入对应的密码
    private String svn_account = SelfDataHandler.getIns().getSelfData().getSvnAccount();
    private String svn_password = SelfDataHandler.getIns().getSelfData().getSvnPassword();
    private EditText ipAddrEdit;
    private EditText ipPortEdit;

    private ImageView svnCheckBox;
    private LinearLayout svnLayout;

    private EditText svnGateEdit;
    private EditText svnPortEdit;
    private EditText svnAccountEdit;
    private EditText svnPasswordEdit;

    private ImageView clearIp;
    private ImageView clearIpPort;
    private ImageView clearSvnGate;
    private ImageView clearSvnPort;
    private ImageView clearSvnAccount;
    private ImageView clearSvnPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗口为无titlebar及不弹出软键盘
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.net_setting_view);

        initView();
    }

    private void initView() {
        //载入ID
        Button doneBtn = (Button) findViewById(R.id.done_Btn);
        doneBtn.setOnClickListener(this);

        ipAddrEdit = (EditText) findViewById(R.id.ip_edit);
        ipPortEdit = (EditText) findViewById(R.id.port_edit);

        svnCheckBox = (ImageView) findViewById(R.id.svn_check_box);
        svnLayout = (LinearLayout) findViewById(R.id.svnlayout);

        svnGateEdit = (EditText) findViewById(R.id.svn_gate_edit);
        svnPortEdit = (EditText) findViewById(R.id.svn_port_edit);
        svnAccountEdit = (EditText) findViewById(R.id.svn_account_edit);
        svnPasswordEdit = (EditText) findViewById(R.id.svn_password_edit);

        clearIp = (ImageView) findViewById(R.id.ivClear_ip);
        clearIpPort = (ImageView) findViewById(R.id.ivClear_port);
        clearSvnGate = (ImageView) findViewById(R.id.ivClear_svn_gate);
        clearSvnPort = (ImageView) findViewById(R.id.ivClear_svn_port);
        clearSvnAccount = (ImageView) findViewById(R.id.ivClear_svn_account);
        clearSvnPassword = (ImageView) findViewById(R.id.ivClear_svn_password);

        //为输入文本框绑定事件，用于处理输入时的内容
        ipAddrEdit.addTextChangedListener(new EditTextWatcher(R.id.ip_edit));
        ipAddrEdit.setOnFocusChangeListener(this);

        ipPortEdit.addTextChangedListener(new EditTextWatcher(R.id.port_edit));
        ipPortEdit.setOnFocusChangeListener(this);


        processEditTextWithNumber(svnGateEdit, svn_gate, MAX_IP_LENGTH);
        processEditTextWithNumber(ipAddrEdit, ip_addr, MAX_IP_LENGTH);

        //设定端口、svn端口、svn帐号、svn密码
        ipAddrEdit.setText(ip_addr);
        ipPortEdit.setText(ip_port);


        //初始化后，判断svn开关的状态,flag为true时，svn可见
        svnCheckBox.setImageResource(svnFlag ? R.drawable.btn_square_selected : R.drawable.btn_square_unselected);
        if (svnFlag) {
            svnCheckBox.setTag(true);
            svnLayout.setVisibility(View.VISIBLE);

            svnGateEdit.setText(svn_gate);
            svnPortEdit.setText(svn_port);
            svnAccountEdit.setText(svn_account);
            svnPasswordEdit.setText(svn_password);

            svnGateEdit.addTextChangedListener(new EditTextWatcher(R.id.svn_gate_edit));
            svnGateEdit.setOnFocusChangeListener(this);

            svnPortEdit.addTextChangedListener(new EditTextWatcher(R.id.svn_port_edit));
            svnPortEdit.setOnFocusChangeListener(this);

            svnAccountEdit.addTextChangedListener(new EditTextWatcher(R.id.svn_account_edit));
            svnAccountEdit.setOnFocusChangeListener(this);

            svnPasswordEdit.addTextChangedListener(new EditTextWatcher(R.id.svn_password_edit));
            svnPasswordEdit.setOnFocusChangeListener(this);


        } else {
            svnCheckBox.setTag(false);
            svnLayout.setVisibility(View.GONE);
        }


        svnCheckBox.setOnClickListener(this);

        clearIp.setOnClickListener(this);
        clearIpPort.setOnClickListener(this);
        clearSvnGate.setOnClickListener(this);
        clearSvnPort.setOnClickListener(this);
        clearSvnAccount.setOnClickListener(this);
        clearSvnPassword.setOnClickListener(this);
    }


    private void processEditTextWithNumber(EditText editText, String initText, int length) {
        if (null == editText) {
            return;
        }
        editText.setFilters(new InputFilter[]
                {new InputFilter.LengthFilter(length)});

        editText.setText(initText);
        Selection.setSelection(editText.getText(), editText.getText().length());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_Btn:
                //先保存，然后跳转到login界面。
                toSave();
                break;

            case R.id.svn_check_box:
                //如果不可见，则将其设为可见。
                if (!svnFlag) {
                    svnLayout.setVisibility(View.VISIBLE);
                    svnCheckBox.setTag(true);
                    svnCheckBox.setImageResource(R.drawable.btn_square_selected);
                    svnFlag = true;
                } else {
                    svnLayout.setVisibility(View.GONE);
                    svnCheckBox.setTag(false);
                    svnCheckBox.setImageResource(R.drawable.btn_square_unselected);
                    svnFlag = false;
                }
                break;

            //清除输入按键
            case R.id.ivClear_ip:
                ipAddrEdit.setText("");
                break;
            case R.id.ivClear_port:
                ipPortEdit.setText("");
                break;
            case R.id.ivClear_svn_gate:
                svnGateEdit.setText("");
                break;
            case R.id.ivClear_svn_port:
                svnPortEdit.setText("");
                break;
            case R.id.ivClear_svn_account:
                svnAccountEdit.setText("");
                svn_account = "";
                svn_password = "";
                svnPasswordEdit.setText("");
                break;
            case R.id.ivClear_svn_password:
                svnPasswordEdit.setText("");
                svn_password = "";
                break;

            default:
                break;
        }

    }

    private void toSave() {
        String ip = ipAddrEdit.getText().toString().replaceAll(" ", "");
        String port = ipPortEdit.getText().toString().replaceAll(" ", "");
        String svnIp = svnGateEdit.getText().toString().replaceAll(" ", "");
        String svnPort = svnPortEdit.getText().toString().replaceAll(" ", "");
        String svnAcc = svnAccountEdit.getText().toString().replaceAll(" ", "");
        String svnPass = svnPasswordEdit.getText().toString().replaceAll(" ", "");

        //保存配置成功，则跳转到登录界面
        if (saveInfoToXml(ip, port, svnIp, svnPort, svnAcc, svnPass)) {
            startActivity(new Intent(getBaseContext(), Login_Activity.class));
        }

    }

    private boolean saveInfoToXml(String ip, String port, String svnIp, String svnPort, String svnAcc, String svnPass) {
        boolean ischecked = ((Boolean) svnCheckBox.getTag()).booleanValue();
        //检查输入是否合法
        int resId = -1;
        if (StringUtil.isStringEmpty(ip)) {
            resId = R.string.serverblank;
        } else if (StringUtil.isStringEmpty(port)) {
            resId = R.string.portblank;
        } else if (!isIPAddress(ip) && !isDomainName(ip)) {
            resId = R.string.net_address_format_error;
        } else if (isIllegalPort(port)) {
            resId = R.string.net_port_error;
        } else if (ischecked) {
            if (StringUtil.isStringEmpty(svnIp)) {
                resId = R.string.svnipblank;
            } else if (StringUtil.isStringEmpty(svnPort)) {
                resId = R.string.svnportblank;
            } else if (StringUtil.isStringEmpty(svnAcc)) {
                resId = R.string.svn_account_empty_prompt;
            } else if (StringUtil.isStringEmpty(svnPass)) {
                resId = R.string.svn_pwd_empty_prompt;
            } else if (!isIPAddress(svnIp) && !isDomainName(svnIp)) {
                resId = R.string.svn_address_format_error;
            } else if (isIllegalPort(svnPort)) {
                resId = R.string.svn_port_error;
            }
        }
        if (-1 != resId) {
            Toast.makeText(getBaseContext(), getString(resId), 0).show();
            return false;
        }

        // 保存操作
        SelfDataHandler.getIns().getSelfData().setServerUrl(ip);
        SelfDataHandler.getIns().getSelfData().setServerPort(port);
        SelfDataHandler.getIns().getSelfData().setSvnFlag(ischecked);
        //勾选的话就保存svn系列
        if (ischecked) {
            SelfDataHandler.getIns().getSelfData().setSvnIp(svnIp);
            SelfDataHandler.getIns().getSelfData().setSvnPort(svnPort);
            SelfDataHandler.getIns().getSelfData().setSvnAccount(svnAcc);
            SelfDataHandler.getIns().getSelfData().setSvnPassword(svnPass);
        }
        //预留：：：：：保存版本号，用于以后版本升级后director界面变化后的显示。
        SelfDataHandler.getIns().getSelfData().setVersion(getString(R.string.androidversion));
        //设置第一次运行界面不再显示，这个不是很重要，所以没有在SelfData中添加，只是做本地化处理。
        AccountShare.getIns().setGuideFlag(false);
        return true;
    }

    //onFocus为否，则隐藏clear的那些按钮。
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
            case R.id.ip_edit:
                clearIp.setVisibility(View.GONE);
                break;
            case R.id.port_edit:
                clearIpPort.setVisibility(View.GONE);
                break;
            case R.id.svn_gate_edit:
                clearSvnGate.setVisibility(View.GONE);
                break;
            case R.id.svn_port_edit:
                clearSvnPort.setVisibility(View.GONE);
                break;
            case R.id.svn_account_edit:
                clearSvnAccount.setVisibility(View.GONE);
                break;
            case R.id.svn_password_edit:
                clearSvnPassword.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    //！！！！此处待考究！！！！有焦点没必要显示，只有修改后再显示。此处注释掉，待几个版本后彻底删除此代码。
//    private void handleHasFocus(EditText et) {
//        if (null == et)
//        {
//            return;
//        }
//        switch (et.getId())
//        {
//            case R.id.ip_edit:
//                clearIp.setVisibility(View.VISIBLE);
//                break;
//            case R.id.port_edit:
//                clearIpPort.setVisibility(View.VISIBLE);
//                break;
//            case R.id.svn_gate_edit:
//                clearSvnGate.setVisibility(View.VISIBLE);
//                break;
//            case R.id.svn_port_edit:
//                clearSvnPort.setVisibility(View.VISIBLE);
//                break;
//            case R.id.svn_account_edit:
//                clearSvnAccount.setVisibility(View.VISIBLE);
//                break;
//            case R.id.svn_password_edit:
//                clearSvnPassword.setVisibility(View.VISIBLE);
//                break;
//            default:
//                break;
//        }
//    }

    /**
     * 判断端口的输入是否为 0 ~ 65535
     *
     * @param s
     */
    private boolean isIllegalPort(String s) {
        if (null == s) {
            return true;
        }
        int value = Integer.parseInt(s);
        if (s.charAt(0) == '0' || value > MAX_INPUT_VALUE_PORT_EDITTEXT) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是IP地址
     *
     * @param s
     * @return
     */
    private boolean isIPAddress(String s) {
        Pattern p = Pattern
                .compile("^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.){3}"
                        + "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])$");
        if (p.matcher(s).matches()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是域名
     *
     * @param s
     * @return
     */
    private boolean isDomainName(String s) {
        if (StringUtil.isStringEmpty(s) || s.matches("[0-9//.]+")) {
            return false;
        }

        Pattern p = Pattern.compile("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?");
        if (p.matcher(s).matches()) {
            return true;
        }
        return false;
    }

    private final class EditTextWatcher implements TextWatcher {
        private int key;

        private EditTextWatcher(int id) {
            this.key = id;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (key) {
                case R.id.ip_edit:
                    handleTextChange(s, ipAddrEdit, clearIp);
                    break;
                case R.id.port_edit:
                    handleTextChange(s, ipPortEdit, clearIpPort);
                    break;
                case R.id.svn_gate_edit:
                    handleTextChange(s, svnGateEdit, clearSvnGate);
                    break;
                case R.id.svn_port_edit:
                    handleTextChange(s, svnPortEdit, clearSvnPort);
                    break;
                case R.id.svn_account_edit:
                    handleTextChange(s, svnAccountEdit, clearSvnAccount);
                    if (svn_account.equalsIgnoreCase(s.toString())) {
                        svnPasswordEdit.setText(svn_password);
                    } else {
                        svnPasswordEdit.setText(Resource.CharCharacter.EMPTY);
                    }
                    break;
                case R.id.svn_password_edit:
                    handleTextChange(s, svnPasswordEdit, clearSvnPassword);
                    break;
                default:
                    break;
            }
        }

        private void handleTextChange(Editable s, EditText et, ImageView iv) {
            if ((null != s) && (null != s.toString())
                    && (null != clearIp)) {
                if ("".equals(et.getText().toString().trim())
                        || !et.hasFocus()) {
                    iv.setVisibility(View.GONE);
                } else {
                    iv.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}