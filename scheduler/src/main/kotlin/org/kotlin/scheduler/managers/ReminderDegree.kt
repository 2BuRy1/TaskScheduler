package org.kotlin.scheduler.managers

enum class ReminderDegree(val value: String) {
    HIGH("Сильный\uD83D\uDFE5"),MEDIUM("Средний\uD83D\uDFE8"),LOW("Легкий\uD83D\uDFE9");

    override fun toString(): String {
        return this.value
    }

}