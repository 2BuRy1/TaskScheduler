package org.kotlin.scheduler.repositories

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisRepository(@Autowired private val redisTemplate: RedisTemplate<String, String>) {

    fun putData(key: String, value: String, time: Long ){
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES)
    }

    fun getAndDeleteData(key:String): String{
      return  redisTemplate.opsForValue().getAndDelete(key) as String
    }

    fun getData(key: String): String{
        return redisTemplate.opsForValue().get(key) as String
    }

}