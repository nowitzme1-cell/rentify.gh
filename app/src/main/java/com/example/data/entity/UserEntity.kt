package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
  @PrimaryKey val id: String,
  val name: String,
  val phone: String,
  val city: String,
  val avatarUrl: String,
  val isVerified: Boolean = false,
  val verificationStatus: String = "NONE", // "NONE", "PENDING", "VERIFIED"
  val cnicFront: String? = null,
  val cnicBack: String? = null,
  val cnicSelfie: String? = null,
  val itemsCount: Int = 0,
  val rating: Float = 4.9f,
  val ratingCount: Int = 18,
  val totalEarned: Int = 0,
  val isBlocked: Boolean = false,
  val joinedDate: String = "Nov 2024"
)
