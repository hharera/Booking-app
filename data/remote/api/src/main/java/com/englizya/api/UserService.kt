package com.englizya.api

import com.englizya.model.dto.User
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.SignupRequest
import com.englizya.model.response.LoginResponse

interface UserService {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun signup(request: SignupRequest): User
}