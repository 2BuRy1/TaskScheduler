package org.kotlin.scheduler.commands

import org.kotlin.scheduler.managers.State
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import java.util.*

abstract class Command(val name: String,val description: String) {


    abstract fun sendMessage(absSender: DefaultAbsSender, update: Update, state: State): State


    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(other.javaClass != this.javaClass) return false
        val obj = other as Command
        return obj.description.equals(this.description) && obj.name.equals(this.name)
    }

    override fun hashCode(): Int {
        return Objects.hash(name, description)
    }

}