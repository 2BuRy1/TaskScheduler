package org.kotlin.scheduler.managers

enum class CallBackData(val value: String) {
    SCHEDULED("По времени 🕑"), TIMER("По таймеру ⌛️");


    override fun toString(): String {
        return this.value
    }
}