package org.kotlin.scheduler.configurations

import org.kotlin.scheduler.services.MyBot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class BotInitializer {



    @Bean
    fun register(bot: MyBot):TelegramBotsApi{
        var defaultBotSession = DefaultBotSession()
        var telegramBotsApi = TelegramBotsApi(defaultBotSession.javaClass)
        telegramBotsApi.registerBot(bot)
        return  telegramBotsApi
    }




}