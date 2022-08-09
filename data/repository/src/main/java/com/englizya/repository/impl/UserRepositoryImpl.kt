package com.englizya.repository.impl

import android.util.Log
import com.englizya.api.UserService
import com.englizya.local.user.UserDao
import com.englizya.model.model.User
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.ResetPasswordRequest
import com.englizya.model.request.SignupRequest
import com.englizya.model.response.LoginResponse
import com.englizya.model.response.UserEditResponse
import com.englizya.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import java.io.File
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
            userService.getUser(token)

//            if (forceOnline) {
//                userService.getUser(token)
//                    .also {
//                    Log.d("UserRemote" , it.toString())
//                    userDao.insertUser(it)
//                }
//            } else {
//                userDao.getUser().also {
//                    Log.d("UserLocal" ,it.toString())
//
//                }


//            }
        }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Result<Any> =
        kotlin.runCatching {
            userService.resetPassword(resetPasswordRequest)
        }

    override suspend fun insertUser(user: User): Result<Any> = kotlin.runCatching {
        userDao.insertUser(user)
    }

    override suspend fun updateUser(
        token: String,
        name: String?,
        address: String?,
        image: File?
    ): Result<UserEditResponse> = kotlin.runCatching {
        Log.d("name , address, image", name + address + image?.name)

        if (address == "" && image == null) {
            Log.d("Name Update", "Done")
            userService.updateUserName(token, name!!)
        } else if (image == null) {
            Log.d("Name Address Update", "Done")

            userService.updateUserNameAndAddress(token, name!!, address!!)
        } else if (address == null) {
            Log.d("Name Image Update", "Done")

            userService.updateUserNameAndImage(token, name!!, image)
        } else {
            Log.d("All Update", "Done")

            userService.updateUser(token, name!!, address!!, image)
        }

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