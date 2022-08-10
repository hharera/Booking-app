package com.englizya.api

import com.englizya.model.model.User
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.ResetPasswordRequest
import com.englizya.model.request.SignupRequest
import com.englizya.model.request.UserEditRequest
import com.englizya.model.response.LoginResponse
import com.englizya.model.response.UserEditResponse
import java.io.File

interface UserService {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun signup(request: SignupRequest): User
    suspend fun getUser(token: String): User
    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Any
    suspend fun updateUser(token: String, name: String , address: String , image: File): UserEditResponse



}