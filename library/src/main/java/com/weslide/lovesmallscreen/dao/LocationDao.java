package com.weslide.lovesmallscreen.dao;

import android.database.sqlite.SQLiteDatabase;

import com.weslide.lovesmallscreen.core.BaseDao;
import com.weslide.lovesmallscreen.models.Location;

import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by xu on 2016/4/26.
 * 用于存储地图定位的数据
 */
public class LocationDao extends BaseDao<Location> {

    public static String TABLENAME = "LOCATION";

    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property id = new Property(0, Long.class, "id", true, "_id");
        public final static Property coordinateType = new Property(1, String.class, "coordinateType", false, "coordinateType");
        public final static Property time = new Property(2, String.class, "time", false, "time");
        public final static Property latitude = new Property(3, String.class, "latitude", false, "latitude");
        public final static Property longitude = new Property(4, String.class, "longitude", false, "longitude");
        public final static Property country = new Property(5, String.class, "country", false, "country");
        public final static Property countryCode = new Property(6, String.class, "countryCode", false, "countryCode");
        public final static Property province = new Property(7, String.class, "province", false, "province");
        public final static Property city = new Property(8, String.class, "city", false, "city");
        public final static Property cityCode = new Property(9, String.class, "cityCode", false, "cityCode");
        public final static Property district = new Property(10, String.class, "district", false, "district");
        public final static Property street = new Property(11, String.class, "street", false, "street");
        public final static Property streetNumber = new Property(12, String.class, "streetNumber", false, "streetNumber");
        public final static Property address = new Property(13, String.class, "address", false, "address");
    }


    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        createTable(db,ifNotExists, LocationDao.class, TABLENAME);
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        dropTable(db, ifExists, TABLENAME);
    }


    public LocationDao(DaoConfig config) {
        super(config);
    }

    public LocationDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    public Class getEntityClass() {
        return Location.class;
    }

}
