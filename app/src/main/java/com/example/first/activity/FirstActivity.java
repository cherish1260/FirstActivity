package com.example.first.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.first.R;

/**
 * Created by sxiaoxia on 2018/4/18.
 */

public class FirstActivity extends Activity {

    LinearLayout llLeft;
    LinearLayout llRight;
    EditText editText;
    Button btnJump;
    private static final String TAG = "FirstActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initViews();
        Log.i(TAG, TAG + "onCreate");
        Log.i("WooYun", TAG + "onCreate：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
    }

    private void initViews() {
        llLeft = findViewById(R.id.ll_left);
        llRight = findViewById(R.id.ll_right);
        btnJump = findViewById(R.id.btn_jump);
        llLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair first = new Pair<>(llLeft, ViewCompat.getTransitionName(llLeft));

                    Pair second = new Pair<>(llRight, ViewCompat.getTransitionName(llRight));

                    ActivityOptionsCompat transitionActivityOptions =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(FirstActivity.this, first, second);

                    startActivity(intent, transitionActivityOptions.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            ActivityInfo info = this.getPackageManager()
                    .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Log.i(TAG, TAG + "taskAffinity:" + info.taskAffinity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, TAG + "onRestoreInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, TAG + "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, TAG + "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, TAG + "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, TAG + "onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, TAG + "onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, TAG + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, TAG + "onDestroy");
    }
}
