package com.example.first.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.first.R;
import com.example.first.fragments.FragmentOne;
import com.example.first.fragments.FragmentTwo;

import java.util.List;

/**
 * Created by sxiaoxia on 2018/5/2.
 */

public class ThirdActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "ThirdActivity—Fragment";
    FragmentManager fragmentManager;
    Button btnOne, btnTwo;
    Fragment fragmentOne, fragmentTwo, curFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initData();
    }

    private void initData() {
        btnOne = findViewById(R.id.btn_one);
        btnTwo = findViewById(R.id.btn_two);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        // 1.创建Fragment的管理对象fragmentManager
        fragmentManager = getFragmentManager();
        // 2.创建fragment
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.v_content, fragmentOne);
        fragmentTransaction.commit();
        curFragment = fragmentOne;
    }

    @Override
    public void onClick(View v) {
        // 3.创建事务对象（Fragment事务对象不能抽取,因为每提交一次,就需要一个新的Fragment事务对象.(所有的事务都有这个特性)）
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch(v.getId()) {
            case R.id.btn_one:
                // 判断fragmentOne是否已经存在
                if (fragmentOne.isAdded()) {
                    //如果fragmentOne已经存在，则隐藏当前的fragment，
                    //然后显示fragmentOne（不会重新初始化，只是加载之前隐藏的fragment）
                    fragmentTransaction.hide(curFragment).show(fragmentOne);
                } else {
                    //如果fragmentOne不存在，则隐藏当前的fragment，
                    //然后添加fragmentOne（此时是初始化）
                    fragmentTransaction.hide(curFragment).add(R.id.v_content, fragmentOne);
                }
                curFragment = fragmentOne;
                // 4.提交事务
                fragmentTransaction.commit();
                break;
            case R.id.btn_two:
                if (fragmentTwo.isAdded()) {
                    fragmentTransaction.hide(curFragment).show(fragmentTwo);
                } else {
                    fragmentTransaction.hide(curFragment).add(R.id.v_content, fragmentTwo);
                }
                curFragment = fragmentTwo;
                fragmentTransaction.commit();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
