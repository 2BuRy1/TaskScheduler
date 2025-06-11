package org.kotlin.scheduler.commands

import org.kotlin.scheduler.managers.State
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import kotlin.reflect.full.createInstance

class Help: Command("/help", "Получить справку по всем командам") {
    override fun sendMessage(absSender: DefaultAbsSender, update: Update, state: State): State {
        val subClasses = Command::class.sealedSubclasses
        val builder = StringBuilder()
        for(e in subClasses){
            val currentInstance = e.createInstance()
            builder.append(currentInstance.name).append(": ").append(currentInstance.description).append("\n")
        }
        val result = SendMessage()
        result.chatId = update?.message?.chatId.toString()
        result.text = builder.toString().trim()
        absSender.execute(result)
        return State.FIRST_ASK
    }
}