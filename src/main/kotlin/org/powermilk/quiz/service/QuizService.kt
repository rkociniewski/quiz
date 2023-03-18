package org.powermilk.quiz.service

import org.powermilk.quiz.model.QuizForm
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuizService {
    fun save(quiz: QuizForm): QuizForm
    fun findAll(pageable: Pageable): Page<QuizForm>
    fun findAllPublished(pageable: Pageable): Page<QuizForm>
    fun find(id: Long): QuizForm
    fun update(newQuiz: QuizForm): QuizForm
    fun delete(quiz: QuizForm)
    fun search(query: String, pageable: Pageable): Page<QuizForm>
    fun publishQuiz(quiz: QuizForm)
}