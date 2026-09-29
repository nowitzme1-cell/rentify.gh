package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.AppSettingEntity
import com.example.data.entity.ChatMessageEntity
import com.example.data.entity.RentalItemEntity
import com.example.data.entity.RentalRequestEntity
import com.example.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RentifyDao {

  // --- Users ---
  @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
  fun getUserById(userId: String): Flow<UserEntity?>

  @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
  suspend fun getUserByIdOnce(userId: String): UserEntity?

  @Query("SELECT * FROM users ORDER BY joinedDate DESC")
  fun getAllUsers(): Flow<List<UserEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUser(user: UserEntity)

  @Update
  suspend fun updateUser(user: UserEntity)

  @Query("UPDATE users SET isVerified = :verified, verificationStatus = :status WHERE id = :userId")
  suspend fun updateVerification(userId: String, verified: Boolean, status: String)

  @Query("UPDATE users SET isBlocked = :blocked WHERE id = :userId")
  suspend fun updateUserBlockStatus(userId: String, blocked: Boolean)

  // --- Rental Items ---
  @Query("SELECT * FROM rental_items WHERE status = 'APPROVED' ORDER BY id DESC")
  fun getApprovedItems(): Flow<List<RentalItemEntity>>

  @Query("SELECT * FROM rental_items ORDER BY id DESC")
  fun getAllItems(): Flow<List<RentalItemEntity>>

  @Query("SELECT * FROM rental_items WHERE status = :status ORDER BY id DESC")
  fun getItemsByStatus(status: String): Flow<List<RentalItemEntity>>

  @Query("SELECT * FROM rental_items WHERE id = :itemId LIMIT 1")
  fun getItemById(itemId: Long): Flow<RentalItemEntity?>

  @Query("SELECT * FROM rental_items WHERE id = :itemId LIMIT 1")
  suspend fun getItemByIdOnce(itemId: Long): RentalItemEntity?

  @Query("SELECT * FROM rental_items WHERE ownerId = :ownerId ORDER BY id DESC")
  fun getItemsByOwner(ownerId: String): Flow<List<RentalItemEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertItem(item: RentalItemEntity): Long

  @Query("UPDATE rental_items SET status = :status WHERE id = :itemId")
  suspend fun updateItemStatus(itemId: Long, status: String)

  @Query("DELETE FROM rental_items WHERE id = :itemId")
  suspend fun deleteItem(itemId: Long)

  // --- Rental Requests ---
  @Query("SELECT * FROM rental_requests WHERE renterId = :renterId ORDER BY createdAt DESC")
  fun getRequestsByRenter(renterId: String): Flow<List<RentalRequestEntity>>

  @Query("SELECT * FROM rental_requests WHERE ownerId = :ownerId ORDER BY createdAt DESC")
  fun getRequestsByOwner(ownerId: String): Flow<List<RentalRequestEntity>>

  @Query("SELECT * FROM rental_requests ORDER BY createdAt DESC")
  fun getAllRequests(): Flow<List<RentalRequestEntity>>

  @Query("SELECT * FROM rental_requests WHERE id = :requestId LIMIT 1")
  fun getRequestById(requestId: Long): Flow<RentalRequestEntity?>

  @Query("SELECT * FROM rental_requests WHERE id = :requestId LIMIT 1")
  suspend fun getRequestByIdOnce(requestId: Long): RentalRequestEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertRequest(request: RentalRequestEntity): Long

  @Query("UPDATE rental_requests SET status = :status WHERE id = :requestId")
  suspend fun updateRequestStatus(requestId: Long, status: String)

  // --- Chat Messages ---
  @Query("SELECT * FROM chat_messages WHERE requestId = :requestId ORDER BY timestamp ASC")
  fun getMessagesForRequest(requestId: Long): Flow<List<ChatMessageEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertChatMessage(message: ChatMessageEntity): Long

  // --- Settings ---
  @Query("SELECT value FROM app_settings WHERE `key` = :key LIMIT 1")
  suspend fun getSetting(key: String): String?

  @Query("SELECT * FROM app_settings")
  fun getAllSettings(): Flow<List<AppSettingEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveSetting(setting: AppSettingEntity)
}
