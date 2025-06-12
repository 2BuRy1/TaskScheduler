package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.State
import org.telegram.telegrambots.meta.api.methods.send.SendMessage



//Класс обертка для отправки сообщений в зависимости от callBackQuery и тд
class Transition(val nextState: State, val sendMessage: SendMessage) {
}

