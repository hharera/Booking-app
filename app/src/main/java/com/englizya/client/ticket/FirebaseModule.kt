package com.englizya.client.ticket

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.dsl.module

val firebaseModule = module {

    single {
        FirebaseFirestore.getInstance()
    }

    single {
        FirebaseStorage.getInstance()
    }

    single {
        FirebaseDatabase.getInstance()
    }

    single {
        FirebaseAuth.getInstance(get())
    }
}