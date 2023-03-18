package org.powermilk.quiz.service.impl

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.powermilk.quiz.model.QuizForm
import org.powermilk.quiz.repository.QuizRepository
import org.powermilk.quiz.service.QuestionService
import org.powermilk.quiz.service.QuizService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service("QuizService")
@Transactional
class QuizServiceImpl @Autowired constructor(
    private val quizRepository: QuizRepository,
    private val questionService: QuestionService
) : QuizService {

    private val logger = KotlinLogging.logger { }

    override fun save(quiz: QuizForm) = quizRepository.save(quiz)
    override fun findAll(pageable: Pageable): Page<QuizForm> = quizRepository.findAll(pageable)
    override fun findAllPublished(pageable: Pageable): Page<QuizForm> = quizRepository.findByIsPublishedTrue(pageable)

    override fun find(id: Long): QuizForm {
        val quiz = quizRepository.findByIdOrNull(id)
        if (quiz == null) {
            logger.error("Quiz $id not found")
            throw EntityNotFoundException("Quiz $id not found")
        }
        return quiz
    }

    override fun update(newQuiz: QuizForm): QuizForm {
        val currentQuiz = find(newQuiz.id)
        newQuiz.id = currentQuiz.id
        return quizRepository.save(newQuiz)
    }

    override fun delete(quiz: QuizForm) = quizRepository.delete(quiz)

    override fun search(query: String, pageable: Pageable) =
        quizRepository.searchByName(query, pageable)

    @Throws(IllegalArgumentException::class)
    override fun publishQuiz(quiz: QuizForm) {
        val count = questionService.countValidQuestionsInQuiz(quiz)

        require(count > 0) { "The quiz doesn't have any valid questions" }
        
        quiz.published = true
        quizRepository.save<QuizForm>(quiz)
    }
}