package com.jh.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppUser getAppUserSample1() {
        return new AppUser().id(1L).name("name1").lastname("lastname1");
    }

    public static AppUser getAppUserSample2() {
        return new AppUser().id(2L).name("name2").lastname("lastname2");
    }

    public static AppUser getAppUserRandomSampleGenerator() {
        return new AppUser().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).lastname(UUID.randomUUID().toString());
    }
}
