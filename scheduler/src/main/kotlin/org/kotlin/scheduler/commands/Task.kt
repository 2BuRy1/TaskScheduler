package org.kotlin.scheduler.commands

import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.objects.Update

class Task: Command("/task", "Создать напоминалку") {
    override fun sendMessage(absSender: DefaultAbsSender, update: Update) {
    }
}