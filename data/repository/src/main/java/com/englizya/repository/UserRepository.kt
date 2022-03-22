package com.englizya.repository

import com.englizya.model.dto.User
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.SignupRequest
import com.englizya.model.response.LoginResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks

interface UserRepository {
    fun sendVerificationCode(phoneNumber: String, callback: OnVerificationStateChangedCallbacks)
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun signup(request: SignupRequest): Result<User>
    fun signInWithCredential(credential: PhoneAuthCredential): Task<AuthResult>
    fun getCurrentUser(): FirebaseUser?
    fun updatePassword(password: String): Task<Void>
    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    fun createCredential(verificationId: String, code: String): PhoneAuthCredential
    fun signup(credential: AuthCredential): Task<AuthResult>
    fun signOut()
}