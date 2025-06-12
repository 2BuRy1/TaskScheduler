package org.kotlin.scheduler.services

import org.kotlin.scheduler.commands.Command
import org.kotlin.scheduler.commands.Task
import org.kotlin.scheduler.configurations.BotInfo
import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.managers.CommandResolver
import org.kotlin.scheduler.managers.State
import org.kotlin.scheduler.managers.StateManager
import org.kotlin.scheduler.repositories.RedisRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.text.SimpleDateFormat
import java.util.Optional

@Service
class MyBot(@Autowired private val info: BotInfo, private val resolver: CommandResolver, private val stateManager: StateManager, private val redisRepository: RedisRepository): TelegramLongPollingBot(){





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


            val state : Optional<State> = p0?.message?.chatId?.let { stateManager.getStateByChatId(it) } as Optional<State>

            if(state.isEmpty) stateManager.addState(p0.message?.chatId!!, State.FIRST_ASK)

            val currentState = stateManager.getStateByChatId(p0.message.chatId)?.get()

            val command : Command = commandOption.get()


            if(stateManager.getCommandByChatId(p0.message.chatId).isEmpty) stateManager.addCommand(p0.message.chatId, command)






            val nextState = p0.let { command.sendMessage(this, it, currentState!!) }

            if(nextState == State.FIRST_ASK) stateManager.removeFromCommands(p0.message.chatId)


            stateManager.addState(p0.message.chatId, nextState)

        }



        else if(p0!!.hasCallbackQuery()){
            val callBack = p0.callbackQuery

            val currentState: Optional<State> =callBack?.message?.chatId.let { stateManager.getStateByChatId(it!!) } as Optional<State>
            val currentOptionalOfCommand = callBack?.message?.chatId?.let { stateManager.getCommandByChatId(it) } as Optional<Command>

            if(currentState.isEmpty || currentOptionalOfCommand.isEmpty) return


            val command: Command = currentOptionalOfCommand.get()
            val nextState = p0.let { command.sendMessage(this, it, currentState.get()) }


            if(nextState == State.FIRST_ASK) stateManager.removeFromCommands(p0.message.chatId)




            nextState.let { stateManager.addState(callBack.message.chatId, it) }
        }

        else if(commandOption.isEmpty){
            val currentState: State? = stateManager.getStateByChatId(p0.message.chatId)?.get()
                currentState.let {
                    stateManager.getCommandByChatId(p0.message.chatId).get().sendMessage(this, p0, it!!)
                }








        }



    }


}