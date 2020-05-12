package com.laowan.redis.service.impl;

import com.laowan.redis.model.Person;
import com.laowan.redis.service.CacheCallBack;
import com.laowan.redis.service.CacheService;
import com.laowan.redis.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @program: redis
 * @description:
 * @author: wanli
 * @create: 2020-05-12 10:13
 **/
@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private String person_cache_key="person:get:";

    @Autowired
    CacheService cacheService;

    /**
     * 通过注解@Cacheable中的value相当于声明一个存放缓存的文件夹，可以理解为  "get:"+keyGenerator
     * keyGenerator = "#id"
     * @param id
     * @return
     */
    @Cacheable(value = "person",keyGenerator = "keyGenerator")
    @Override
    public Person get(Integer id){
        log.info("未命中缓存，从数据库查询");
        Person person = new Person();
        person.setId(id);
        person.setName("laowan");
        person.setAge(25);
        return person;
    }


    /**
     * 硬编码实现查询缓存——》判空——》然后查询数据库——》判空——》更新缓存
     * @param id
     * @return
     */
    @Override
    public Person getPerson(Integer id){
        String key = person_cache_key + id;
        Person person = cacheService.getCache(key,Person.class);
        if(person!=null){
            log.info("命中缓存，结果为：{}" ,person.toString());
        }else{
            //模拟数据库查询
            person = new Person();
            person.setId(id);
            person.setName("laowan");
            person.setAge(25);
            if(person!=null){
                log.info("未命中缓存，从数据库查询结果为：{}",person.toString());
                cacheService.setCache(key,person);
            }
        }
        return person;
    }


    /**
     * 通过传递回调函数，减少重复的查询缓存——》判空——》然后查询数据库——》判空——》更新缓存 编码操作
     * @param id
     * @return
     */
    @Override
    public Person getPersonWithCallBack(Integer id){
        String key = person_cache_key + id;
        Person person = cacheService.getCache(key, Person.class, new CacheCallBack<Person, String>() {
            @Override
            public Person execute(String input) {
                //模拟数据库查询
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
