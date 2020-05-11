package com.laowan.redis.service;

import com.laowan.redis.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @program: redis
 * @description:
 * @author: wanli
 * @create: 2020-05-11 11:44
 **/
@Service
@Slf4j
public class PersonService {
    private String person_cache_key="person:get:";


    @Autowired
    RedisManageService redisManageService;

    /**
     * 通过注解     @Cacheable中的value相当于声明一个存放缓存的文件夹，可以理解为  "get:"+keyGenerator
     *
     * keyGenerator = "#id"
     * @param id
     * @return
     */
    @Cacheable(value = "person",keyGenerator = "keyGenerator")
    public Person get(Integer id){
        log.info("未命中缓存，从数据库查询");
        Person person = new Person();
        person.setId(id);
        person.setName("laowan");
        person.setAge(25);
        return person;
    }


    public Person getPerson(Integer id){
        String key = person_cache_key + id;
        Person person = null;
        if(redisManageService.hasKey(key)){
            person = (Person)redisManageService.get(key);
            log.info("命中缓存，结果为：{}" ,person.toString());
        }else{
            person = new Person();
            person.setId(id);
            person.setName("laowan");
            person.setAge(25);
            if(person!=null){
                log.info("未命中缓存，从数据库查询结果为：{}",person.toString());
                redisManageService.set(key,person);
            }
        }
        return person;
    }


    public Person getPersonWithClosure(Integer id){
        String key = person_cache_key + id;
        Person person = redisManageService.get(key, Person.class, new Closure<Person, String>() {
            @Override
            public Person execute(String input) {
                log.info("查询数据库");
                Person  personDB = new Person();
                personDB.setId(id);
                personDB.setName("laowan");
                personDB.setAge(25);
                return personDB;
            }
        });
        return person;
    }

}
