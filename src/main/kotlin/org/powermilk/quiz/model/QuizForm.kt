package org.powermilk.quiz.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "quiz")
data class QuizForm(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 messages.")
    @NotNull(message = "Please, provide a name")
    val name: String,

    @Size(max = 200, message = "The description can't be longer than 200 characters.")
    @NotNull(message = "Please, provide a description")
    val description: String,

    @OneToMany(mappedBy = "quiz", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    val questions: Set<Question>,
    var published: Boolean,
)