package org.kotlin.scheduler.taskHelpers

import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.managers.ReminderDegree
import org.kotlin.scheduler.repositories.RedisRepository
import org.kotlin.scheduler.managers.State
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Component
class SecondState(override val supportedState: State = State.SECOND_ASK, @Autowired private val redisRepository: RedisRepository) : TaskState {
    override fun handle(update: Update, state: State, absSender: DefaultAbsSender): Transition {
        val callBack  = update.callbackQuery
        println("callback: ${callBack.data}")


        redisRepository.putData("${callBack.message.chatId}type", callBack.data, 30)

        val deleteMessage = DeleteMessage()


        deleteMessage.chatId = callBack.message.chatId.toString()

        deleteMessage.messageId = callBack.message.messageId

        absSender.execute(deleteMessage)

        val result = SendMessage()

        val markup = createInlineKeyboard(ReminderDegree.LOW, ReminderDegree.MEDIUM, ReminderDegree.HIGH)



        result.replyMarkup = markup
        result.chatId = callBack.message.chatId.toString()
        result.text = "Хорошо, выбери уровень уведомления!"

        return Transition(State.THIRD_ASK, result)


    }



    private fun createInlineKeyboard(vararg data: ReminderDegree): InlineKeyboardMarkup {
        val markup = InlineKeyboardMarkup()
        val listKeyboard = ArrayList<List<InlineKeyboardButton>>()
        var index = 0
        for(i in 0 until data.size){
            listKeyboard.add(index, ArrayList<InlineKeyboardButton>())

            val currentList: ArrayList<InlineKeyboardButton> = listKeyboard.get(index) as ArrayList<InlineKeyboardButton>
            val currentButton = InlineKeyboardButton()
            currentButton.text = data[i].toString()
            currentButton.callbackData = data[i].name.uppercase()

            currentList.add(currentButton)
            index++;


        }
        markup.keyboard = listKeyboard
        return markup
    }





}