package org.kotlin.scheduler.scheduleManagers

import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.managers.ReminderDegree
import org.kotlin.scheduler.managers.State
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class ScheduledTask(private val executionDao: ExecutionDao): Runnable {


    override fun run() {
        val amount = getDegreeOfReminder()


        val sendMessage = SendMessage()

        sendMessage.chatId = executionDao.chatId.toString()


        sendMessage.text = "🚨🚨Напоминание: ${executionDao.name}\n📕Нужно : ${executionDao.description}"

        for(i in 0  until amount){
            executionDao.absSender.execute(sendMessage)

        }

    }




    private fun getDegreeOfReminder(): Int{
        return when(executionDao.reminderDegree){
            ReminderDegree.LOW -> 1
            ReminderDegree.MEDIUM -> 10
            ReminderDegree.HIGH -> 100
        }
    }

}