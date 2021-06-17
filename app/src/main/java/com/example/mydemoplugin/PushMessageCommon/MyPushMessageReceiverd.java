package com.example.mydemoplugin.PushMessageCommon;

import android.content.Context;
import android.util.Log;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by zhangbowen on 6/17/21.
 **/
public class MyPushMessageReceiverd extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
//        Log.e("推送onNotificati",pushType+"----"+pushNotificationMessage.getTargetId());
        return false;
    }
}