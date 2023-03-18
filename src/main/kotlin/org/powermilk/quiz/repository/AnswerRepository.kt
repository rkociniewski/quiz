package org.powermilk.quiz.repository

import org.powermilk.quiz.model.Answer
import org.powermilk.quiz.model.Question
import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository : JpaRepository<Answer, Long> {
    fun countByQuestion(question: Question): Int
    fun findByQuestionOrderByOrderAsc(question: Question): List<Answer>
}