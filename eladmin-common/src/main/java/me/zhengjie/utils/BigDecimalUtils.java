/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zhengjie.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Zheng Jie
 * @description 计算类
 * @date 2024-12-27
 **/
public class BigDecimalUtils {

    /**
     * 将对象转换为 BigDecimal
     * @param obj 输入对象
     * @return 转换后的 BigDecimal
     */
    private static BigDecimal toBigDecimal(Object obj) {
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        } else if (obj instanceof Long) {
            return BigDecimal.valueOf((Long) obj);
        } else if (obj instanceof Integer) {
            return BigDecimal.valueOf((Integer) obj);
        } else if (obj instanceof Double) {
            return new BigDecimal(String.valueOf(obj));
        } else {
            throw new IllegalArgumentException("Unsupported type");
        }
    }

    /**
     * 加法
     * @param a 加数
     * @param b 加数
     * @return 两个加数的和，保留两位小数
     */
    public static BigDecimal add(Object a, Object b) {
        BigDecimal bdA = toBigDecimal(a);
        BigDecimal bdB = toBigDecimal(b);
        return bdA.add(bdB).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 减法
     * @param a 被减数
     * @param b 减数
     * @return 两数的差，保留两位小数
     */
    public static BigDecimal subtract(Object a, Object b) {
        BigDecimal bdA = toBigDecimal(a);
        BigDecimal bdB = toBigDecimal(b);
        return bdA.subtract(bdB).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 乘法
     * @param a 乘数
     * @param b 乘数
     * @return 两个乘数的积，保留两位小数
     */
    public static BigDecimal multiply(Object a, Object b) {
        BigDecimal bdA = toBigDecimal(a);
        BigDecimal bdB = toBigDecimal(b);
        return bdA.multiply(bdB).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 除法
     * @param a 被除数
     * @param b 除数
     * @return 两数的商，保留两位小数
     */
    public static BigDecimal divide(Object a, Object b) {
        BigDecimal bdA = toBigDecimal(a);
        BigDecimal bdB = toBigDecimal(b);
        return bdA.divide(bdB, 2, RoundingMode.HALF_UP);
    }

    /**
     * 除法
     * @param a 被除数
     * @param b 除数
     * @param scale 保留小数位数
     * @return 两数的商，保留两位小数
     */
    public static BigDecimal divide(Object a, Object b, int scale) {
        BigDecimal bdA = toBigDecimal(a);
        BigDecimal bdB = toBigDecimal(b);
        return bdA.divide(bdB, scale, RoundingMode.HALF_UP);
    }

    /**
     * 分转元
     * @param obj 分的金额
     * @return 转换后的元，保留两位小数
     */
    public static BigDecimal centsToYuan(Object obj) {
        BigDecimal cents = toBigDecimal(obj);
        return cents.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    /**
     * 元转分
     * @param obj 元的金额
     * @return 转换后的分
     */
    public static Long yuanToCents(Object obj) {
        BigDecimal yuan = toBigDecimal(obj);
        return yuan.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).longValue();
    }
    
    public static void main(String[] args) {
        BigDecimal num1 = new BigDecimal("10.123");
        BigDecimal num2 = new BigDecimal("2.456");

        System.out.println("加法结果: " + add(num1, num2));
        System.out.println("减法结果: " + subtract(num1, num2));
        System.out.println("乘法结果: " + multiply(num1, num2));
        System.out.println("除法结果: " + divide(num1, num2));

        Long cents = 12345L;
        System.out.println("分转元结果: " + centsToYuan(cents));

        BigDecimal yuan = new BigDecimal("123.45");
        System.out.println("元转分结果: " + yuanToCents(yuan));
    }
}
