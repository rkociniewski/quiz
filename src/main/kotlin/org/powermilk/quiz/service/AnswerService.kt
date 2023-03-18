package org.powermilk.quiz.service

import org.powermilk.quiz.model.Answer
import org.powermilk.quiz.model.Question

interface AnswerService {
    fun save(answer: Answer): Answer
    fun find(id: Long): Answer
    fun update(newAnswer: Answer): Answer
    fun delete(answer: Answer)
    fun findAnswersByQuestion(question: Question): List<Answer>
    fun countAnswersInQuestion(question: Question): Int
}
