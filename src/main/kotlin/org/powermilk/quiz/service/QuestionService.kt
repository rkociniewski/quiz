package org.powermilk.quiz.service

import org.powermilk.quiz.model.Answer
import org.powermilk.quiz.model.Question
import org.powermilk.quiz.model.QuizForm

interface QuestionService {
    fun save(question: Question): Question
    fun find(id: Long): Question
    fun findQuestionsByQuiz(quiz: QuizForm): List<Question>
    fun findValidQuestionsByQuiz(quiz: QuizForm): List<Question>
    fun update(newQuestion: Question): Question
    fun delete(question: Question)
    fun checkIsCorrectAnswer(question: Question, answerId: Long): Boolean
    fun setCorrectAnswer(question: Question, answer: Answer)
    fun getCorrectAnswer(question: Question): Answer
    fun addAnswerToQuestion(answer: Answer, question: Question): Answer
    fun countQuestionsInQuiz(quiz: QuizForm): Int
    fun countValidQuestionsInQuiz(quiz: QuizForm): Int
}