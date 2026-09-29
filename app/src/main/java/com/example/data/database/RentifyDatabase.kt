package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.RentifyDao
import com.example.data.entity.AppSettingEntity
import com.example.data.entity.ChatMessageEntity
import com.example.data.entity.RentalItemEntity
import com.example.data.entity.RentalRequestEntity
import com.example.data.entity.UserEntity

@Database(
  entities = [
    UserEntity::class,
    RentalItemEntity::class,
    RentalRequestEntity::class,
    ChatMessageEntity::class,
    AppSettingEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class RentifyDatabase : RoomDatabase() {
  abstract fun rentifyDao(): RentifyDao

  companion object {
    @Volatile
    private var INSTANCE: RentifyDatabase? = null

    fun getDatabase(context: Context): RentifyDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          RentifyDatabase::class.java,
          "rentify_marketplace.db"
        )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
