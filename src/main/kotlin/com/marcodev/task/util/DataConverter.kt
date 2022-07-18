package com.marcodev.task.util

import com.marcodev.task.model.User
import com.marcodev.task.model.dto.UserDTO

class DataConverter {

    companion object{
        fun userToDTO(user: User): UserDTO{
            return UserDTO(
                id = user.id,
                username = user.username,
                password = user.password
            )
        }
        fun userFromDTO(userDTO: UserDTO): User{
            return User(
                id = userDTO.id!!,
                username = userDTO.username,
                password = userDTO.password
            )
        }
    }
}