package org.kotlin.scheduler.managers

import org.kotlin.scheduler.commands.Command
import org.kotlin.scheduler.commands.Start
import org.springframework.stereotype.Service
import java.util.*
import kotlin.reflect.full.createInstance


@Service
class CommandResolver {



    fun resolveCommand(name: String) : Optional<out Any> {
        val subclasses = Command::class.sealedSubclasses
        for(e in subclasses){
            if(e.createInstance().name.equals(name)) return Optional.of(e.createInstance())
        }

        return Optional.empty();
    }



}