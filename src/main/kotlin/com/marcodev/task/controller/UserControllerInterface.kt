package com.marcodev.task.controller

import com.marcodev.task.model.dto.UserDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Controller
interface UserControllerInterface {
    @RequestMapping(
        value = ["/users"],
        produces = ["application/json"],
        method = [RequestMethod.GET]
    )
    fun getUsers(): ResponseEntity<List<UserDTO>>

    @RequestMapping(
        value = ["/signUp"],
        consumes = ["application/json"],
        produces = ["application/json"],
        method = [RequestMethod.POST]
    )
    fun signUp(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO>

    @RequestMapping(
        value = ["/login"],
        produces = ["application/json"],
        method = [RequestMethod.GET],
        params = ["username", "password"]
    )
    fun login(@RequestParam(name = "username", required = true) username: String, @RequestParam(name = "password", required = true) password: String): ResponseEntity<*>?
}