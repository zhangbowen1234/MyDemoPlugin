package com.example.mydemoplugin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    private Button id_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        id_click=findViewById(R.id.id_click);

        id_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Boolean> supportedConversation = new HashMap<>();
                supportedConversation.put(String.valueOf(Conversation.ConversationType.PRIVATE.getValue()), false);
                supportedConversation.put(String.valueOf(Conversation.ConversationType.GROUP.getValue()), false);
                supportedConversation.put(String.valueOf(RongPushClient.ConversationType.SYSTEM.getValue()), true);
                RongIM.getInstance().startConversationList(getApplicationContext(), supportedConversation);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().disconnect();
    }

}