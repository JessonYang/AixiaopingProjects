package com.weslide.lovesmallscreen.managers;

import android.database.sqlite.SQLiteDatabase;

import com.weslide.lovesmallscreen.managers.DownloadDao;
import com.weslide.lovesmallscreen.models.DownloadEntity;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by Administrator on 2016/8/8.
 */
public class MyDaoSession extends AbstractDaoSession {
    private final DaoConfig downloadDaoConfig;

    private final DownloadDao downloadDao;

    public MyDaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        downloadDaoConfig = daoConfigMap.get(DownloadDao.class).clone();
        downloadDaoConfig.initIdentityScope(type);

        downloadDao = new DownloadDao(downloadDaoConfig, this);

        registerDao(DownloadEntity.class, downloadDao);
    }

    public void clear() {
        downloadDaoConfig.getIdentityScope().clear();
    }

    public DownloadDao getDownloadDao() {
        return downloadDao;
    }
}
