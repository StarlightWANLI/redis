package com.laowan.redis;

import com.laowan.redis.model.Person;
import com.laowan.redis.service.PersonService;
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


    @Test
    void getTest() {
       Person person = personService.get(102);
       log.info("查询结果为：" + person.toString());
    }

    @Test
    void getPersonTest() {
        Person person = personService.getPerson(102);
        log.info("查询结果为：" + person.toString());
    }

    @Test
    void getPersonWithClosureTest() {
        Person person = personService.getPersonWithCallBack(104);
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

}
