package com.englizya.repository.impl

import com.englizya.api.UserService
import com.englizya.local.UserDao
import com.englizya.model.model.User
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.ResetPasswordRequest
import com.englizya.model.request.SignupRequest
import com.englizya.model.response.LoginResponse
import com.englizya.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class UserRepositoryImpl constructor(
    private val userService: UserService,
    private val auth: FirebaseAuth,
    private val userDao: UserDao,
) : UserRepository {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> = runCatching {
        userService.login(request)
    }

    fun loginAnonymously(): Task<AuthResult> = auth.signInAnonymously()

    override fun signOut() = auth.signOut()

    override suspend fun getUser(token: String, forceOnline: Boolean): Result<User> =
        kotlin.runCatching {
            if (forceOnline) {
                userService.getUser(token)
            } else {
                userDao.getUser()
            }
        }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Result<Any> =
        kotlin.runCatching {
            userService.resetPassword(resetPasswordRequest)
        }

    override fun signInWithCredential(credential: PhoneAuthCredential) =
        auth.signInWithCredential(credential)

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override fun sendVerificationCode(
        phoneNumber: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun updatePassword(password: String) =
        auth.currentUser!!.updatePassword(password)

    override fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
        auth.signInWithEmailAndPassword(email, password)

    override fun createCredential(verificationId: String, code: String) =
        PhoneAuthProvider.getCredential(verificationId, code)

    override fun signup(credential: AuthCredential) =
        auth.signInWithCredential(credential)

    override suspend fun signup(request: SignupRequest) = runCatching {
        userService.signup(request)
    }
}