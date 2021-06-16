package com.example.mydemoplugin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import io.rong.imkit.fragment.ConversationFragment;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ConversationFragment conversationFragment = new ConversationFragment();
        Intent intent = getIntent(); // 取得从上一个Activity当中传递过来的Intent对象
        String targetId = null;
        if (intent != null) {
            targetId = intent.getStringExtra("targetId");
        }
        Uri uri = Uri.parse("rong://" +
                getApplicationContext().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath("private")
                .appendQueryParameter("targetId", targetId)//设置私聊会话是否聚合显示,targetId:单聊人的id
                .build();

        conversationFragment.setUri(uri);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, conversationFragment);
        transaction.commit();
    }
}