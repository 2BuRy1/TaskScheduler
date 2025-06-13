package org.kotlin.scheduler.scheduleManagers

import org.kotlin.scheduler.managers.ReminderDegree
import org.telegram.telegrambots.bots.DefaultAbsSender
import java.time.LocalDateTime
import java.util.Date

class ExecutionDao(val date: LocalDateTime, val name: String, val description: String, val chatId : Long, val absSender: DefaultAbsSender, val reminderDegree: ReminderDegree) {

}
