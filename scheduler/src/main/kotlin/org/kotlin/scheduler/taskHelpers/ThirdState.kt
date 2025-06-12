package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.repositories.RedisRepository
import org.kotlin.scheduler.managers.State
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
@Component
class ThirdState(override val supportedState: State = State.THIRD_ASK, @Autowired private val redisRepository: RedisRepository) : TaskState {
    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {
        val sendMessage = SendMessage()

        println("mama" + redisRepository.getData("${update.message.chatId}callBack"))


        sendMessage.chatId = update.message.chatId.toString()
        sendMessage.text = "meow pussy striker!!"
        return Transition(State.FIRST_ASK, sendMessage)
    }
}