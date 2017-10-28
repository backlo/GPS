package com.example.lik.gps;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class LockService extends Service {

    private View mView;
    private WindowManager mManager;

    @Override
    public void onCreate() {
        super.onCreate();
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(R.layout.activity_lock_service, null);

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);

        mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mManager.addView(this.mView, mParams);

    }

    public void onDestory(){
        super.onDestroy();
        if(this.mView != null){
            ((WindowManager)getSystemService(WINDOW_SERVICE)).removeView(this.mView);
            this.mView = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
