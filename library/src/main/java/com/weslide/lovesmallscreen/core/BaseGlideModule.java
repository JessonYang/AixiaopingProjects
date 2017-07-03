package com.weslide.lovesmallscreen.core;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpGlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.weslide.lovesmallscreen.utils.L;

/**
 * Created by xu on 2016/7/26.
 * Glide核心配置
 */
public class BaseGlideModule extends OkHttpGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        L.e("defaultMemoryCacheSize-" + defaultMemoryCacheSize / 1024 / 1024 + ", defaultBitmapPoolSize" + defaultBitmapPoolSize / 1024 / 1024);

        int customMemoryCacheSize = (int) (0.5 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (0.5 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        int cacheSize100MegaBytes = 104857600;

        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes)
        );

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
