package org.kotlin.scheduler.commands

import org.kotlin.scheduler.managers.CallBackData
import org.kotlin.scheduler.managers.ReminderDegree
import org.kotlin.scheduler.managers.State
import org.kotlin.scheduler.repositories.RedisRepository
import org.kotlin.scheduler.scheduleManagers.ExecutionDao
import org.kotlin.scheduler.services.ScheduleService
import org.kotlin.scheduler.taskHelpers.TaskState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.objects.Update
import java.time.LocalDateTime

@Component
class Task(
    @Autowired private val taskStates: MutableList<out TaskState>,
    private val scheduleService: ScheduleService,
    private val redisRepository: RedisRepository
): Command("/task", "поставить напоминалку") {


    override fun sendMessage(absSender: DefaultAbsSender, update: Update, state: State): State {
        val taskState = taskStates.find { it.supportedState == state }
        val transition = taskState?.handle(update, state, absSender)

        absSender.execute(transition?.sendMessage)


        val returnState = transition?.nextState

        if(returnState == State.FIRST_ASK){

            val executionDao: ExecutionDao



            val chatId = update.message.chatId

            val type = redisRepository.getAndDeleteData("${chatId}type")

            val overallDescription = redisRepository.getAndDeleteData("${chatId}description").split(":")

            val date = redisRepository.getAndDeleteData("${chatId}date")

            print("date before scheduling: $date")

            executionDao = when(CallBackData.valueOf(type)){
                CallBackData.SCHEDULED -> ExecutionDao(LocalDateTime.parse(date),
                    overallDescription[0],
                    overallDescription[1],
                    chatId,
                    absSender,
                    ReminderDegree.valueOf(redisRepository.getAndDeleteData("${chatId}degree")) )
                CallBackData.TIMER -> ExecutionDao(LocalDateTime.now().plusMinutes(date.toLong()),
                    overallDescription[0],
                    overallDescription[1],
                    chatId,
                    absSender,
                    ReminderDegree.valueOf(redisRepository.getAndDeleteData("${chatId}degree")) )
            }

            scheduleService.executeTask(executionDao)

        }





        return transition!!.nextState





    }






}