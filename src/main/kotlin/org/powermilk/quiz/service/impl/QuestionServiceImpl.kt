package org.powermilk.quiz.service.impl

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.powermilk.quiz.model.Answer
import org.powermilk.quiz.model.Question
import org.powermilk.quiz.model.QuizForm
import org.powermilk.quiz.repository.QuestionRepository
import org.powermilk.quiz.service.AnswerService
import org.powermilk.quiz.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service("QuestionService")
@Transactional
class QuestionServiceImpl @Autowired constructor(
    private val questionRepository: QuestionRepository,
    private val answerService: AnswerService
) : QuestionService {
    private val logger = KotlinLogging.logger { }

    override fun save(question: Question) = questionRepository.save(question)

    @Throws(EntityNotFoundException::class)
    override fun find(id: Long): Question {
        val question = questionRepository.findByIdOrNull(id)
        if (question == null) {
            logger.error("Question $id not found")
            throw EntityNotFoundException("Question $id not found")
        }
        return question
    }

    override fun update(newQuestion: Question): Question {
        val currentQuestion = find(newQuestion.id)
        newQuestion.id = currentQuestion.id
        return questionRepository.save(newQuestion)
    }

    override fun delete(question: Question) = questionRepository.delete(question)

    override fun checkIsCorrectAnswer(question: Question, answerId: Long): Boolean {
        return if (!question.valid) false
        else question.correctAnswer.id == answerId
    }

    override fun findQuestionsByQuiz(quiz: QuizForm): List<Question> = questionRepository.findByQuiz(quiz)

    override fun findValidQuestionsByQuiz(quiz: QuizForm) =
        questionRepository.findyByQuizAndIsValidTrue(quiz)

    override fun setCorrectAnswer(question: Question, answer: Answer) {
        question.correctAnswer = answer
        save(question)
    }

    override fun addAnswerToQuestion(answer: Answer, question: Question): Answer {
        val count = answerService.countAnswersInQuestion(question)
        val newAnswer: Answer = updateAndSaveAnswer(answer, question)
        checkQuestionInitialization(question, count, newAnswer)
        return newAnswer
    }

    private fun checkQuestionInitialization(question: Question, count: Int, newAnswer: Answer) {
        checkAndUpdateQuestionValidity(question)
        setCorrectAnswerIfFirst(question, count, newAnswer)
    }

    private fun updateAndSaveAnswer(answer: Answer, question: Question): Answer {
        answer.question = question
        return answerService.save(answer)
    }

    private fun checkAndUpdateQuestionValidity(question: Question) {
        if (!question.valid) {
            question.valid = true
            save(question)
        }
    }

    private fun setCorrectAnswerIfFirst(question: Question, count: Int, newAnswer: Answer) {
        if (count == 0) {
            question.correctAnswer = newAnswer
            questionRepository.save<Question>(question)
        }
    }

    override fun countQuestionsInQuiz(quiz: QuizForm) = questionRepository.countByQuiz(quiz)

    override fun countValidQuestionsInQuiz(quiz: QuizForm) = questionRepository.countByQuizAndIsValidTrue(quiz)

    override fun getCorrectAnswer(question: Question) = question.correctAnswer
}