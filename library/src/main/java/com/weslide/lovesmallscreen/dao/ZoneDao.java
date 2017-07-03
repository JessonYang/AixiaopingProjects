package com.weslide.lovesmallscreen.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.core.BaseDao;
import com.weslide.lovesmallscreen.models.Zone;
import com.weslide.lovesmallscreen.utils.L;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by xu on 2016/7/11.
 * 城市列表持久化
 */
public class ZoneDao extends BaseDao<Zone> {

    public static String TABLENAME = "ZONE_TABLE_NAME";

    public ZoneDao(DaoConfig config) {
        super(config);
    }

    public ZoneDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {

        public final static Property id = new Property(0, Long.class, "id", true, "_id");
        public final static Property level = new Property(1, String.class, "level", false, "level");
        public final static Property name = new Property(2, String.class, "name", false, "name");
        public final static Property englishChar = new Property(3, String.class, "englishChar", false, "englishChar");
        public final static Property zoneId = new Property(4, String.class, "zoneId", false, "zoneId");
        public final static Property parentZoneId = new Property(5, String.class, "parentZoneId", false, "parentZoneId");

    }


    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        createTable(db,ifNotExists, ZoneDao.class, TABLENAME);
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        dropTable(db, ifExists, TABLENAME);
    }

    /**
     * 加载所有城市
     * @return
     */
    public List<Zone> loadAllCity(){
        Cursor cursor = ArchitectureAppliation.getDaoSession().getDatabase()
                .rawQuery(statements.getSelectAll() + " where level=? ORDER BY englishChar ASC", new String[]{"2"});
        List<Zone> zones = new ArrayList<>();

        while (cursor.moveToNext()) {
            zones.add(readEntity(cursor, pkOrdinal));
        }

        return  zones;
    }

    /**
     * 通过zoneId获取城市信息
     * @param zoneId
     * @return
     */
    public Zone loadCityByZoneId(String zoneId){
        Cursor cursor = ArchitectureAppliation.getDaoSession().getDatabase()
                .rawQuery(statements.getSelectAll() + " where level=? and zoneId=?", new String[]{"2" ,zoneId});
        Zone zone = new Zone();

        if (cursor.moveToNext()) {
            readEntity(cursor, zone, pkOrdinal);
        }

        return  zone;
    }

    /**
     * 通过name获取地区信息
     * @param zoneName
     * @return
     */
    public Zone loadDistrictByZoneName(String cityName ,String zoneName){
        String sql = statements.getSelectAll() + " where level=? and name=? and parentZoneId=(select zoneId from ZONE_TABLE_NAME where level=2 and name=?)";
        Cursor cursor = ArchitectureAppliation.getDaoSession().getDatabase()
                .rawQuery(sql , new String[]{"3" ,zoneName, cityName});
        Zone zone = new Zone();

        L.e("一共有" + cursor.getCount()+"条数据");

        if (cursor.moveToNext()) {
            readEntity(cursor, zone, pkOrdinal);
        }

        return  zone;
    }



    /**
     * 加载城市的所有地区
     * @param parentZoneId
     * @return
     */
    public List<Zone> loadDistrictList(String parentZoneId){
        Cursor cursor = ArchitectureAppliation.getDaoSession().getDatabase()
                .rawQuery(statements.getSelectAll() + " where level=? and parentZoneId=? ORDER BY englishChar ASC", new String[]{"3", parentZoneId});
        List<Zone> zones = new ArrayList<>();

        while (cursor.moveToNext()) {
            zones.add(readEntity(cursor, pkOrdinal));
        }

        return  zones;
    }


    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    public Class getEntityClass() {
        return Zone.class;
    }
}
