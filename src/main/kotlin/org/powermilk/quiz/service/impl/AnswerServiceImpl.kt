package org.powermilk.quiz.service.impl

import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.powermilk.quiz.model.Answer
import org.powermilk.quiz.model.Question
import org.powermilk.quiz.repository.AnswerRepository
import org.powermilk.quiz.service.AnswerService
import org.powermilk.quiz.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("AnswerService")
@Transactional
class AnswerServiceImpl @Autowired constructor(
    private val answerRepository: AnswerRepository,
    private val questionService: QuestionService,
) : AnswerService {

    private val logger = KotlinLogging.logger { }

    override fun find(id: Long): Answer {
        val answer = answerRepository.findByIdOrNull(id)
        if (answer == null) {
            logger.error("Answer $id not found")
            throw NullPointerException("Answer $id not found")
        }
        return answer
    }

    override fun save(answer: Answer) = answerRepository.save(answer)

    override fun update(newAnswer: Answer): Answer {
        val currentAnswer = find(newAnswer.id)
        newAnswer.id = currentAnswer.id
        return answerRepository.save(newAnswer)
    }

    override fun delete(answer: Answer) {
        require(
            !questionService.checkIsCorrectAnswer(answer.question, answer.id)
        ) { "The correct answer can't be deleted" }
        answerRepository.delete(answer)
    }

    override fun findAnswersByQuestion(question: Question) =
        answerRepository.findByQuestionOrderByOrderAsc(question)

    override fun countAnswersInQuestion(question: Question) = answerRepository.countByQuestion(question)
}