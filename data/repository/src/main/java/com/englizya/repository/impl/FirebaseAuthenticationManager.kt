package com.englizya.repository.impl

import com.englizya.api.RemoteUserService
import com.englizya.model.dto.User
import com.englizya.model.request.SignupRequest
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.englizya.repository.AuthenticationManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseAuthenticationManager @Inject constructor(
    private val auth: FirebaseAuth,
    private val userService: RemoteUserService,
) : AuthenticationManager {

    override fun signIn() {
    }

    fun loginAnonymously(): Task<AuthResult> = auth.signInAnonymously()

    override fun signInByPhoneNumber() {
    }

    override fun signOut() = auth.signOut()

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

    override fun signInWithEmailAndPassword(email: String, password : String): Task<AuthResult> =
        auth.signInWithEmailAndPassword(email, password)

    override fun createCredential(verificationId : String, code: String) =
        PhoneAuthProvider.getCredential(verificationId, code)

    override fun signup(credential: AuthCredential) =
        auth.signInWithCredential(credential)

    override suspend fun signup(request: SignupRequest) = runCatching {
        userService.signup(request)
    }
}