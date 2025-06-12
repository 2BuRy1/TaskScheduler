package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.managers.State
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
@Component
class FirstState(override val supportedState: State = State.FIRST_ASK) : TaskState {




    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {
        val markup = createInlineKeyboard(CallBackData.TIMER, CallBackData.SCHEDULED)


        val sendMessage = SendMessage()
        sendMessage.chatId = update.message.chatId.toString()
        sendMessage.replyMarkup = markup
        sendMessage.text = "Выберите тип напоминания:"


        return Transition(State.SECOND_ASK, sendMessage)
    }


    private fun createInlineKeyboard(vararg data: CallBackData): InlineKeyboardMarkup {
        val markup = InlineKeyboardMarkup()
        print(data.size)
        val listKeyboard = ArrayList<List<InlineKeyboardButton>>()
        var index = 0
        for(i in 0 until data.size){
            listKeyboard.add(index, ArrayList<InlineKeyboardButton>())

            val currentList: ArrayList<InlineKeyboardButton> = listKeyboard.get(index) as ArrayList<InlineKeyboardButton>
            val currentButton = InlineKeyboardButton()
            currentButton.text = data[i].toString()
            currentButton.callbackData = data[i].toString().uppercase()

            currentList.add(currentButton)
            index++;


        }
        markup.keyboard = listKeyboard
        print(data.size)
        return markup
    }
}