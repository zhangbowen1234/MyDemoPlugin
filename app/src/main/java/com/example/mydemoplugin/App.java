package com.example.mydemoplugin;

/**
 * Created by zhangbowen on 6/8/21.
 **/

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import com.example.mydemoplugin.PhoneInfoProvider.RedPackageMessage;
import com.example.mydemoplugin.PhoneInfoProvider.RedPackageItemProvider;
import com.example.mydemoplugin.SealPlugin.SealExtensionModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;


/**
 * 应用启动时，判断用户是否已接受隐私协议，如果已接受，正常初始化；否则跳转到隐私授权页面请求用户授权。
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        if (!AppContext.isInitialized()) {
//            AppContext.init(getApplicationContext());
//        }
        //用户已接受隐私协议，进行初始化
        String appKey = "pvxdm17jpws8r";
        //            第一个参数必须传应用上下文
        RongIM.init(this.getApplicationContext(), appKey);
        //注册Plugin自定义类型
        registerExtensionPlugin();
        //消息自定义类型
        RongIM.registerMessageType(RedPackageMessage.class);
        RongIM.registerMessageTemplate(new RedPackageItemProvider());

        ArrayList<Friend> friendList = new ArrayList<Friend>();
        friendList.add(new Friend(999 + "", "bowen", "https://c-ssl.duitang.com/uploads/item/201412/01/20141201173308_skFWP.png"));
        friendList.add(new Friend(100 + "", "张三100", "https://c-ssl.duitang.com/uploads/item/201812/30/20181230144326_yzofb.thumb.1000_0.jpg"));
        friendList.add(new Friend(101 + "", "李四101", "https://c-ssl.duitang.com/uploads/item/201510/15/20151015064025_BRHAr.jpeg"));
        String token = "hsE5f3CXliULgTPr/USXNazb39xdkVBWcXOhWOHAV2A=@4hjh.cn.rongnav.com;4hjh.cn.rongcfg.com";//999
        //        String token = "O4L/5+74YrULgTPr/USXNeFYA/kXKPn1cXOhWOHAV2A=@4hjh.cn.rongnav.com;4hjh.cn.rongcfg.com";//100
        //                String token = "av3eoNpwnS8LgTPr/USXNcG1m+hLytMncXOhWOHAV2A=@4hjh.cn.rongnav.com;4hjh.cn.rongcfg.com";//101
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {
                //消息数据库打开，可以进入到主页面
            }

            @Override
            public void onSuccess(String s) {
                //连接成功
                Log.i("IM连接成功", s);
                //                1。会话列表
//                Map<String, Boolean> supportedConversation = new HashMap<>();
//                supportedConversation.put(String.valueOf(Conversation.ConversationType.PRIVATE.getValue()), false);
//                supportedConversation.put(String.valueOf(Conversation.ConversationType.GROUP.getValue()), false);
//                supportedConversation.put(String.valueOf(RongPushClient.ConversationType.SYSTEM.getValue()), true);
//                RongIM.getInstance().startConversationList(getApplicationContext(), supportedConversation);
                //                2。聊天页面
                //                                Intent intent = new Intent(mContext, ConversationActivity.class);
                //                                mContext.startActivity(intent);
                //同步设置
                // 是否缓存用户信息. true 缓存, false 不缓存
                // 1. <span style="color:red">当设置 true 后, 优先从缓存中获取用户信息.
                // 2. 更新用户信息, 需调用 RongIM.getInstance().refreshUserInfoCache(userInfo)
                //                boolean isCacheUserInfo = true;
                //                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                //
                //                    /**
                //                     * 获取设置用户信息. 通过返回的 userId 来封装生产用户信息.
                //                     * @param userId 用户 ID
                //                     */
                //                    @Override
                //                    public UserInfo getUserInfo(String userId) {
                //                        Log.i("获取设置用户信息---", userId);
                //                        UserInfo userInfo = new UserInfo(userId, "张三100", Uri.parse("https://c-ssl.duitang.com/uploads/item/201812/30/20181230144326_yzofb.thumb.1000_0.jpg"));
                //                        return userInfo;
                //                    }
                //
                //                }, isCacheUserInfo);

                //3。用户信息提供者异步设置
                // 是否缓存用户信息. true 缓存, false 不缓存
                // 1. <span style="color:red">当设置 true 后, 优先从缓存中获取用户信息.
                // 2. 更新用户信息, 需调用 RongIM.getInstance().refreshUserInfoCache(userInfo)
                boolean isCacheUserInfo = true;
                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

                    /**
                     * 获取设置用户信息. 通过返回的 userId 来封装生产用户信息.
                     * @param userId 用户 ID
                     */
                    @Override
                    public UserInfo getUserInfo(String userId) {
                        // 执行异步请求逻辑方法
                        //                                                UserInfo userInfo = new UserInfo(userId, "张三100", Uri.parse("https://c-ssl.duitang.com/uploads/item/201812/30/20181230144326_yzofb.thumb.1000_0.jpg"));
                        //                //                                UserInfo userInfo = new UserInfo(userId, "userId 对应的名称", Uri.parse("userId 对应的头像地址"));
                        //                                                RongIM.getInstance().refreshUserInfoCache(userInfo);
                        Log.i("获取设置用户信息---", userId);
                        for (Friend i : friendList) {
                            if (i.getUserId().equals(userId)) {
                                return new UserInfo(i.getUserId(), i.getUserName(), Uri.parse(i.getPortraitUri()));
                            }
                        }
                        return null;
                    }

                }, isCacheUserInfo);

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
    }

    private void registerExtensionPlugin() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                //移除已注册的默认模块，替换成自定义模块RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new SealExtensionModule());
            }
        }
    }
}
