package com.laowan.redis.model;

import lombok.Data;

/**
 * @program: redis
 * @description:
 * @author: wanli
 * @create: 2020-05-11 11:37
 **/
@Data
public class Person {
    private Integer id;
    private String name;
    private Integer age;
}
