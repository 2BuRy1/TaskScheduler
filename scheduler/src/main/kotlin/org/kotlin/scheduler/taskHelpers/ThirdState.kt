package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.State
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
@Component
class ThirdState(override val supportedState: State = State.THIRD_ASK) : TaskState {
    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {
        val sendMessage = SendMessage()
        sendMessage.chatId = update.message.chatId.toString()
        sendMessage.text = "meow pussy striker!!"
        return Transition(State.FIRST_ASK, sendMessage)
    }
}