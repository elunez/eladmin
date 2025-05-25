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
 * @description Calculation class
 * @date 2024-12-27
 **/
public class BigDecimalUtils {

    /**
     * Convert object to BigDecimal
     * @param obj Input object
     * @return Converted BigDecimal
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
     * Addition
     * @param a Addend
     * @param b Addend
     * @return Sum of two addends, rounded to two decimal places
     */
    public static BigDecimal add(Object a, Object b) {
        BigDecimal bdA = toBigDecimal(a);
        BigDecimal bdB = toBigDecimal(b);
        return bdA.add(bdB).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Subtraction
     * @param a Minuend
     * @param b Subtrahend
     * @return Difference of two numbers, rounded to two decimal places
     */
    public static BigDecimal subtract(Object a, Object b) {
        BigDecimal bdA = toBigDecimal(a);
        BigDecimal bdB = toBigDecimal(b);
        return bdA.subtract(bdB).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Multiplication
     * @param a Multiplier
     * @param b Multiplier
     * @return Product of two multipliers, rounded to two decimal places
     */
    public static BigDecimal multiply(Object a, Object b) {
        BigDecimal bdA = toBigDecimal(a);
        BigDecimal bdB = toBigDecimal(b);
        return bdA.multiply(bdB).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Division
     * @param a Dividend
     * @param b Divisor
     * @return Quotient of two numbers, rounded to two decimal places
     */
    public static BigDecimal divide(Object a, Object b) {
        BigDecimal bdA = toBigDecimal(a);
        BigDecimal bdB = toBigDecimal(b);
        return bdA.divide(bdB, 2, RoundingMode.HALF_UP);
    }

    /**
     * Division
     * @param a Dividend
     * @param b Divisor
     * @param scale Number of decimal places to keep
     * @return Quotient of two numbers, rounded to two decimal places
     */
    public static BigDecimal divide(Object a, Object b, int scale) {
        BigDecimal bdA = toBigDecimal(a);
        BigDecimal bdB = toBigDecimal(b);
        return bdA.divide(bdB, scale, RoundingMode.HALF_UP);
    }

    /**
     * Cents to Yuan
     * @param obj Amount in cents
     * @return Converted yuan, rounded to two decimal places
     */
    public static BigDecimal centsToYuan(Object obj) {
        BigDecimal cents = toBigDecimal(obj);
        return cents.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    /**
     * Yuan to Cents
     * @param obj Amount in yuan
     * @return Converted cents
     */
    public static Long yuanToCents(Object obj) {
        BigDecimal yuan = toBigDecimal(obj);
        return yuan.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).longValue();
    }
    
    public static void main(String[] args) {
        BigDecimal num1 = new BigDecimal("10.123");
        BigDecimal num2 = new BigDecimal("2.456");

        System.out.println("Addition result: " + add(num1, num2));
        System.out.println("Subtraction result: " + subtract(num1, num2));
        System.out.println("Multiplication result: " + multiply(num1, num2));
        System.out.println("Division result: " + divide(num1, num2));

        Long cents = 12345L;
        System.out.println("Cents to Yuan result: " + centsToYuan(cents));

        BigDecimal yuan = new BigDecimal("123.45");
        System.out.println("Yuan to Cents result: " + yuanToCents(yuan));
    }
}
