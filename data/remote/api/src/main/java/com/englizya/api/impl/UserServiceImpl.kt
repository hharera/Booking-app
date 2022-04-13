package com.englizya.api.impl

import com.englizya.api.UserService
import com.englizya.api.utils.Routing.FETCH_USER
import com.englizya.api.utils.Routing.LOGIN
import com.englizya.api.utils.Routing.SIGNUP
import com.englizya.model.model.User
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.SignupRequest
import com.englizya.model.response.LoginResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders.Authorization
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val client: HttpClient
) : UserService {

    override suspend fun login(request: LoginRequest): LoginResponse =
        client.post<LoginResponse> {
            url(LOGIN)
            contentType(ContentType.Application.Json)
            body = request
        }

    override suspend fun signup(request: SignupRequest): User =
        client.post<User> {
            url(SIGNUP)
            contentType(ContentType.Application.Json)
            body = request
        }

    override suspend fun getUser(token: String): User =
        client.post {
            url(FETCH_USER)
            headers.append(
                Authorization,
                "Bearer $token"
            )
        }
}