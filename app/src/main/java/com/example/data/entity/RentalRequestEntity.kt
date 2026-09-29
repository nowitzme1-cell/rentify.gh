package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rental_requests")
data class RentalRequestEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val itemId: Long,
  val itemTitle: String,
  val itemImage: String,
  val pricePerDay: Int,
  val ownerId: String,
  val ownerName: String,
  val ownerPhone: String,
  val renterId: String,
  val renterName: String,
  val renterPhone: String,
  val startDate: String,
  val endDate: String,
  val daysCount: Int,
  val rentAmount: Int,
  val serviceFee: Int, // 10% commission
  val depositAmount: Int,
  val totalAmount: Int,
  val message: String,
  val status: String = "PENDING", // "PENDING", "ACCEPTED", "REJECTED"
  val createdAt: Long = System.currentTimeMillis()
)
