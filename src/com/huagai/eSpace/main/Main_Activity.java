package com.huagai.eSpace.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.huagai.eSpace.R;

/**
 * Created by huagai on 14-3-6.
 */
public class Main_Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);
    }
}
