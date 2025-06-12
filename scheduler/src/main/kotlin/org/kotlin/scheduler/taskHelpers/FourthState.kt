package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.State
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
@Component
class FourthState(override val supportedState: State = State.FOURTH_ASK) : TaskState {


    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {
        val callback = update.callbackQuery

        val result = SendMessage()
        println("laaaaaal ${callback.data}")

        result.text = "заебись, братуха"
        result.chatId = callback.message.chatId.toString()






        return Transition(State.LAST_ASK, result)
    }
}