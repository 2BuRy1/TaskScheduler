package org.kotlin.scheduler.commands

import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

sealed class Command(val name: String,val description: String) {

    abstract fun sendMessage(absSender: DefaultAbsSender, update: Update)


}