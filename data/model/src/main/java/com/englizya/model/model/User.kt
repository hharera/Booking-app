package com.englizya.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "User")
data class User(
    @PrimaryKey  val uid: String,
    val username: String,
    val phoneNumber: String,
    val name: String,
    val address: String? = null,
    val imageUrl: String? = null,
    val walletOtp: String,
)