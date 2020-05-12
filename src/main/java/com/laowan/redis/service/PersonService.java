package com.laowan.redis.service;

import com.laowan.redis.model.Person;

/**
 * @program: redis
 * @description:
 * @author: wanli
 * @create: 2020-05-11 11:44
 **/
public interface PersonService {
    Person get(Integer id);

    Person getPerson(Integer id);


    Person getPersonWithCallBack(Integer id);

}
