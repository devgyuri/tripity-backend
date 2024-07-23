package me.gyuri.tripity.global.redis.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RedisServiceTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void redisTest() throws Exception {
        // given
        String email = "test@test.com";
        String code = "aaa111";

        // when
        redisService.setDataExpire(email, code, 60 * 60L);

        // then
        Assertions.assertTrue(redisService.existData(email));
        Assertions.assertFalse(redisService.existData("test1@test.com"));
        Assertions.assertEquals(redisService.getData(email), code);
    }
}