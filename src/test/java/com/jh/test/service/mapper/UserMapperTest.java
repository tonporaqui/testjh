package com.jh.test.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
    }
}
