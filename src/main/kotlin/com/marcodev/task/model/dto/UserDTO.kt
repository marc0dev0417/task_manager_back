package com.marcodev.task.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class UserDTO(
    @JsonProperty("id") var id: UUID = UUID.randomUUID(),

    @JsonProperty("username") var username: String? = null,

    @JsonProperty("password") var password: String? = null
)
