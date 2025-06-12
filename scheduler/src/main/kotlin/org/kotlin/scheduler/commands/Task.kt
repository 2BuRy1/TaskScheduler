package org.kotlin.scheduler.commands

import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.managers.State
import org.kotlin.scheduler.managers.StateManager
import org.kotlin.scheduler.taskHelpers.FirstState
import org.kotlin.scheduler.taskHelpers.SecondState
import org.kotlin.scheduler.taskHelpers.TaskState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton.InlineKeyboardButtonBuilder
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup.InlineKeyboardMarkupBuilder
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
@Component
class Task(@Autowired private val taskStates: List<TaskState>): Command("/task", "поставить напоминалку") {





    override fun sendMessage(absSender: DefaultAbsSender, update: Update, state: State): State {
        val taskState = taskStates.find { it.supportedState == state }
        val transition = taskState?.handle(update, state, absSender)

        absSender.execute(transition?.sendMessage)
        return transition!!.nextState





    }






}