package org.kotlin.scheduler.configurations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisRepository(@Autowired val redisTemplate: RedisTemplate<String, String>) {

    fun putData(key: String, value: String, time: Long ){
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES)
    }

    fun getData(key:String): String{
      return  redisTemplate.opsForValue().get(key) as String
    }






}