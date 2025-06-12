package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.repositories.RedisRepository
import org.kotlin.scheduler.managers.State
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
@Component
class SecondState(override val supportedState: State = State.SECOND_ASK, @Autowired private val redisRepository: RedisRepository) : TaskState {
    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {
        val callBack  = update.callbackQuery
        println("callback: ${callBack.data}")


        redisRepository.putData("${callBack.message.chatId}callBack", callBack.data, 30)

        val deleteMessage = DeleteMessage()

        deleteMessage.chatId = callBack.message.chatId.toString()

        deleteMessage.messageId = callBack.message.messageId

        absSender.execute(deleteMessage)

        val result = SendMessage()

        result.chatId = callBack.message.chatId.toString()
        result.text = "Класс, погнали дальше!"

        return Transition(State.THIRD_ASK, result)


    }
}