package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.managers.State
import org.kotlin.scheduler.repositories.RedisRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.lang.NumberFormatException
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component
class LastState(override val supportedState: State= State.LAST_ASK, @Autowired private val redisRepository: RedisRepository) : TaskState {



    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {

        val result = SendMessage()

        val value = update.message.text

        result.chatId = update.message.chatId.toString()


        val type : CallBackData = CallBackData.valueOf(redisRepository.getData("${update.message.chatId}type"))

        if(type == CallBackData.TIMER){
            try {
                val intValue = value.toLong()
                if (intValue < 0){
                    result.text = "Неверное количество минут\nПопробуйте снова"
                    return Transition(State.FOURTH_ASK, result)
                }
                else{
                    result.text = "вас понял"
                    redisRepository.putData("${update.message.chatId}date", intValue.toString(), 30)
                }
            }catch(e: NumberFormatException){
                result.text = "введите целое число"
                return Transition(State.FOURTH_ASK, result)
            }

        }

        else if(type == CallBackData.SCHEDULED){
            val formattedDate: LocalDateTime
            try{
                formattedDate= parseCustomDate(value)
                result.text = "вас понял"
                println("scheduled date: ${formattedDate}")
                redisRepository.putData("${update.message.chatId}date", formattedDate.toString(), 30)
            }catch (e: IllegalArgumentException){
                println("Somehow in catch block")
                result.text = "неверный формат даты!!!\nПопробуйте снова"
                return Transition(State.FOURTH_ASK, result )
            }

        }



        return Transition(State.FIRST_ASK, result)
    }





    private fun parseCustomDate(input: String): LocalDateTime {
        val parts = input.split(".")
        if (parts.size != 2) throw IllegalArgumentException("Неверный формат даты: $input")

        val day = parts[0].toInt()
        val time = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("HH:mm"))

        val now = LocalDateTime.now()
        return now.withDayOfMonth(day).withHour(time.hour).withMinute(time.minute).withSecond(0).withNano(0)
    }

}