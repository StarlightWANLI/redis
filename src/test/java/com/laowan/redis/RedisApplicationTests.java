package com.laowan.redis;

import com.laowan.redis.model.Person;
import com.laowan.redis.service.PersonService;
import com.laowan.redis.service.RedisManageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class RedisApplicationTests {

    @Autowired
    PersonService personService;

    @Autowired
    RedisTemplate redisTemplate;


    @Autowired
    RedisManageService redisManageService;


    @Test
    void getTest() {
       Person person = personService.get(101);
       log.info("查询结果为：" + person.toString());
    }

    @Test
    void getPersonTest() {
        Person person = personService.getPersonWithClosure(101);
        log.info("查询结果为：" + person.toString());
    }


    @Test
    void test1() {
        redisTemplate.opsForValue().set("name","laowan",60, TimeUnit.SECONDS);
    }


    @Test
    void cacheUser() {
        Person person = new Person();
        person.setName("laowan");
        person.setAge(25);
        redisTemplate.opsForValue().set("person:"+ person.getName(), person,60, TimeUnit.SECONDS);
    }


    @Test
    void redisTest1() {
        redisManageService.set("name","laowan",60);
    }


    @Test
    void getCaChe() {
        redisManageService.set("name","laowan");
        String name = redisManageService.get("name",String.class);
        log.info(name);
    }

}
