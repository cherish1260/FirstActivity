package com.example.first;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.first.activity.FirstActivity;
import com.example.first.activity.ThirdActivity;
import com.example.first.service.BindService;
import com.example.first.service.IMyAidlInterface2;
import com.example.first.service.MyBinder;
import com.example.first.service.MyIntentService;
import com.example.first.service.MyService;
import com.example.first.service.NormalService;
import com.example.second.secondapplication.IMyAidlInterface;

public class MainActivity extends Activity implements View.OnClickListener {

    Button btnTest, btnStart, btnStop, btnBind, btnUnbind,
            btnRemoteBind, btnRemoteBind2, btnStartIntentService,
            btnMessenger, btnSend;
    TextView textView;
    private MyBinder myBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //实例化Service的内部类myBinder
            //通过向下转型得到了MyBinder的实例
            myBinder = (MyBinder) service;
            //在Activity调用Service类的方法
            myBinder.service_connect_Activity();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private ServiceConnection serviceConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = IMyAidlInterface.Stub.asInterface(service);
            try {
                binder.useFromRemoteService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private ServiceConnection serviceConnection3 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder2 = IMyAidlInterface2.Stub.asInterface(service);
            try {
                binder2.useFromRemoteService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private IMyAidlInterface binder;
    private IMyAidlInterface2 binder2;
    private static final int MSG_SUM = 0x110;
    private Messenger mService;
    private boolean conn;
    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msgFromServer) {
            Message msgToServer = Message.obtain(msgFromServer);
            switch (msgFromServer.what) {
                case MSG_SUM:
                    textView.setText("from server:" + msgFromServer.arg2);
                    break;

            }
            super.handleMessage(msgFromServer);
        }
    });

    private ServiceConnection serviceConnection4 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            conn = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            conn = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    private void initData() {
        Uri uri_user = Uri.parse("content://com.example.first.provider/user");
        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", 3);
        values.put("name", "Iverson");

        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user,values);

        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri_user, new String[]{"_id","name"}, null, null, null);
        while (cursor.moveToNext()){
            System.out.println("query book:" + cursor.getInt(0) +" "+ cursor.getString(1));
            // 将表中数据全部输出
        }
        cursor.close();

    }

    private void startIntentService() {
        // 请求1
        Intent i = new Intent(this, MyIntentService.class);
        Bundle bundle = new Bundle();
        bundle.putString("taskName", "task1");
        i.putExtras(bundle);
        startService(i);

        // 请求2
        Intent i2 = new Intent(this, MyIntentService.class);
        Bundle bundle2 = new Bundle();
        bundle2.putString("taskName", "task2");
        i2.putExtras(bundle2);
        startService(i2);

        startService(i);  //多次启动
    }

    private void initViews() {
        textView = findViewById(R.id.textView);
        btnTest = findViewById(R.id.btn_test);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnBind = findViewById(R.id.btn_bind);
        btnUnbind = findViewById(R.id.btn_unbind);
        btnRemoteBind = findViewById(R.id.btn_remote_bind);
        btnRemoteBind2 = findViewById(R.id.btn_remote_bind2);
        btnStartIntentService = findViewById(R.id.btn_start_intent_service);
        btnSend = findViewById(R.id.btn_send);
        btnMessenger = findViewById(R.id.btn_messenger);
        btnTest.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnBind.setOnClickListener(this);
        btnUnbind.setOnClickListener(this);
        btnRemoteBind.setOnClickListener(this);
        btnRemoteBind2.setOnClickListener(this);
        btnStartIntentService.setOnClickListener(this);
        btnMessenger.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_start:
                //构建启动服务的Intent对象
                Intent startIntent = new Intent(this, BindService.class);
                //调用startService()方法-传入Intent对象,以此启动服务
                startService(startIntent);
                break;
            case R.id.btn_stop:
                //构建停止服务的Intent对象
                Intent stopIntent = new Intent(this, BindService.class);
                //调用stopService()方法-传入Intent对象,以此停止服务
                stopService(stopIntent);
                break;
            case R.id.btn_bind:
                //构建绑定服务的Intent对象
                Intent bindIntent = new Intent(this, BindService.class);
                bindIntent.setPackage("com.example.first.service");
                //调用bindService()方法,以此停止服务
                /**
                 * 参数说明
                 * 第一个参数:Intent对象
                 * 第二个参数:上面创建的Serviceconnection实例
                 * 第三个参数:标志位
                 * 这里传入BIND_AUTO_CREATE表示在Activity和Service建立关联后自动创建Service
                 * 这会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会执行
                 */
                bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                //调用unbindService()解绑服务
                //参数是上面创建的Serviceconnection实例
                unbindService(serviceConnection);
                break;
            case R.id.btn_remote_bind:
                System.out.println("点击了[绑定服务]按钮");
                Intent bindRemoteIntent = new Intent("com.example.second.secondapplication.IMyAidlInterface");
                bindRemoteIntent.setPackage("com.example.second.secondapplication");
                bindService(bindRemoteIntent, serviceConnection2, BIND_AUTO_CREATE);
                break;
            case R.id.btn_remote_bind2:
                System.out.println("点击了[绑定服务2]按钮");
                Intent bindRemoteIntent2 = new Intent("com.example.first.service.IMyAidlInterface2");
                bindRemoteIntent2.setPackage("com.example.first.service:remote");
                bindService(bindRemoteIntent2, serviceConnection3 , BIND_AUTO_CREATE);
                break;
            case R.id.btn_start_intent_service:
                startIntentService();
                break;
            case R.id.btn_messenger:
                startMessengerService();
                break;
            case R.id.btn_send:
                sendMessage();
                break;
            default:
                break;
        }
    }

    private void sendMessage() {
        Message msgFromClient = Message.obtain(null, MSG_SUM, 10, 11);
        msgFromClient.replyTo = messenger;
        if (conn) {
            try {
                mService.send(msgFromClient);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void startMessengerService() {

        Intent intent = new Intent("com.example.second.message");
        intent.setPackage("com.example.second.secondapplication");
        bindService(intent, serviceConnection4, BIND_AUTO_CREATE);


    }
}
