package com.weslide.lovesmallscreen.core;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.weslide.lovesmallscreen.ArchitectureAppliation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by xu on 2016/4/26.
 * 用于封装简化Dao的实现
 *
 * 实体类必须存在id属性
 */
public abstract class BaseDao<T> extends AbstractDao<T, Long> {

    private static final String TAG = "BASEDAO";

    private static Map<String, Class> cacheReflectClass = new HashMap<String, Class>();
    private static Map<String, Field> cacheReflectField = new HashMap<String, Field>();
    List<Property> propertys = new ArrayList<>();

    public BaseDao(DaoConfig config) {
        this(config, null);
    }

    public BaseDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);

        try {
            propertys = reflectProperties(this.getClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取表名
     * @return
     */
    public abstract String getTableName();

    /**
     * 获取数据的承载类
     * @return
     */
    public abstract Class getEntityClass();

    @Override
    protected T readEntity(Cursor cursor, int offset) {

        T t = null;
        try {
            t = (T) getEntityClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        readEntity(cursor, t, offset);


        return t;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        for (Property property: propertys
             ) {
            if(property.primaryKey){
                return cursor.getLong(property.ordinal);
            }
        }
        return null;
    }

    /**
     * 查询最后一条数据
     * @return
     */
    public T loadLastRow() {
        Cursor cursor = ArchitectureAppliation.getDaoSession().getDatabase()
                .rawQuery(statements.getSelectAll() + " ORDER BY "+getPkProperty().columnName+" DESC LIMIT 0,1", null);
        if(cursor.moveToNext()){
            return readEntity(cursor, pkOrdinal);
        }

        return  null;
    }

    /**
     * 查询第一条数据
     * @return
     */
    public T loadFirstRow(){
        Cursor cursor = ArchitectureAppliation.getDaoSession().getDatabase()
                .rawQuery(statements.getSelectAll() + " LIMIT 0,1", null);
        if(cursor.moveToNext()){
            return readEntity(cursor, pkOrdinal);
        }

        return  null;
    }

    @Override
    protected void readEntity(Cursor cursor, T entity, int offset) {
        try {
            for (Property property: propertys ) {
                if(property.primaryKey){
                    getCacheField(property, entity.getClass()).set(entity, cursor.getLong( offset + property.ordinal));
                } else {
                    String value =  cursor.getString( offset + property.ordinal);
                    getCacheField(property, entity.getClass()).set(entity, cursor.getString( offset + property.ordinal));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, T entity) {
        for (Property property: propertys ) {
            try {
                if(!property.primaryKey){
                    String value = (String) getCacheField(property, entity.getClass()).get(entity);
                    if(value != null){
                        stmt.bindString(property.ordinal + 1, value);
                    }
                } else {
                    Object id = getCacheField(property, entity.getClass()).get(entity);
                    if(id != null){
                        stmt.bindLong(property.ordinal + 1, (Long) id);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected Long updateKeyAfterInsert(T entity, long rowId) {
        Log.e("test", "rowId:" + rowId);
        for (Property property: propertys
                ) {
            if(property.primaryKey){
                try {
                    getCacheField(property, entity.getClass()).set(entity, rowId );
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return rowId;
    }

    public Field getCacheField(Property property, Class cla){
        try {
            if(!cacheReflectField.containsKey(getTableName()+property.name)){

                    cacheReflectField.put(getTableName()+property.name,
                            cla.getDeclaredField(property.name));
                    cacheReflectField.get(getTableName()+property.name).setAccessible(true);
                    return cacheReflectField.get(getTableName()+property.name);

            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
        return cacheReflectField.get(getTableName()+property.name);
    }

    @Override
    protected Long getKey(T entity) {
        for (Property property: propertys
                ) {
            if(property.primaryKey){
                try {
                    return (Long) getCacheField(property, entity.getClass()).get(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return null;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return false;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists,Class<? extends BaseDao> daoClass, String tableName) {

        List<Property> propertys = null;
        try {
            propertys = reflectProperties(daoClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        String constraint = ifNotExists? "IF NOT EXISTS ": "";

        StringBuilder createTableSql = new StringBuilder();
        createTableSql.append("CREATE TABLE " + constraint + "\""+tableName+"\" (");
        for (int i=0; i < propertys.size(); i ++){
            if(i != 0){
                createTableSql.append(",");
            }
            createTableSql.append("\""+ propertys.get(i).columnName +"\"");

            //判断类型
            if(propertys.get(i).type.getName().equals(String.class.getName())){
                createTableSql.append(" TEXT ");
            } else if(propertys.get(i).type.getName().equals(Long.class.getName()) ||
                    propertys.get(i).type.getName().equals(Integer.class.getName()) ||
                    propertys.get(i).type.getName().equals(int.class.getName()) ||
                    propertys.get(i).type.getName().equals(long.class.getName())){
                createTableSql.append(" INTEGER ");
            } else {
                createTableSql.append(" TEXT ");
            }

            //判断主键
            if(propertys.get(i).primaryKey){
                createTableSql.append(" PRIMARY KEY ");
            }
        }

        createTableSql.append(");");

        Log.e(TAG, "生成的SQL:" + createTableSql.toString());
        db.execSQL(createTableSql.toString());

    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists, String tableName) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\""+ tableName +"\"";
        db.execSQL(sql);
    }

    private static List<Property> reflectProperties(Class<? extends BaseDao> daoClass)
            throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        Class<?> propertiesClass = Class.forName(daoClass.getName() + "$Properties");
        Field[] fields = propertiesClass.getDeclaredFields();

        ArrayList<Property> propertyList = new ArrayList<Property>();
        final int modifierMask = Modifier.STATIC | Modifier.PUBLIC;
        for (Field field : fields) {
            // There might be other fields introduced by some tools, just ignore them (see issue #28)
            if ((field.getModifiers() & modifierMask) == modifierMask) {
                Object fieldValue = field.get(null);
                if (fieldValue instanceof Property) {
                    propertyList.add((Property) fieldValue);
                }
            }
        }

        return propertyList;
    }


}
