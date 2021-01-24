package com.rumakin.universityschedule.utils;

import java.util.*;

public final class PasswordGenerator {

    private static final String SIGNS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*()_+-=[]|,./?><";
    private static final int SIGN_COUNT = 10;
    
    private PasswordGenerator() {
    }

    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < SIGN_COUNT; i++) {
            password.append(SIGNS.charAt(random.nextInt(SIGNS.length())));
        }
        return new String(password);
    }
}
