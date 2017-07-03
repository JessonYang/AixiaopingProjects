package com.weslide.lovesmallscreen.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by xu on 2016/5/6.
 * 操作SharedPreferences的统一管理类
 */
public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String fileName, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    public static void put(Context context, String key, Object object) {
        put(context, FILE_NAME, object);
    }

    /**
     * 同时保存多个数据
     *
     * @param context
     * @param fileName
     * @param values
     */
    public static void puts(Context context, String fileName, Map<String, ?> values) {

        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        for (String key : values.keySet()) {
            Object object = values.get(key);

            if (object instanceof String) {
                editor.putString(key, object == null ? null : (String) object);
            } else if (object instanceof Integer) {
                editor.putInt(key, object == null ? null : (Integer) object);
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, object == null ? null : (Boolean) object);
            } else if (object instanceof Float) {
                editor.putFloat(key, object == null ? null : (Float) object);
            } else if (object instanceof Long) {
                editor.putLong(key, object == null ? null : (Long) object);
            } else {
                editor.putString(key, object == null ? null : object.toString());
            }
        }

        SharedPreferencesCompat.apply(editor);
    }

    public static void puts(Context context, Map<String, ?> values) {
        puts(context, FILE_NAME, values);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String fileName, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public static Object get(Context context, String key, Object defaultObject) {
        return get(context, FILE_NAME, key, defaultObject);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public static void remove(Context context, String key) {
        remove(context, FILE_NAME, key);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    public static void clear(Context context) {
        clear(context, FILE_NAME);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static boolean contains(Context context, String key) {
        return contains(context, FILE_NAME);
    }

    public static String getValueByMapToString(Map<String, ?> values, String key, String defaultValue) {
        Object value = values.get(key);
        return value == null ? defaultValue : value.toString();
    }

    public static int getValueByMapToInteger(Map<String, ?> values, String key, int defaultValue) {
        Object value = values.get(key);
        return value == null ? defaultValue : Integer.parseInt(value.toString());
    }

    public static long getValueByMapToLong(Map<String, ?> values, String key, long defaultValue) {
        Object value = values.get(key);
        return value == null ? defaultValue : Long.parseLong(value.toString());
    }

    public static boolean getValueByMapToBoolean(Map<String, ?> values, String key, boolean defaultValue) {
        Object value = values.get(key);
        return value == null ? defaultValue : Boolean.parseBoolean(value.toString());
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    public static Map<String, ?> getAll(Context context) {
        return getAll(context, FILE_NAME);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
