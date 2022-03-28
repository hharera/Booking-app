package com.englizya.repository

import com.englizya.model.dto.User
import com.englizya.model.model.Trip
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.SignupRequest
import com.englizya.model.response.LoginResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks

interface TripRepository {
    suspend fun getAllTrips(): Result<List<Trip>>
}