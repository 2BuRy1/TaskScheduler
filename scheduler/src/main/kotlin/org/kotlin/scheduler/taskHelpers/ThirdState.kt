package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.repositories.RedisRepository
import org.kotlin.scheduler.managers.State
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
@Component
class ThirdState(override val supportedState: State = State.THIRD_ASK, @Autowired private val redisRepository: RedisRepository) : TaskState {
    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {
        val result = SendMessage()

        val callBack = update.callbackQuery

        val deleteMessage = DeleteMessage()

        redisRepository.putData("${callBack.message.chatId}degree", callBack.data, 30)

        deleteMessage.chatId = callBack.message.chatId.toString()
        deleteMessage.messageId = callBack.message.messageId


        val timerType = CallBackData.valueOf(redisRepository.getData("${callBack.message.chatId}type"))




        val type = when(timerType){
            CallBackData.SCHEDULED -> "введите время в формате dd:mm:yyyy"
            CallBackData.TIMER -> "введите время в минутах"
        }



        result.text = type
        result.chatId = callBack.message.chatId.toString()
        absSender.execute(deleteMessage)

        return Transition(State.FOURTH_ASK, result)




    }
}