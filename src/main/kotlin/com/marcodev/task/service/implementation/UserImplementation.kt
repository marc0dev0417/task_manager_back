package com.marcodev.task.service.implementation

import com.marcodev.task.model.dto.UserDTO
import com.marcodev.task.repository.UserRepository
import com.marcodev.task.security.JwtTokenUtil
import com.marcodev.task.service.UserService
import com.marcodev.task.util.DataConverter
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.text.DateFormat
import java.util.*

@Service
class UserImplementation(
   private val userRepository: UserRepository,
   private val authenticationManager: AuthenticationManager,
   private val userDetailsService: JwtUserDetailsService,
   private val jwtTokenUtil: JwtTokenUtil
): UserService {
    override fun getUsers(): List<UserDTO>? {
        return userRepository.findAll().map { DataConverter.userToDTO(it) }
    }

    override fun signUp(userDto: UserDTO): UserDTO? {
            val userItem = DataConverter.userFromDTO(userDto)
            userItem.id = UUID.randomUUID()
            userItem.password = BCryptPasswordEncoder().encode(userItem.password)
            val userToSave = userRepository.save(userItem)
            return DataConverter.userToDTO(userToSave)

    }

    override fun Login(username: String, password: String): ResponseEntity<*> {
        val responseMap: MutableMap<String, Any> = mutableMapOf()
        val user = userRepository.findByUsername(username)
        val dataFormatMedium = DateFormat.getDateInstance(DateFormat.MEDIUM)

        try {
            val auth: Authentication =
                authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(username,
                    password)
                )

            if (auth.isAuthenticated) {

                val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
                val token: String = jwtTokenUtil.generateToken(userDetails) ?: ""
                responseMap["error"] = false
                responseMap["message"] = "Logged In"
                responseMap["token"] = token
                responseMap["token_expired"] = jwtTokenUtil.isTokenExpired(token).toString()
                responseMap["expired_date"] = dataFormatMedium.format(jwtTokenUtil.getExpirationDateFromToken(token)).toString()
                responseMap["user"] = user
                return ResponseEntity.ok(responseMap)

            } else {

                responseMap["error"] = true
                responseMap["message"] = "Invalid Credentials"
                return ResponseEntity.status(401).body(responseMap)

            }

        } catch (e: DisabledException) {
            e.printStackTrace();
            responseMap["error"] = true
            responseMap["message"] = "User is disabled"
            return ResponseEntity.status(500).body(responseMap);
        } catch (e: BadCredentialsException) {

            responseMap["error"] = true
            responseMap["message"] = "Invalid Credentials"
            return ResponseEntity.status(401).body(responseMap);
        } catch (e: Exception) {

            e.printStackTrace();
            responseMap["error"] = true
            responseMap["message"] = "Something went wrong";
            return ResponseEntity.status(500).body(responseMap);

        }
    }
}