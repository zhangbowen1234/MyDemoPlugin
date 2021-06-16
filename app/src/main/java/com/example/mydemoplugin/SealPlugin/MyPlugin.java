package com.example.mydemoplugin.SealPlugin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mydemoplugin.PhoneInfoProvider.RedPackageMessage;

import java.util.Random;

import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * Created by zhangbowen on 6/15/21.
 * 自定义红包Plugin
 **/
public class MyPlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, io.rong.imkit.R.drawable.rc_cs_evaluate_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return "红包";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        Log.e("发红包啦！！！", rongExtension.getTargetId());
        RedPackageMessage redPackageMessage = RedPackageMessage.obtain("测试" + new Random().nextInt(1000), "商店名称" + new Random().nextInt(1000), "描述" + new Random().nextInt(1000), "描述" + new Random().nextInt(1000));
        RongIM.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, rongExtension.getTargetId(), redPackageMessage, "测试一下pushContent" + new Random().nextInt(100), "测试一下pushData" + new Random().nextInt(100)
                , new IRongCallback.ISendMediaMessageCallback() {
                    @Override
                    public void onAttached(Message message) {

                    }

                    @Override
                    public void onSuccess(Message message) {

                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {

                    }

                    @Override
                    public void onProgress(Message message, int i) {

                    }

                    @Override
                    public void onCanceled(Message message) {

                    }
                });
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {
        Log.e("发红包啦！！！", "onActivityResult");
    }
}