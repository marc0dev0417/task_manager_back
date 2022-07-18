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
    @Column(name = "username")
    var username: String? = null,
    @Column(name= "password")
    var password: String? = null
)
