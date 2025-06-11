package org.kotlin.scheduler.managers

enum class CallBackData(name: String) {
    SCHEDULED("запустить по времени"), TIMER("запустить по таймеру");


    override fun toString(): String {
        return this.name
    }
}