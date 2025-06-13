package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.managers.State
import org.kotlin.scheduler.repositories.RedisRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component
class FourthState(override val supportedState: State = State.FOURTH_ASK, @Autowired private val redisRepository: RedisRepository) : TaskState {



    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {

        val result = SendMessage()

        val timerType = CallBackData.valueOf(redisRepository.getData("${update.message.chatId}type"))

        result.chatId = update.message.chatId.toString()


        val description = update.message.text

        if(description.split(":").size != 2){
            result.text = "Введите данные в формате имя: описание, пожалуйста"
            return Transition(State.FOURTH_ASK, result)
        }

        redisRepository.putData("${update.message.chatId}description", description, 30)





        val type = when(timerType){
            CallBackData.SCHEDULED -> "введите день в этом месяце и время. Например: 28.13:44 (28 число, 13 часов 44 минуты)"
            CallBackData.TIMER -> "введите время в минутах"
        }



        result.text = type
        return Transition(State.LAST_ASK, result)
    }


}