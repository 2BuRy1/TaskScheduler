package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.managers.State
import org.kotlin.scheduler.repositories.RedisRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

@Component
class FourthState(override val supportedState: State = State.FOURTH_ASK, @Autowired private val redisRepository: RedisRepository) : TaskState {


    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {

        val result = SendMessage()

        val value = update.message.text

        val type : CallBackData =CallBackData.valueOf(redisRepository.getData("${update.message.chatId}type"))

        if(type == CallBackData.TIMER){

            val intValue = value.toInt()

            if(intValue < 0) result.text = "Введи больше нуля ебланище"

        }

        else if(type == CallBackData.SCHEDULED){

           //todo



        }



        result.text = "заебись, братуха"
        result.chatId = update.message.chatId.toString()





        return Transition(State.LAST_ASK, result)
    }
}