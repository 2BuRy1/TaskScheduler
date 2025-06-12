package org.kotlin.scheduler.commands

import org.kotlin.scheduler.managers.State
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import kotlin.reflect.full.createInstance
@Component
class Help(@Autowired private val applicationContext: ApplicationContext): Command("/help", "Получить справку по всем командам") {
    override fun sendMessage(absSender: DefaultAbsSender, update: Update, state: State): State {
        val subClasses = applicationContext.getBeansOfType(Command::class.java)
        val result = SendMessage()
        val builder = StringBuilder()
        for(i in subClasses){
            builder.append(i.value.name).append(": ").append(i.value.description).append("\n")
        }
        result.chatId = update?.message?.chatId.toString()
        result.text = builder.toString().trim()
        absSender.execute(result)
        return State.FIRST_ASK
    }
}