package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.State
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
@Component
class SecondState(override val supportedState: State = State.SECOND_ASK) : TaskState {
    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {
        val callBack  = update.callbackQuery

        //TODO CallBackLogic (schedule tasks)

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