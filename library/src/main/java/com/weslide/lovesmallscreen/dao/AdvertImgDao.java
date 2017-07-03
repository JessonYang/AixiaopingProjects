package com.weslide.lovesmallscreen.dao;

import android.database.sqlite.SQLiteDatabase;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseDao;
import com.weslide.lovesmallscreen.models.AdvertImg;

import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by xu on 2016/5/30.
 * 存储滑屏广告图片
 */
public class AdvertImgDao extends BaseDao<AdvertImg> {

    public static String TABLENAME = "ADVERT_IMG";

    public AdvertImgDao(DaoConfig config) {
        super(config);
    }

    public AdvertImgDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {

        public final static Property id = new Property(0, Long.class, "id", true, "_id");
        public final static Property advertId = new Property(1, String.class, "advertId", false, "advertId");
        public final static Property level = new Property(2, String.class, "level", false, "level");
        public final static Property image = new Property(3, String.class, "image", false, "image");
        public final static Property uri = new Property(4, String.class, "uri", false, "uri");
        public final static Property sellerId = new Property(5, String.class, "sellerId", false, "sellerId");
        public final static Property acquireSocre = new Property(6, String.class, "acquireSocre", false, "acquireSocre");
        public final static Property acquireSocreNumber = new Property(7, String.class, "acquireSocreNumber", false, "acquireSocreNumber");
        public final static Property imageFile = new Property(8, String.class, "imageFile", false, "imageFile");
    }

    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    public Class getEntityClass() {
        return AdvertImg.class;
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        createTable(db,ifNotExists,AdvertImgDao.class, TABLENAME);
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        dropTable(db, ifExists, TABLENAME);
    }
}
