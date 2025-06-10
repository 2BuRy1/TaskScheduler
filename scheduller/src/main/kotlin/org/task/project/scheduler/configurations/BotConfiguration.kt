package org.task.project.scheduler.configurations

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.task.project.scheduler.bot.SchedulerBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
@Configuration
class BotConfiguration  {





    @Bean
    fun TelegramBotsApi(bot: SchedulerBot): TelegramBotsApi {
        var botSession = DefaultBotSession()
        var api = TelegramBotsApi(botSession.javaClass)
        api.registerBot(bot)
        return api;
    }




}