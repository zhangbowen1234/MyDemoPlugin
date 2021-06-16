package com.example.mydemoplugin;

/**
 * Created by zhangbowen on 6/8/21.
 **/

import android.app.Application;
import android.content.Context;

import com.example.mydemoplugin.PhoneInfoProvider.RedPackageMessage;
import com.example.mydemoplugin.PhoneInfoProvider.RedPackageItemProvider;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;


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
