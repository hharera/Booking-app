package com.englizya.repository.impl

import com.englizya.api.UserService
import com.englizya.model.model.User
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.SignupRequest
import com.englizya.model.response.LoginResponse
import com.englizya.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val auth: FirebaseAuth,
) : UserRepository {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> = runCatching {
        userService.login(request)
    }

    fun loginAnonymously(): Task<AuthResult> = auth.signInAnonymously()

    override fun signOut() = auth.signOut()

    override suspend fun fetchUser(token : String): Result<User> = kotlin.runCatching {
        userService.getUser(token)
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