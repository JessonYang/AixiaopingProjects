package com.weslide.lovesmallscreen.view_yy.model;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.utils.AppUtils;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * Created by YY on 2017/11/9.
 */
@ProviderTag(messageContent = CustomizeMessage.class)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeMessage> {

    private Context context;

    @Override
    public void bindView(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {
        MyViewHolder holder = (MyViewHolder) view.getTag();
        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
            holder.msg_bg.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right_file);
        }else {
            holder.msg_bg.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
        }
        holder.good_desc.setText(customizeMessage.getContent());
        Glide.with(context).load(customizeMessage.getGoodPic()).into(holder.good_pic);
//        AndroidEmoji.ensure((Spannable) holder.good_desc.getText());//显示消息中的 Emoji 表情.
        holder.send_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "发送链接", Toast.LENGTH_SHORT).show();
                holder.send_link.setTextColor(Color.parseColor("#999999"));
                holder.send_link.setBackgroundResource(R.drawable.custom_msg_send_btn_bg_gray);
                holder.send_link.setEnabled(false);
            }
        });
        holder.send_link.setVisibility(View.GONE);
        holder.msg_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toGoods(context,customizeMessage.getGoodID());
            }
        });
    }

    @Override
    public Spannable getContentSummary(CustomizeMessage customizeMessage) {
        return new SpannableString(customizeMessage.getContent());
    }

    @Override
    public void onItemClick(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.custom_msg_provider_item, viewGroup, false);
        MyViewHolder holder = new MyViewHolder();
        holder.good_desc = (TextView) view.findViewById(R.id.good_desc);
        holder.good_pic = (ImageView) view.findViewById(R.id.good_pic);
        holder.send_link = (Button) view.findViewById(R.id.send_link_btn);
        holder.msg_bg = (RelativeLayout) view.findViewById(R.id.msg_bg);
        view.setTag(holder);
        return view;
    }

    class MyViewHolder{
        TextView good_desc;
        ImageView good_pic;
        Button send_link;
        RelativeLayout msg_bg;
    }
}
