package com.laowan.redis.service;

/**
 * @program: redis
 * @description:缓存回调接口
 * @author: wanli
 * @create: 2020-05-11 15:26
 **/
public interface Closure<O,I> {
      O execute(I input);
}
