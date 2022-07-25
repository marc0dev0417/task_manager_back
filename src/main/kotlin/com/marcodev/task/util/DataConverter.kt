package com.marcodev.task.util

import com.marcodev.task.model.User
import com.marcodev.task.model.dto.UserDTO
import com.marcodev.task.model.dto.UserProfileDTO

class DataConverter {

    companion object{
        fun userToDTO(user: User): UserDTO{
            return UserDTO(
                id = user.id,
                email = user.email,
                username = user.username,
                password = user.password
            )
        }
        fun userFromDTO(userDTO: UserDTO): User{
            return User(
                id = userDTO.id,
                email = userDTO.email,
                username = userDTO.username,
                password = userDTO.password
            )
        }
        fun userProfileToDTO(user: User): UserProfileDTO{
            return UserProfileDTO(
                id = user.id,
                email = user.email,
                username = user.username
            )
        }
    }
}