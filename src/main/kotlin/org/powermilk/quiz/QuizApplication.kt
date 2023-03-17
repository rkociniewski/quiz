package org.powermilk.quiz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuizApplication

fun main(vararg args: String) {
    runApplication<QuizApplication>(*args)
}
