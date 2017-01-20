package com.app.chanshiguan.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.chanshiguan.R;
import com.app.chanshiguan.utils.NoUtilCheck;
import com.app.chanshiguan.widget.LoginEditText;
import com.maxleap.LogInCallback;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.RequestSmsCodeCallback;
import com.maxleap.exception.MLException;

import butterknife.OnClick;
import cn.droidlover.xdroid.router.Router;

/**
 * Created by lyao on 2017/1/3.
 */

public class RegisterActivity extends BaseActivity {


    private TextView txt_resend_code;
    private LoginEditText editVerifi, editPhone;
    private CountDownTimer countDownTimer;
    private MaterialDialog progressDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        txt_resend_code = (TextView) findViewById(R.id.resend_code);
        editVerifi = (LoginEditText) findViewById(R.id.editVoiceCode);
        editPhone = (LoginEditText) findViewById(R.id.editPhone);
    }

    @Override
    public void setListener() {


    }

    @OnClick({R.id.close, R.id.resend_code, R.id.loginButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.resend_code:

                if (TextUtils.isEmpty(editPhone.getText().toString().trim())) {
                    showToast(getString(R.string.fragment_login_tel_empty_error));
                    editPhone.requestFocus();
                } else if (!NoUtilCheck.isMobileNo(editPhone.getText().toString())) {
                    showToast(getString(R.string.fragment_login_tel_empty_error));
                    editPhone.requestFocus();
                } else {
                    sendSms();
                }
                break;
            case R.id.loginButton:


                break;

        }
    }


    public static void launch(Activity activity) {
        Router.newIntent()
                .from(activity)
                .to(RegisterActivity.class)
                .data(new Bundle())
                .launch();
    }

    @Override
    public int getLayoutId() {
        return R.layout.register2;
    }


    private void sendSms() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    txt_resend_code.setEnabled(false);
                    txt_resend_code.setText("重新发送(" + millisUntilFinished / 1000 + ")");
                }

                @Override
                public void onFinish() {
                    txt_resend_code.setEnabled(true);
                    txt_resend_code.setText(R.string.activity_register_get_verify_code);
                }
            };
        }
        countDownTimer.start();
        String tel = editPhone.getText().toString();

        MLUserManager.requestLoginSmsCodeInBackground(tel, new RequestSmsCodeCallback() {
            @Override
            public void done(final MLException e) {
                if (e != null) {
                    showToast(getString(R.string.fragment_login_get_verify_code_failed));
                    countDownTimer.cancel();
                    txt_resend_code.setEnabled(true);
                    txt_resend_code.setText(R.string.activity_register_get_verify_code);
                } else {
                    showToast("发送成功");
                }
            }
        });

    }


    private void regist() {

        final String tel = editPhone.getText().toString();

        if (TextUtils.isEmpty(tel)) {
            showToast(getString(R.string.fragment_login_tel_empty_error));
            editPhone.requestFocus();
            return;
        }
        String code = editVerifi.getText().toString();
        if (!NoUtilCheck.isMobileNo(tel)) {
            showToast(getString(R.string.fragment_login_tel_invalid_error));
            editPhone.requestFocus();
        } else if (TextUtils.isEmpty(code)) {
            showToast(getString(R.string.fragment_login_password_code_empty_error));
            editVerifi.requestFocus();
        } else {


            hideKeyboard();
            progressDialog = new MaterialDialog.Builder(RegisterActivity.this).title("").content(R.string.wait).progress(true, 0).progressIndeterminateStyle(false).show();

            MLUserManager.loginWithSmsCodeInBackground(tel, code, new LogInCallback() {
                @Override
                public void done(MLUser user, MLException e) {
                    if (e != null) {
                        showToast(e.getMessage());
                    } else {
                        user.setUserName(tel);
//                        user.setPassword(pwd);
//                        MLUserManager.signUpInBackground(user, new SignUpCallback() {
//                            public void done(MLException e) {
//                                progressBarArea.setVisibility(View.GONE);
//                                if (e == null) {
//                                    // 注册成功
//                                    showToast("注册成功!");
//                                    finish();
//                                } else {
//                                    // 注册失败
//                                    showToast(e.getMessage());
//                                }
//                            }
//                        });
                    }
                }
            });
        }

    }
}

