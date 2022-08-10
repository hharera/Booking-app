package com.englizya.api.impl

import com.englizya.api.UserService
import com.englizya.api.utils.Routing.EDIT_USER
import com.englizya.api.utils.Routing.FETCH_USER
import com.englizya.api.utils.Routing.LOGIN
import com.englizya.api.utils.Routing.RESET_PASSWORD
import com.englizya.api.utils.Routing.SIGNUP
import com.englizya.model.model.User
import com.englizya.model.request.*
import com.englizya.model.response.LoginResponse
import com.englizya.model.response.UserEditResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders.Authorization
import io.ktor.util.*
import java.io.File

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
    override suspend fun updateUser(token: String, name: String, address: String, image: File): UserEditResponse =
        client.submitFormWithBinaryData<UserEditResponse>(
            url = EDIT_USER, formData =
            formData {
                append(FormPart("name", name))
                append(FormPart("address", address))
                append(FormPart("image", image.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    append(HttpHeaders.ContentDisposition, "filename=image.png")
                }))
            })
        {
            headers.append(Authorization, "Bearer $token")
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }


        }
}