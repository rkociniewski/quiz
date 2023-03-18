package org.powermilk.quiz.repository

import org.powermilk.quiz.model.Question
import org.powermilk.quiz.model.QuizForm
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, Long> {
    fun countByQuiz(quiz: QuizForm): Int
    fun findByQuiz(quiz: QuizForm): List<Question>
    fun countByQuizAndIsValidTrue(quiz: QuizForm): Int
    fun findyByQuizAndIsValidTrue(quiz: QuizForm): List<Question>
}