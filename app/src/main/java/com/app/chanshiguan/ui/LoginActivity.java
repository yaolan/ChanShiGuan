package com.app.chanshiguan.ui;

import android.os.Bundle;
import android.view.View;

import com.app.chanshiguan.R;

import butterknife.OnClick;

/**
 * Created by lyao on 2017/1/3.
 */

public class LoginActivity extends BaseActivity {


    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {


    }

    @OnClick({R.id.close,R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.register:
                RegisterActivity.launch(context);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.login2;
    }
}
