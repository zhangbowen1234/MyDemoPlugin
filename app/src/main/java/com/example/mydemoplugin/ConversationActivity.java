package com.example.mydemoplugin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends AppCompatActivity {
    String targetId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Intent intent = getIntent(); // 取得从上一个Activity当中传递过来的Intent对象
        if (intent.getStringExtra("targetId") != null) {
            targetId = intent.getStringExtra("targetId");
        }else {
            targetId = intent.getData().getQueryParameter("targetId");
        }
//        Log.e("回话界面123",RongIM.getInstance().getCurrentConnectionStatus()+"---");
        if (RongIM.getInstance().getCurrentConnectionStatus()!= RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED){
            String token = "hsE5f3CXliULgTPr/USXNazb39xdkVBWcXOhWOHAV2A=@4hjh.cn.rongnav.com;4hjh.cn.rongcfg.com";//999
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {
                    //消息数据库打开，可以进入到主页面
                }

                @Override
                public void onSuccess(String s) {
                    //连接成功
                    Log.i("IM连接成功", s);
                    //3。用户信息提供者异步设置
                    // 是否缓存用户信息. true 缓存, false 不缓存
                    // 1. <span style="color:red">当设置 true 后, 优先从缓存中获取用户信息.
                    // 2. 更新用户信息, 需调用 RongIM.getInstance().refreshUserInfoCache(userInfo)
                    Uri uri = Uri.parse("rong://" +
                            getApplicationContext().getApplicationInfo().packageName).buildUpon()
                            .appendPath("conversation").appendPath("private")
                            .appendQueryParameter("targetId", targetId)//设置私聊会话是否聚合显示,targetId:单聊人的id
                            .build();
                    ConversationFragment conversationFragment = new ConversationFragment();
                    conversationFragment.setUri(uri);
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.container, conversationFragment);
                    transaction.commit();
                }

                @Override
                public void onError(RongIMClient.ConnectionErrorCode errorCode) {
                    if (errorCode.equals(RongIMClient.ConnectionErrorCode.RC_CONN_TOKEN_INCORRECT)) {
                        //从 APP 服务获取新 token，并重连
                    } else {
                        //无法连接 IM 服务器，请根据相应的错误码作出对应处理
                        Log.e("IM连接失败", errorCode + "撒大山东");
                    }
                }
            });

        }else {
//            Log.e("回话界面123",targetId+"---");
            Uri uri = Uri.parse("rong://" +
                    getApplicationContext().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversation").appendPath("private")
                    .appendQueryParameter("targetId", targetId)//设置私聊会话是否聚合显示,targetId:单聊人的id
                    .build();
            ConversationFragment conversationFragment = new ConversationFragment();
            conversationFragment.setUri(uri);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, conversationFragment);
            transaction.commit();
        }

//
    }
}