package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rental_items")
data class RentalItemEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val category: String,
  val pricePerDay: Int,
  val pricePerWeek: Int,
  val deposit: Int,
  val ownerId: String,
  val ownerName: String,
  val ownerAvatar: String,
  val ownerPhone: String,
  val ownerVerified: Boolean = true,
  val location: String = "Kashmore",
  val distance: String = "1.2km",
  val images: String, // comma-separated URLs
  val description: String,
  val specs: String, // comma-separated bullet points
  val availableFrom: String = "Today",
  val status: String = "APPROVED", // "PENDING", "APPROVED", "REJECTED"
  val createdAt: Long = System.currentTimeMillis()
) {
  fun getImageList(): List<String> {
    return images.split(",").map { it.trim() }.filter { it.isNotEmpty() }
  }

  fun getSpecsList(): List<String> {
    return specs.split(",").map { it.trim() }.filter { it.isNotEmpty() }
  }
}
