package org.kotlin.scheduler.services

import org.kotlin.scheduler.configurations.BotInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class MyBot(@Autowired val info: BotInfo): TelegramLongPollingBot(){






    override fun getBotUsername(): String {
       return info.schedulerName
    }

    override fun getBotToken(): String {
        return info.schedulerToken
    }

    override fun onUpdateReceived(p0: Update?) {
        print(p0?.message?.text)
    }


}