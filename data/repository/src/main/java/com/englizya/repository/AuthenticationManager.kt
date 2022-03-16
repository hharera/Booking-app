package com.englizya.repository

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken

interface AuthenticationManager {

    fun signIn()
    fun signInByPhoneNumber()
    fun signOut()
    fun signInWithCredential(credential: PhoneAuthCredential): Task<AuthResult>
    fun getCurrentUser(): FirebaseUser?
    fun sendVerificationCode(
        phoneNumber: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )
    fun updatePassword(password: String): Task<Void>
    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    fun createCredential(verificationId: String, code: String): PhoneAuthCredential
    fun signup(credential: AuthCredential): Task<AuthResult>
}