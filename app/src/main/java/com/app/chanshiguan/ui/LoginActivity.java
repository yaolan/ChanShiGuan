package com.app.chanshiguan.ui;

import android.os.Bundle;
import android.view.View;

import com.app.chanshiguan.R;

import butterknife.OnClick;
import cn.droidlover.xdroid.base.XActivity;

/**
 * Created by lyao on 2017/1/3.
 */

public class LoginActivity extends XActivity {


    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {


    }

    @OnClick({R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.login2;
    }
}
