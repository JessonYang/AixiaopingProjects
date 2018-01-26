package com.weslide.lovesmallscreen.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 作者： YY 时间： 2017年12月2日 功能描述： 用于对数值的精确计算工具类
 */
public class CalcUtils {

    public static final int TYPE_ADD = 0x00; // 加法
    public static final int TYPE_MULTIPLY = 0x01; // 乘法
    public static final int TYPE_DIVIDE = 0x02; // 除法
    public static final int TYPE_SUBTRACT = 0x03; // 减法

    /**
     * 加法
     *
     * @param a
     * @param b
     * @return
     */
    public static Double add(Double a, Double b) {
        return calc(a, b, -1, TYPE_ADD, null);
    }

    /**
     * 减法
     *
     * @param a
     * @param b
     * @return
     */

    public static Double sub(Double a, Double b) {
        return calc(a, b, -1, TYPE_SUBTRACT, null);
    }

    /**
     * 乘法
     *
     * @param a
     * @param b
     * @return
     */

    public static Double multiply(Double a, Double b) {
        return calc(a, b, -1, TYPE_MULTIPLY, null);
    }

    /**
     * 除法
     *
     * @param a
     * @param b
     * @return
     */

    public static Double divide(Double a, Double b) {
        return calc(a, b, -1, TYPE_DIVIDE, null);
    }

    /**
     * 乘法
     *
     * @param a
     * @param b
     * @param scale 小数点后保留的位数
     * @param mode  保留的模式
     * @return
     */
    public static Double multiply(Double a, Double b, int scale, RoundingMode mode) {

        return calc(a, b, scale, TYPE_MULTIPLY, mode);
    }

    /**
     * 除法
     *
     * @param a
     * @param b
     * @param scale 小数点后保留的位数
     * @param mode  保留的模式
     * @return
     */
    public static Double divide(Double a, Double b, int scale, RoundingMode mode) {

        return calc(a, b, scale, TYPE_DIVIDE, mode);
    }

    /**
     * 计算
     *
     * @param a
     * @param b
     * @param scale
     * @param type
     * @param mode
     * @return
     */
    private static Double calc(Double a, Double b, int scale, int type, RoundingMode mode) {
        BigDecimal result = null;

        BigDecimal bgA = new BigDecimal(String.valueOf(a));
        BigDecimal bgB = new BigDecimal(String.valueOf(b));
        switch (type) {
            case TYPE_ADD:
                result = bgA.add(bgB);
                break;
            case TYPE_MULTIPLY:
                result = bgA.multiply(bgB);
                break;
            case TYPE_DIVIDE:
                try {
                    result = bgA.divide(bgB);
                } catch (ArithmeticException e) {// 防止无限循环而报错  采用四舍五入保留3位有效数字
                    result = bgA.divide(bgB, 3, RoundingMode.HALF_DOWN);
                }

                break;
            case TYPE_SUBTRACT:
                result = bgA.subtract(bgB);
                break;

        }
        if (mode == null) {
            if (scale != -1) {

                result = result.setScale(scale);
            }
        } else {
            if (scale != -1) {
                result = result.setScale(scale, mode);
            }
        }
        return result.doubleValue();
    }

    //两个Double数相加
    public static Double doubleAdd(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }

    //两个Double数相减
    public static Double doubleSub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }


    /*这里涉及到两个参数：Scale和RoundingMode
     Scale
     scale是用来对利用BigDecimal对数值进行运算后保留的位数。

     RouningMode
    该参数是BigDecimal是一个枚举类，包含有8个枚举类型，用来说明对经过计算后数值的取舍模式：
            - ROUND_UP：远离零方向舍入。向绝对值最大的方向舍入，只要舍弃位非0即进位。
            - ROUND_DOWN：趋向零方向舍入。向绝对值最小的方向输入，所有的位都要舍弃，不存在进位情况。
            - ROUND_CEILING：向正无穷方向舍入。向正最大方向靠拢。若是正数，舍入行为类似于ROUND_UP，若为负数，舍入行为类似于ROUND_DOWN。Math.round()方法就是使用的此模式。
            - ROUND_FLOOR：向负无穷方向舍入。向负无穷方向靠拢。若是正数，舍入行为类似于ROUND_DOWN；若为负数，舍入行为类似于ROUND_UP。
            - HALF_UP：最近数字舍入(5进)。这是我们最经典的四舍五入。
            - HALF_DOWN：最近数字舍入(5舍)。在这里5是要舍弃的。
            - HAIL_EVEN：银行家舍入法。*/
}