package org.kotlin.scheduler.commands

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
        val chatId = update.message.chatId
        val returnState : State
        result.chatId = chatId.toString()

        if(state == State.FIRST_ASK){
            result.text = "можешь второй раз спросить"
            returnState = State.SECOND_ASK
        }

        else if(state == State.SECOND_ASK){
            if(update.message.text.equals("allah")){
                returnState = State.FIRST_ASK
                result.text = "ты проебался броу"
            }

            else {
                result.text = "так уж и быть, спроси третий раз"
                returnState = State.THIRD_ASK
            }
        }

        else{
            result.text = "на этом конец броу"
            returnState = State.FIRST_ASK
        }



        absSender.execute(result)
        
        return returnState
    }





    private fun createInlineKeyboard(vararg data: String): InlineKeyboardMarkup{
        for(e in data){
            println(e)

        }

        print(data.size)
        return InlineKeyboardMarkup()
    }
}