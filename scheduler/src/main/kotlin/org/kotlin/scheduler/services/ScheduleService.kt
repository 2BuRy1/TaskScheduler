package org.kotlin.scheduler.services

import org.kotlin.scheduler.scheduleManagers.ExecutionDao
import org.kotlin.scheduler.scheduleManagers.ScheduledTask
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.DefaultAbsSender
import java.time.ZoneId


@Service
@EnableScheduling
class ScheduleService(private val taskScheduler: TaskScheduler) {



    fun executeTask(executionDao: ExecutionDao){
        taskScheduler.schedule(ScheduledTask(executionDao), executionDao.date.atZone(ZoneId.systemDefault()).toInstant())
    }








}