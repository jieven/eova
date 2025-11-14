/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.util;

import java.awt.*;
import java.util.Random;

public class RandomUtil {
    //	private static final String ASC_II = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
    private static char[] seed1 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'};

    private static char[] seed2 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static char nextChar() {
        Random random = new Random();
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz".charAt(random
                .nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz".length()));
    }

//	public static String ranStr(int length) {
//		for (int i = 0; i < 20; i++) {
//			System.out.println(nextChar());
//		}
//		
//	}

    public static String getChinese(int length) throws Exception {
        if (length < 1) {
            return "";
        }
        if (length == 1) {
            return getChinese();
        }
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stb.append(getChinese());
        }
        return stb.toString();
    }

    public static String getChinese() throws Exception {
        Random random = new Random();
        Integer highPos = Integer.valueOf(176 + Math.abs(random.nextInt(39)));
        Integer lowPos = Integer.valueOf(161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = highPos.byteValue();
        b[1] = lowPos.byteValue();
        return new String(b, "GB2312");
    }

    public static String nextString(int number) {
        StringBuilder buf = new StringBuilder();
//		buf.append(number);
        for (int j = 0; j < number; j++) {
            buf.append(nextChar());
        }
        return buf.toString();
    }

    public static int nextInt() {
        Random random = new Random();
        return random.nextInt();
    }

    public static int nextInt(int n) {
        Random random = new Random();
        return random.nextInt(n);
    }

    public static boolean nextBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    public static int nextInt(int min, int max) {
        if (min == max) {
            return min;
        }
        Random random = new Random();
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        return random.nextInt(max - min) + min;
    }

    public static String nextIntAsStringByLength(int n) {
        StringBuilder buf = new StringBuilder();
        for (int j = 0; j < n; j++) {
            buf.append(nextInt(10));
        }

        return buf.toString();
    }

    public static boolean isRandomThingHappen(float persent) {
        if (persent >= 100.0F) {
            return true;
        }
        if (persent == 0.0F) {
            return false;
        }

        Random random = new Random();
        int max = 1000000;
        persent *= 10000.0F;

        if (System.currentTimeMillis() % 2L == 0L) {
            return random.nextInt(max) < persent;
        }
        return random.nextInt(max) >= max - persent;
    }

    public static Color nextColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    public static Color nextColor(int ac, int bc) {
        Random random = new Random();
        int tmp = Math.abs(bc - ac);
        if (tmp > 256) {
            tmp = 256;
        }
        if (tmp <= 0) {
            tmp = 1;
        }

        int min = Math.min(Math.abs(ac), Math.abs(bc));
        int r = min + random.nextInt(tmp);
        int g = min + random.nextInt(tmp);
        int b = min + random.nextInt(tmp);
        return new Color(r, g, b);
    }

    public static String getRandomNameByLength(int length) {
        StringBuilder newRandom = new StringBuilder();
        Random rd = new Random();

        for (int i = 0; i < length; i++) {
            newRandom.append(seed1[rd.nextInt(seed1.length)]);
        }

        return newRandom.toString();
    }

    public static String getRandomPasswordByLength(int length) {
        StringBuilder newRandom = new StringBuilder();
        Random rd = new Random();

        for (int i = 0; i < length; i++) {
            newRandom.append(seed2[rd.nextInt(seed2.length)]);
        }

        return newRandom.toString();
    }

    public static String getAppId(int length) {
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();

        for (int i = 0; i < length; i++) {
            if (i % 2 == 0) {
                sb.append(seed2[rd.nextInt(seed2.length)]);
            } else {
                sb.append(seed1[rd.nextInt(seed1.length)]);
            }
        }
        return sb.toString();
    }
}