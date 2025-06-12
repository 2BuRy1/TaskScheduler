package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.State
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.objects.Update

interface TaskState {



    val supportedState: State

    fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition


}