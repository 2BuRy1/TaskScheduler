package org.kotlin.scheduler.commands

import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.managers.State
import org.kotlin.scheduler.managers.StateManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton.InlineKeyboardButtonBuilder
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup.InlineKeyboardMarkupBuilder
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

class Task: Command("/task", "поставить напоминалку") {



    override fun sendMessage(absSender: DefaultAbsSender, update: Update, state: State): State {
        val result = SendMessage()
        val chatId: Long?
        if(update.hasMessage()) chatId = update.message.chatId
        else chatId = update.callbackQuery.message.chatId

        val returnState : State
        result.chatId = chatId.toString()

        if(state == State.FIRST_ASK){
            val markup: InlineKeyboardMarkup = createInlineKeyboard(CallBackData.SCHEDULED, CallBackData.TIMER)
            result.text = "можешь второй раз спросить"
            result.replyMarkup = markup
            returnState = State.SECOND_ASK
        }

        else if(state == State.SECOND_ASK){

                result.text = "так уж и быть, спроси третий раз"
                returnState = State.THIRD_ASK

        }

        else{
            result.text = "на этом конец броу"
            returnState = State.FIRST_ASK
        }



        absSender.execute(result)
        
        return returnState
    }





    private fun createInlineKeyboard(vararg data: CallBackData): InlineKeyboardMarkup{
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