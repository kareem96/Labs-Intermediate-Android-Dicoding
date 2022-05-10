package com.kareemdev.dicodingstory.domain.repository

import com.kareemdev.dicodingstory.data.LoginRequest
import com.kareemdev.dicodingstory.data.RegisterRequest
import com.kareemdev.dicodingstory.data.remote.ApiService

class AuthRepository(private val api: ApiService) {
    fun login(body: LoginRequest) = api.login(body)
    fun register(body: RegisterRequest) = api.register(body)
}