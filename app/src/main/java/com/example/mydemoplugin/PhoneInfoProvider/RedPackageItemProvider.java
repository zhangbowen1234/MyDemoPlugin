package com.example.mydemoplugin.PhoneInfoProvider;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mydemoplugin.R;

import io.rong.imkit.RongIM;
import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.utilities.OptionsPopupDialog;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Created by zhangbowen on 6/15/21.
 * desc新建一个消息类继承 IContainerItemProvider.MessageProvider 类，实现对应接口方法，
 * 1.注意开头的注解！
 * 2.注意泛型！
 */
@ProviderTag(
        messageContent = RedPackageMessage.class,
        showReadState = true
)
public class RedPackageItemProvider extends IContainerItemProvider.MessageProvider<RedPackageMessage> {

    public RedPackageItemProvider() {
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        //这就是展示在会话界面的自定义的消息的布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_redpackage_message, null);
        ViewHolder holder = new ViewHolder();
        holder.ll_msg = (FrameLayout) view.findViewById(R.id.ll_msg);
        holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
        holder.tvStoreName = (TextView) view.findViewById(R.id.tv_store_name);
        holder.tvDesc1 = (TextView) view.findViewById(R.id.tv_desc1);
        holder.tvDesc2 = (TextView) view.findViewById(R.id.tv_desc2);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, RedPackageMessage redPackageMessage, UIMessage message) {

        //根据需求，适配数据
        ViewHolder holder = (ViewHolder) view.getTag();
        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
//            holder.ll_msg.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
        } else {
//            holder.ll_msg.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
        }
//        AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
        holder.tvTitle.setText(redPackageMessage.getTitle());
        holder.tvStoreName.setText(redPackageMessage.getStoreName());
        holder.tvDesc1.setText(redPackageMessage.getDesc1());
        holder.tvDesc2.setText(redPackageMessage.getDesc2());
    }

    @Override
    public Spannable getContentSummary(RedPackageMessage redPackageMessage) {
        return new SpannableString(redPackageMessage.getDesc1());
    }

    @Override
    public void onItemClick(View view, int i, RedPackageMessage redPackageMessage, UIMessage uiMessage) {

    }

    @Override
    public void onItemLongClick(View view, int i, RedPackageMessage redPackageMessage, UIMessage uiMessage) {
        //实现长按删除等功能，咱们直接复制融云其他provider的实现
        String[] items1;//复制，删除
        items1 = new String[]{view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_copy), view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_delete)};

        OptionsPopupDialog.newInstance(view.getContext(), items1).setOptionsPopupDialogListener(new OptionsPopupDialog.OnOptionsItemClickedListener() {
            public void onOptionsItemClicked(int which) {
//                Log.e("实现长按删除等功能",uiMessage.getMessage().getMessageId()+"---"+redPackageMessage.describeContents()+"-----"+uiMessage.getMessage());
                //长安复制暂时没有实现
                if (which == 0) {
                    ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
//                    clipboard.setText(redPackageMessage.encode());//这里是自定义消息的消息属性
                } else if (which == 1) {
                    RongIM.getInstance().deleteMessages(new int[]{uiMessage.getMessage().getMessageId()}, (RongIMClient.ResultCallback) null);
                }
            }
        }).show();

    }

    private static class ViewHolder {
        TextView tvTitle, tvStoreName, tvDesc1, tvDesc2;
        FrameLayout ll_msg;
    }
}