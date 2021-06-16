package com.example.mydemoplugin.SealPlugin;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;

/**
 * Created by zhangbowen on 6/15/21.
 * Plugin自定义类型
 **/
public class SealExtensionModule extends DefaultExtensionModule {
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModuleList = new ArrayList<>();
        IPluginModule image = new ImagePlugin();
        IPluginModule location = new DefaultLocationPlugin();
        //        IPluginModule audio = new AudioPlugin();
        //        IPluginModule video = new VideoPlugin();
        IPluginModule file = new FilePlugin();
        IPluginModule myPlugin = new MyPlugin();

        if (conversationType.equals(Conversation.ConversationType.GROUP) ||
                conversationType.equals(Conversation.ConversationType.DISCUSSION) ||
                conversationType.equals(Conversation.ConversationType.PRIVATE)) {
            pluginModuleList.add(image);
            pluginModuleList.add(location);
            //            pluginModuleList.add(audio);
            //            pluginModuleList.add(video);
            pluginModuleList.add(file);
            pluginModuleList.add(myPlugin);
        } else {
            pluginModuleList.add(image);
        }

        return pluginModuleList;
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        return super.getEmoticonTabs();
    }
}
