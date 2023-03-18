package org.powermilk.quiz.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
data class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Size(min = 2, max = 150, message = "The question should be between 2 and 150 characters")
    @NotNull(message = "Question text not provided")
    val text: String,

    @OneToMany(mappedBy = "question", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val answers: MutableSet<Answer>,

    @OneToOne
    var correctAnswer: Answer,

    @ManyToOne
    @JsonIgnore
    val quiz: QuizForm,

    @JsonIgnore
    var valid: Boolean,
)
