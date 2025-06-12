package org.kotlin.scheduler.managers

import org.kotlin.scheduler.commands.Command
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap


@Component
class StateManager {

    private val stateMap = ConcurrentHashMap<Long, State>()

    private val commandMap = ConcurrentHashMap<Long, Command>()


    fun getStateByChatId(chatId: Long): Optional<State>? {
        if(stateMap[chatId] == null) return Optional.empty()
        return Optional.of(stateMap[chatId]!!)

    }

    fun addState(chatId: Long, state: State){
        stateMap[chatId] = state
    }

    fun getCommandByChatId(chatId: Long): Optional<Command> {
        if(commandMap[chatId] == null) return Optional.empty()
        return Optional.of(commandMap[chatId]!!)
    }

    fun addCommand(chatId: Long, command: Command){
        commandMap[chatId] = command
    }

    fun removeFromCommands(chatId: Long){
        commandMap.remove(chatId)
    }

    fun containsState(chatId: Long): Boolean{
        return stateMap.containsKey(chatId)
    }






}