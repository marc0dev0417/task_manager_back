package com.marcodev.task.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.marcodev.task.security.JwtRequestFilter
import com.marcodev.task.service.implementation.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig() {

    @Autowired
    lateinit var jwtRequestFilter: JwtRequestFilter

    @Autowired
    lateinit var jwtUserDetailsService: JwtUserDetailsService

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder){
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(BCryptPasswordEncoder())
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    @Throws(Exception::class)
    @Bean
    fun configure(http: HttpSecurity?): SecurityFilterChain {
        http?.csrf()?.disable()?.authorizeRequests()?.antMatchers("/signUp", "/login")
            ?.permitAll()?.anyRequest()?.authenticated()?.and()?.exceptionHandling()
            ?.authenticationEntryPoint{ request: HttpServletRequest?, response: HttpServletResponse, authException: AuthenticationException? ->

                val responseMap: MutableMap<String, Any> = HashMap()
                val mapper = ObjectMapper()
                response.status = 401
                responseMap["error"] = true
                responseMap["message"] = "Unauthorized"
                response.setHeader("content-type", "application/json")
                val responseMsg = mapper.writeValueAsString(responseMap)
                response.writer.write(responseMsg)

            }?.and()?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Then I will add the filter with JWT...
            ?.and()?.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http!!.build()
    }
}