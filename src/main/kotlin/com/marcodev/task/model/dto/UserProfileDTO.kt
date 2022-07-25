package com.marcodev.task.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class UserProfileDTO(
    @JsonProperty("id") var id: UUID? = UUID.randomUUID(),

    @JsonProperty("email") var email: String? = null,

    @JsonProperty("username") var username: String? = null
    )
