package org.kotlin.scheduler.services

import org.kotlin.scheduler.commands.Command
import org.kotlin.scheduler.configurations.BotInfo
import org.kotlin.scheduler.managers.CommandResolver
import org.kotlin.scheduler.managers.StateManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap

@Service
class MyBot(@Autowired val info: BotInfo, val resolver: CommandResolver): TelegramLongPollingBot(){


    val map = ConcurrentHashMap<Int, StateManager>()



    override fun getBotUsername(): String {
       return info.schedulerName
    }

    override fun getBotToken(): String {
        return info.schedulerToken
    }

    override fun onUpdateReceived(p0: Update?) {

        val commandName : String = p0?.message?.text.toString()
        val commandOption : Optional<Command> = resolver.resolveCommand(commandName) as Optional<Command>
        if(commandOption.isPresent){
            val command : Command = commandOption.get()
            p0?.let { command.sendMessage(this, it) }



        }



    }


}