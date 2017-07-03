package com.weslide.lovesmallscreen.dao;

import android.database.sqlite.SQLiteDatabase;

import com.weslide.lovesmallscreen.core.BaseDao;
import com.weslide.lovesmallscreen.models.demo.IpInfo;

import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by xu on 2016/5/4.
 * 在demo中使用
 */
public class IpInfoDao extends BaseDao<IpInfo> {

    public static String TABLENAME = "IPINFO";

    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property id = new Property(0, Long.class, "id", true, "_id");
        public final static Property country = new Property(1, String.class, "ip", false, "ip");
        public final static Property country_id = new Property(2, String.class, "country_id", false, "country_id");
        public final static Property area = new Property(3, String.class, "area", false, "area");
        public final static Property area_id = new Property(4, String.class, "area_id", false, "area_id");
        public final static Property region = new Property(5, String.class, "region", false, "region");
        public final static Property region_id = new Property(6, String.class, "region_id", false, "region_id");
        public final static Property city = new Property(7, String.class, "city", false, "city");
        public final static Property city_id = new Property(8, String.class, "city_id", false, "city_id");
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        createTable(db,ifNotExists,IpInfoDao.class, TABLENAME);
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        dropTable(db, ifExists, TABLENAME);
    }

    public IpInfoDao(DaoConfig config) {
        super(config);
    }

    public IpInfoDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    public Class getEntityClass() {
        return IpInfo.class;
    }
}
