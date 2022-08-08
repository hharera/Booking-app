package com.englizya.api.impl

import com.englizya.api.UserService
import com.englizya.api.utils.RequestParam
import com.englizya.api.utils.Routing
import com.englizya.api.utils.Routing.EDIT_USER
import com.englizya.api.utils.Routing.FETCH_USER
import com.englizya.api.utils.Routing.LOGIN
import com.englizya.api.utils.Routing.RESET_PASSWORD
import com.englizya.api.utils.Routing.SIGNUP
import com.englizya.model.model.User
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.ResetPasswordRequest
import com.englizya.model.request.SignupRequest
import com.englizya.model.request.UserEditRequest
import com.englizya.model.response.LoginResponse
import com.englizya.model.response.UserEditResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders.Authorization
import io.ktor.util.*
import java.text.Normalizer

class UserServiceImpl constructor(
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
        client.get {
            url(FETCH_USER)
            headers.append(
                Authorization,
                "Bearer $token"
            )
        }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Any =
        client.post(RESET_PASSWORD) {
            contentType(ContentType.Application.Json)
            body = resetPasswordRequest
        }

    @OptIn(InternalAPI::class)
    override suspend fun updateUser(token: String, request: UserEditRequest): UserEditResponse =
        client.put(EDIT_USER) {
            headers.append(Authorization, "Bearer $token")
            body = MultiPartFormDataContent(
                formData {
                    append(FormPart("name", request.name))
                    append(FormPart("address", request.address))
                    append("image", request.image.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "multipart/form-data;")

                    })
                }
            )
        }

}