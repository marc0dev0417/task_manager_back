package com.marcodev.task.controller.implementation

import com.marcodev.task.controller.UserControllerInterface
import com.marcodev.task.model.dto.UserDTO
import com.marcodev.task.service.implementation.UserImplementation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserControllerImp(
    val userService: UserImplementation
): UserControllerInterface {
    override fun getUsers(): ResponseEntity<List<UserDTO>> {
        return ResponseEntity.ok().body(userService.getUsers())
    }

    override fun signUp(userDTO: UserDTO): ResponseEntity<UserDTO> {
        return ResponseEntity.ok().body(userService.signUp(userDTO))
    }

    override fun login(username: String, password: String): ResponseEntity<*>? {
        return userService.Login(username, password)
    }
}