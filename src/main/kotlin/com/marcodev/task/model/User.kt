package com.marcodev.task.model

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Users")
data class User(
    @Id
    var id: UUID = UUID.randomUUID(),
    @Column(name="email", nullable = false, unique = true)
    var email: String? = null,
    @Column(name = "username", nullable = false)
    var username: String? = null,
    @Column(name= "password", nullable = false)
    var password: String? = null
)
