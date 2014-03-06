package com.huagai.eSpace.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.huagai.eSpace.R;
import com.huagai.eSpace.login.NetSetting_Activity;

/**
 * Created by huagai on 14-3-6.
 */
public class Welcome_Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        initView();
    }

    private void initView() {
        //获取按键，点击后跳转到设置界面
        Button start_network_btn = (Button) findViewById(R.id.start_setting_network);

        start_network_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), NetSetting_Activity.class));
                Log.d("++++++++++++++HUAGAI++++++++++++++", "点击 开始 按钮");
            }
        });
    }
}
