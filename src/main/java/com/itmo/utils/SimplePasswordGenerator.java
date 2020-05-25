package com.itmo.utils;

import java.util.Random;

/**
 * генерируем пароли для ленивых пользователей
 */
public class SimplePasswordGenerator {
    private static String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String NUMBERS = "0123456789";
    private static String SPECIAL_CHARS = "!@#$%&*()_+-=[]|,./?><";

    private String charsForGenerate = "Ы";

    public SimplePasswordGenerator(boolean upper, boolean lower, boolean numbers, boolean special_chars) {
        if (upper) charsForGenerate += UPPER;
        if (lower) charsForGenerate += LOWER;
        if (numbers) charsForGenerate += NUMBERS;
        if (special_chars) charsForGenerate += SPECIAL_CHARS;
    }

    public String generate(int minLen, int maxLen) {
        Random random = new Random();
        int length = random.nextInt(maxLen - minLen + 1) + minLen;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
            result.append(charsForGenerate.charAt(random.nextInt(charsForGenerate.length())));
        return result.toString();
    }
}
