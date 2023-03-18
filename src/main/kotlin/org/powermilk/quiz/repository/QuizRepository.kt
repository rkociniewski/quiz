package org.powermilk.quiz.repository

import org.powermilk.quiz.model.QuizForm
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuizRepository : JpaRepository<QuizForm, Long> {
    fun findByIsPublishedTrue(pageable: Pageable): Page<QuizForm>

    @Query("select q from Quiz q where q.name like %?1%")
    fun searchByName(name: String?, pageable: Pageable?): Page<QuizForm>
}