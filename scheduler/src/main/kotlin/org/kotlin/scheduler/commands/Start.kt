package org.kotlin.scheduler.commands

import org.kotlin.scheduler.managers.State
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class Start : Command("/start", "Стартовая команда") {



    override fun sendMessage(absSender: DefaultAbsSender, update: Update, state: State): State {
        val message = SendMessage()

        message.text = "meow!!"
        message.chatId = update?.message?.chatId.toString()


        absSender.execute(message)
        return State.FIRST_ASK
    }

}