package com.app.chanshiguan;

import android.app.Application;
import android.util.Log;

import com.maxleap.GetCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.MaxLeap;
import com.maxleap.exception.MLException;


public class App extends Application {

    public static final String APP_ID = "585c99c50b0646000693b14c";
    public static final String API_KEY = "Rm5xVU1wb0Q4VWdhMkIxcV8yRzZWZw";

    @Override
    public void onCreate() {
        super.onCreate();

        if (APP_ID.startsWith("Replace") || API_KEY.startsWith("Replace")) {
            throw new IllegalArgumentException("Please replace with your app id and api key first before" +
                    "using MaxLeap SDK.");
        }

		/*
         * Fill in this section with your MaxLeap credentials
		 */
        MaxLeap.setLogLevel(MaxLeap.LOG_LEVEL_ERROR);
        MaxLeap.initialize(this, APP_ID, API_KEY, MaxLeap.REGION_CN);


        MLDataManager.fetchInBackground(MLObject.createWithoutData("foobar", "123"),
                new GetCallback<MLObject>() {
                    @Override
                    public void done(MLObject mlObject, MLException e) {
                        if (e != null && e.getCode() == MLException.INVALID_OBJECT_ID) {
                            Log.d("MaxLeap", "SDK 成功连接到你的云端应用！");
                        } else {
                            Log.d("MaxLeap", "应用访问凭证不正确，请检查。");
                        }
                    }
                });
    }
}
