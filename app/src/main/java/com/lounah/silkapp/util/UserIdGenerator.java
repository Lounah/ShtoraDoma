package com.lounah.silkapp.util;

import java.util.Random;

public final class UserIdGenerator {

    private static final int LIMIT_ID = 999999999;
    private static final Random random = new Random(LIMIT_ID);

    private UserIdGenerator() {}

    public static int generate() {
        return random.nextInt();
    }

}
