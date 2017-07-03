package com.weslide.lovesmallscreen.core;

import android.database.sqlite.SQLiteDatabase;

import com.weslide.lovesmallscreen.dao.AcquireScoreDao;
import com.weslide.lovesmallscreen.dao.AdvertImgDao;
import com.weslide.lovesmallscreen.dao.IpInfoDao;
import com.weslide.lovesmallscreen.dao.LocationDao;
import com.weslide.lovesmallscreen.dao.ZoneDao;
import com.weslide.lovesmallscreen.models.AdvertImg;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * GreenDao DaoSession
 * 用于创建和管理Dao
 * xu 2016/4/25.
 */
public class DaoSession extends AbstractDaoSession {

    DaoConfig locationDaoConfig;
    DaoConfig ipinfoDaoConfig;
    DaoConfig acquireScoreDaoConfig;
    DaoConfig advertImgDaoConfig;
    DaoConfig zoneDaoConfig;

    private LocationDao locationDao;
    private IpInfoDao ipInfoDao;
    private AcquireScoreDao acquireScoreDao;
    private AdvertImgDao advertImgDao;
    private ZoneDao zoneDao;


    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        zoneDaoConfig = daoConfigMap.get(ZoneDao.class).clone();
        zoneDaoConfig.initIdentityScope(type);

        locationDaoConfig = daoConfigMap.get(LocationDao.class).clone();
        locationDaoConfig.initIdentityScope(type);

        ipinfoDaoConfig = daoConfigMap.get(IpInfoDao.class).clone();
        ipinfoDaoConfig.initIdentityScope(type);

        acquireScoreDaoConfig = daoConfigMap.get(AcquireScoreDao.class).clone();
        acquireScoreDaoConfig.initIdentityScope(type);

        advertImgDaoConfig = daoConfigMap.get(AdvertImgDao.class).clone();
        advertImgDaoConfig.initIdentityScope(type);

        locationDao = new LocationDao(locationDaoConfig, this);
        ipInfoDao = new IpInfoDao(ipinfoDaoConfig, this);
        acquireScoreDao = new AcquireScoreDao(acquireScoreDaoConfig, this);
        advertImgDao = new AdvertImgDao(advertImgDaoConfig, this);
        zoneDao = new ZoneDao(zoneDaoConfig, this);

        registerDao(LocationDao.class, (AbstractDao) getLocationDao());
        registerDao(IpInfoDao.class, (AbstractDao) getIpInfoDao());
        registerDao(AcquireScoreDao.class, (AbstractDao) getAcquireScoreDao());
        registerDao(AdvertImgDao.class, (AbstractDao) getAdvertImgDao());
        registerDao(ZoneDao.class, (AbstractDao) getZoneDao());
    }


    public LocationDao getLocationDao() {
        return locationDao;
    }

    public IpInfoDao getIpInfoDao() {
        return ipInfoDao;
    }

    public AcquireScoreDao getAcquireScoreDao(){ return acquireScoreDao;}

    public AdvertImgDao getAdvertImgDao(){return  advertImgDao;}

    public ZoneDao getZoneDao(){return  zoneDao;}
}
