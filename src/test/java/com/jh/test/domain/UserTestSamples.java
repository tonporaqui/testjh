package com.jh.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static User getUserSample1() {
        return new User().id(1L).name("name1").lastname("lastname1");
    }

    public static User getUserSample2() {
        return new User().id(2L).name("name2").lastname("lastname2");
    }

    public static User getUserRandomSampleGenerator() {
        return new User().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).lastname(UUID.randomUUID().toString());
    }
}
