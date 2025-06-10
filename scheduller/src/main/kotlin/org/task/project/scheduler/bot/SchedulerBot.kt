package org.task.project.scheduler.bot

import org.springframework.beans.factory.annotation.Value
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

class SchedulerBot : TelegramLongPollingBot() {

    @Value("\${bot.token}")
    private lateinit var botToken: String

    @Value("\${bot.name}")
    private lateinit var botName: String

    override fun getBotUsername(): String {
        return botName
    }

    override fun getBotToken(): String {
        return botToken
    }

    override fun onUpdateReceived(update: Update?) {
        // TODO: Implement your update handling logic here
    }
}