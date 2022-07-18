package com.marcodev.task.service

import com.marcodev.task.model.dto.UserDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun getUsers(): List<UserDTO>?
    fun signUp(userDto: UserDTO): UserDTO?
    fun login(username: String, password: String): ResponseEntity<*>
}