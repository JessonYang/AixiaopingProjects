package com.weslide.lovesmallscreen.view_yy.customview;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.NfcpModel;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by YY on 2017/6/10.
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (path instanceof ImageText) {
            ImageText imageText = (ImageText) path;
            Glide.with(context).load(imageText.getImage()).into(imageView);
        } else if (path instanceof NfcpModel) {
            NfcpModel model = (NfcpModel) path;
            Glide.with(context).load(model.getImage()).into(imageView);
        } else if (path instanceof String) {
//            Picasso.with(context).load(((String) path)).into(imageView);
            Glide.with(context).load(path).into(imageView);
        }
    }
}
