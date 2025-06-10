package org.kotlin.scheduler.configurations

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class BotInfo {


    @Value("\${botToken}")
    lateinit var schedulerToken: String

    @Value("\${botName}")
    lateinit var schedulerName: String



}