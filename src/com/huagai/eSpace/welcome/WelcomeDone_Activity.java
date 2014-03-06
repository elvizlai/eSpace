package com.huagai.eSpace.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import com.huagai.eSpace.R;
import com.huagai.eSpace.login.Login_Activity;
import com.huagai.eSpace.sharedprefer.AccountShare;

public class WelcomeDone_Activity extends Activity {
    /**
     * Called when the activity is first created.
     */

    //从配置文件中获取是否是第一次安装？
    private boolean isTheFirstInsatall = AccountShare.getIns().isGuidedFlag();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //要求无title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcomedone);
        initView();
    }

    private void initView() {
        //延时2000ms跳转
        new Handler().postDelayed(new DelayDisplay(), 2000);
    }


    //设置要跳转到的Activity
    private class DelayDisplay implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent();
            //判断是否首次运行，如果是，就跳转到Welcome_Activity，如果不是，就直接跳转到Login_Activity
            if (true == isTheFirstInsatall) {
                intent.setClass(getBaseContext(), Welcome_Activity.class);
                Log.d("++++++++++++++HUAGAI++++++++++++++", "第一次安装");
            } else {
                intent.setClass(getBaseContext(), Login_Activity.class);
                Log.d("++++++++++++++HUAGAI++++++++++++++", "不是！！第一次安装");
            }
            startActivity(intent);
        }
    }
}
