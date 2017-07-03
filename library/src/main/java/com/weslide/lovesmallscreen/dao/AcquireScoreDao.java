package com.weslide.lovesmallscreen.dao;

import android.database.sqlite.SQLiteDatabase;

import com.weslide.lovesmallscreen.core.BaseDao;
import com.weslide.lovesmallscreen.models.AcquireScore;

import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by xu on 2016/5/25.
 * 获得的积分持久化操作
 */
public class AcquireScoreDao extends BaseDao<AcquireScore> {
    public AcquireScoreDao(DaoConfig config) {
        super(config);
    }

    public AcquireScoreDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    public static String TABLENAME = "ACQUIRE_SCORE";

    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property id = new Property(0, Long.class, "id", true, "_id");
        public final static Property acquireTime = new Property(1, String.class, "acquireTime", false, "acquireTime");
        public final static Property advertId = new Property(2, String.class, "advertId", false, "advertId");
        public final static Property type = new Property(3, String.class, "type", false, "type");
        public final static Property score = new Property(4, String.class, "score", false, "score");
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        createTable(db,ifNotExists,AcquireScoreDao.class, TABLENAME);
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        dropTable(db, ifExists, TABLENAME);
    }

    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    public Class getEntityClass() {
        return AcquireScore.class;
    }
}
