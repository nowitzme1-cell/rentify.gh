package com.example.data.repository

import com.example.data.dao.RentifyDao
import com.example.data.entity.AppSettingEntity
import com.example.data.entity.ChatMessageEntity
import com.example.data.entity.RentalItemEntity
import com.example.data.entity.RentalRequestEntity
import com.example.data.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RentifyRepository(private val dao: RentifyDao) {

  val approvedItems: Flow<List<RentalItemEntity>> = dao.getApprovedItems()
  val allItems: Flow<List<RentalItemEntity>> = dao.getAllItems()
  val allUsers: Flow<List<UserEntity>> = dao.getAllUsers()
  val allRequests: Flow<List<RentalRequestEntity>> = dao.getAllRequests()

  fun getItemById(itemId: Long): Flow<RentalItemEntity?> = dao.getItemById(itemId)
  fun getItemsByStatus(status: String): Flow<List<RentalItemEntity>> = dao.getItemsByStatus(status)
  fun getItemsByOwner(ownerId: String): Flow<List<RentalItemEntity>> = dao.getItemsByOwner(ownerId)

  fun getUserById(userId: String): Flow<UserEntity?> = dao.getUserById(userId)

  fun getRequestsByRenter(renterId: String): Flow<List<RentalRequestEntity>> = dao.getRequestsByRenter(renterId)
  fun getRequestsByOwner(ownerId: String): Flow<List<RentalRequestEntity>> = dao.getRequestsByOwner(ownerId)
  fun getRequestById(requestId: Long): Flow<RentalRequestEntity?> = dao.getRequestById(requestId)

  fun getChatMessages(requestId: Long): Flow<List<ChatMessageEntity>> = dao.getMessagesForRequest(requestId)

  suspend fun insertItem(item: RentalItemEntity): Long = dao.insertItem(item)
  suspend fun updateItemStatus(itemId: Long, status: String) = dao.updateItemStatus(itemId, status)
  suspend fun deleteItem(itemId: Long) = dao.deleteItem(itemId)

  suspend fun insertRequest(request: RentalRequestEntity): Long = dao.insertRequest(request)
  suspend fun updateRequestStatus(requestId: Long, status: String) = dao.updateRequestStatus(requestId, status)

  suspend fun sendChatMessage(message: ChatMessageEntity) = dao.insertChatMessage(message)

  suspend fun updateUser(user: UserEntity) = dao.insertUser(user)
  suspend fun verifyUser(userId: String, verified: Boolean) {
    val status = if (verified) "VERIFIED" else "REJECTED"
    dao.updateVerification(userId, verified, status)
  }
  suspend fun setUserBlock(userId: String, blocked: Boolean) = dao.updateUserBlockStatus(userId, blocked)

  suspend fun getSetting(key: String): String? = dao.getSetting(key)
  suspend fun saveSetting(key: String, value: String) = dao.saveSetting(AppSettingEntity(key, value))

  // Seed default data if empty
  fun checkAndSeedInitialData() {
    CoroutineScope(Dispatchers.IO).launch {
      val existingUsers = dao.getUserByIdOnce("user_salman")
      if (existingUsers == null) {
        seedDefaults()
      }
    }
  }

  private suspend fun seedDefaults() {
    // 1. Users
    val salman = UserEntity(
      id = "user_salman",
      name = "Salman Khan",
      phone = "0301-7894561",
      city = "Kashmore",
      avatarUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200",
      isVerified = true,
      verificationStatus = "VERIFIED",
      cnicFront = "https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=400",
      cnicBack = "https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=400",
      cnicSelfie = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
      itemsCount = 12,
      rating = 4.9f,
      ratingCount = 23,
      totalEarned = 38000,
      joinedDate = "Aug 2024"
    )

    val bilal = UserEntity(
      id = "user_bilal",
      name = "Bilal Ahmad",
      phone = "0300-1234567",
      city = "Kashmore",
      avatarUrl = "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=200",
      isVerified = false,
      verificationStatus = "PENDING",
      cnicFront = "https://images.unsplash.com/photo-1557804506-669a67965ba0?w=400",
      cnicBack = "https://images.unsplash.com/photo-1557804506-669a67965ba0?w=400",
      cnicSelfie = "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=400",
      itemsCount = 0,
      rating = 5.0f,
      ratingCount = 4,
      totalEarned = 0,
      joinedDate = "Oct 2024"
    )

    val tariq = UserEntity(
      id = "user_tariq",
      name = "Tariq Baloch",
      phone = "0333-8765432",
      city = "Usta Muhammad",
      avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200",
      isVerified = true,
      verificationStatus = "VERIFIED",
      itemsCount = 6,
      rating = 4.8f,
      ratingCount = 15,
      totalEarned = 24500,
      joinedDate = "Sep 2024"
    )

    val zainab = UserEntity(
      id = "user_zainab",
      name = "Zainab Noor",
      phone = "0345-9988776",
      city = "Kandhkot",
      avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200",
      isVerified = true,
      verificationStatus = "VERIFIED",
      itemsCount = 4,
      rating = 4.9f,
      ratingCount = 11,
      totalEarned = 19000,
      joinedDate = "Jul 2024"
    )

    dao.insertUser(salman)
    dao.insertUser(bilal)
    dao.insertUser(tariq)
    dao.insertUser(zainab)

    // 2. Rental Items (Including 8 core items + 2 pending for admin approval demo)
    val item1 = RentalItemEntity(
      title = "Sony Alpha A6400 with 16-50mm Lens - Excellent Condition",
      category = "Camera",
      pricePerDay = 2000,
      pricePerWeek = 12000,
      deposit = 5000,
      ownerId = salman.id,
      ownerName = salman.name,
      ownerAvatar = salman.avatarUrl,
      ownerPhone = salman.phone,
      ownerVerified = true,
      location = "Kashmore",
      distance = "1.2km away",
      images = "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800,https://images.unsplash.com/photo-1502982720700-bfff97f2ecac?w=800,https://images.unsplash.com/photo-1512790182412-b19e6d62bc39?w=800",
      description = "Sony A6400 in mint condition, bought 6 months ago from Karachi, used only for weddings. Includes 1 Sony original battery, dual charger, original shoulder bag, 32GB high-speed SanDisk card, and purchase receipt. Zero scratches on sensor, shutter count only 2500.",
      specs = "Brand Sony, Model A6400 4K, 24.2 MP APS-C Sensor, 16-50mm f/3.5-5.6 Lens, 4K HDR Video, Pickup from Kashmore Bus Stand, 100% Genuine",
      availableFrom = "Today",
      status = "APPROVED"
    )

    val item2 = RentalItemEntity(
      title = "Canon EOS 200D DSLR Camera with Kit Lens",
      category = "Camera",
      pricePerDay = 1500,
      pricePerWeek = 9000,
      deposit = 4000,
      ownerId = salman.id,
      ownerName = salman.name,
      ownerAvatar = salman.avatarUrl,
      ownerPhone = salman.phone,
      ownerVerified = true,
      location = "Kashmore",
      distance = "1.8km away",
      images = "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=800,https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=800",
      description = "Lightweight Canon 200D with 18-55mm STM lens. Great for outdoor portraits, family gatherings, and wedding vlogs. Includes extra battery and 64GB memory card.",
      specs = "Brand Canon, 24.2 MP, Dual Pixel AF, Touchscreen, Wifi / Bluetooth transfer, Condition 9/10",
      availableFrom = "Tomorrow",
      status = "APPROVED"
    )

    val item3 = RentalItemEntity(
      title = "Dawlance 1.5 Ton Inverter AC (Cooling & Heating)",
      category = "AC",
      pricePerDay = 1000,
      pricePerWeek = 6000,
      deposit = 6000,
      ownerId = tariq.id,
      ownerName = tariq.name,
      ownerAvatar = tariq.avatarUrl,
      ownerPhone = tariq.phone,
      ownerVerified = true,
      location = "Kashmore",
      distance = "2.4km away",
      images = "https://images.unsplash.com/photo-1621905251918-48416bd8575a?w=800",
      description = "Clean Dawlance Chrome inverter AC. Available for event halls, guest rooms, or temporary office setup during peak hot days. Quick cooling within 5 minutes.",
      specs = "Dawlance Enercon, 1.5 Ton T3 Inverter, Energy class A++, Remote control included, Super quiet operation",
      availableFrom = "Today",
      status = "APPROVED"
    )

    val item4 = RentalItemEntity(
      title = "Gree 1 Ton Energy Saver Split AC",
      category = "AC",
      pricePerDay = 1200,
      pricePerWeek = 7200,
      deposit = 5000,
      ownerId = tariq.id,
      ownerName = tariq.name,
      ownerAvatar = tariq.avatarUrl,
      ownerPhone = tariq.phone,
      ownerVerified = true,
      location = "Usta Muhammad",
      distance = "3.1km away",
      images = "https://images.unsplash.com/photo-1585338107529-13afc5f02586?w=800",
      description = "Gree Fairy series 1 ton split unit. Clean indoor and outdoor units. Perfect for bedroom temporary cooling in Usta Muhammad.",
      specs = "Gree Fairy Series, 1 Ton Inverter, Fast Chill Turbo Mode, Stabilizer not required",
      availableFrom = "Today",
      status = "APPROVED"
    )

    val item5 = RentalItemEntity(
      title = "Royal Velvet 5-Seater Sofa Set for Events",
      category = "Furniture",
      pricePerDay = 800,
      pricePerWeek = 4500,
      deposit = 3000,
      ownerId = zainab.id,
      ownerName = zainab.name,
      ownerAvatar = zainab.avatarUrl,
      ownerPhone = zainab.phone,
      ownerVerified = true,
      location = "Kashmore",
      distance = "1.5km away",
      images = "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=800,https://images.unsplash.com/photo-1493663284031-b7e3aefcae8e?w=800",
      description = "Emerald green velvet luxury sofa set (3+1+1) with gold trim legs. Ideal for home events, guest reception, nikkah ceremonies, or photo shoots.",
      specs = "5 Seater (3-seater + 2 single), Plush velvet foam, Spotless clean, Free matching cushions included",
      availableFrom = "Today",
      status = "APPROVED"
    )

    val item6 = RentalItemEntity(
      title = "Suzuki Mehran 2019 White (Chilled AC)",
      category = "Cars",
      pricePerDay = 2500,
      pricePerWeek = 15000,
      deposit = 10000,
      ownerId = tariq.id,
      ownerName = tariq.name,
      ownerAvatar = tariq.avatarUrl,
      ownerPhone = tariq.phone,
      ownerVerified = true,
      location = "Kandhkot",
      distance = "4.2km away",
      images = "https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800,https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800",
      description = "Suzuki Mehran VX in pristine condition with working chilled air conditioner. Petrol driven, smooth engine, good tyres. Original smart card with owner.",
      specs = "2019 Model, Chilled AC, Fuel average 16 km/l, Original docs, Self-drive or with driver optional",
      availableFrom = "Today",
      status = "APPROVED"
    )

    val item7 = RentalItemEntity(
      title = "Honda 3KV Heavy Duty Petrol Generator",
      category = "Generators",
      pricePerDay = 1500,
      pricePerWeek = 9000,
      deposit = 8000,
      ownerId = tariq.id,
      ownerName = tariq.name,
      ownerAvatar = tariq.avatarUrl,
      ownerPhone = tariq.phone,
      ownerVerified = true,
      location = "Usta Muhammad",
      distance = "2.0km away",
      images = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=800",
      description = "High capacity Honda copper winding 3KV generator. Easily runs 1 inverter AC or lights + fans during load shedding. Essential for Usta Muhammad wedding functions.",
      specs = "3.0 KVA Output, 100% Copper Winding, Self-Start Key, 15L Fuel Tank (10 hours runtime)",
      availableFrom = "Today",
      status = "APPROVED"
    )

    val item8 = RentalItemEntity(
      title = "Bosch Professional Impact Drill Machine Kit",
      category = "Tools",
      pricePerDay = 300,
      pricePerWeek = 1800,
      deposit = 1000,
      ownerId = salman.id,
      ownerName = salman.name,
      ownerAvatar = salman.avatarUrl,
      ownerPhone = salman.phone,
      ownerVerified = true,
      location = "Kashmore",
      distance = "0.8km away",
      images = "https://images.unsplash.com/photo-1504148455328-c376907d081c?w=800",
      description = "Heavy duty Bosch GSB 550W drill machine with complete masonry and steel bit set. Perfect for hanging frames, AC installation brackets, and DIY woodwork.",
      specs = "550W Motor, Forward/Reverse, Hammer mode for concrete, 13mm Keyed Chuck, 20-piece bit set in toolbox",
      availableFrom = "Today",
      status = "APPROVED"
    )

    // Items pending admin approval
    val item9 = RentalItemEntity(
      title = "Bridal Sharara Designer Dress (Maroon & Zari)",
      category = "Dresses",
      pricePerDay = 4500,
      pricePerWeek = 18000,
      deposit = 12000,
      ownerId = zainab.id,
      ownerName = zainab.name,
      ownerAvatar = zainab.avatarUrl,
      ownerPhone = zainab.phone,
      ownerVerified = true,
      location = "Kashmore",
      distance = "1.0km away",
      images = "https://images.unsplash.com/photo-1566737236500-c8ac43014a67?w=800",
      description = "Original designer maroon bridal dress with hand-crafted dabka and zari embroidery. Worn once for 3 hours only. Dry cleaned and ready for wedding.",
      specs = "Size Medium, Pure Chiffon and Jamawar, Full Dupatta Embroidery, Net weight 4.5kg",
      availableFrom = "This Weekend",
      status = "PENDING"
    )

    val item10 = RentalItemEntity(
      title = "Stage Fairy Lights 100m Warm White Pack",
      category = "Wedding",
      pricePerDay = 600,
      pricePerWeek = 3000,
      deposit = 1500,
      ownerId = salman.id,
      ownerName = salman.name,
      ownerAvatar = salman.avatarUrl,
      ownerPhone = salman.phone,
      ownerVerified = true,
      location = "Kashmore",
      distance = "1.3km away",
      images = "https://images.unsplash.com/photo-1519741497674-611481863552?w=800",
      description = "Warm golden waterproof wedding string lights for garden, rooftop, or stage backdrop setup. Total 5 reels of 20 meters each.",
      specs = "100 Meters Total, IP65 Waterproof, Low Power LED, 8 Flash Modes",
      availableFrom = "Today",
      status = "PENDING"
    )

    val itemId1 = dao.insertItem(item1)
    dao.insertItem(item2)
    dao.insertItem(item3)
    dao.insertItem(item4)
    dao.insertItem(item5)
    dao.insertItem(item6)
    dao.insertItem(item7)
    dao.insertItem(item8)
    dao.insertItem(item9)
    dao.insertItem(item10)

    // 3. Demo Rental Request: Bilal requesting Salman's camera for 2 days
    val demoRequest = RentalRequestEntity(
      itemId = itemId1,
      itemTitle = item1.title,
      itemImage = "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800",
      pricePerDay = item1.pricePerDay,
      ownerId = salman.id,
      ownerName = salman.name,
      ownerPhone = salman.phone,
      renterId = bilal.id,
      renterName = bilal.name,
      renterPhone = bilal.phone,
      startDate = "15 Nov",
      endDate = "16 Nov",
      daysCount = 2,
      rentAmount = 4000,
      serviceFee = 400, // 10%
      depositAmount = 5000,
      totalAmount = 9400,
      message = "Hi Salman, I need camera for my sister wedding on 15-16 Nov, will take care of everything like my own item.",
      status = "ACCEPTED"
    )
    val requestId = dao.insertRequest(demoRequest)

    // 4. Demo Chat messages for this request
    dao.insertChatMessage(
      ChatMessageEntity(
        requestId = requestId,
        senderId = bilal.id,
        senderName = bilal.name,
        text = "Salam Salman bhai! Is the Sony A6400 camera with battery charger available for 15-16 Nov?",
        timestamp = System.currentTimeMillis() - 3600000 * 5,
        isSystem = false
      )
    )
    dao.insertChatMessage(
      ChatMessageEntity(
        requestId = requestId,
        senderId = "system",
        senderName = "Rentify System",
        text = "Salman accepted your rent request • Contact info unlocked",
        timestamp = System.currentTimeMillis() - 3600000 * 4,
        isSystem = true
      )
    )
    dao.insertChatMessage(
      ChatMessageEntity(
        requestId = requestId,
        senderId = salman.id,
        senderName = salman.name,
        text = "Walaikum Assalam Bilal! Yes it is fully charged with 32GB card. We can meet at Kashmore Bus Stand near Main Chowk.",
        timestamp = System.currentTimeMillis() - 3600000 * 3,
        isSystem = false
      )
    )
    dao.insertChatMessage(
      ChatMessageEntity(
        requestId = requestId,
        senderId = bilal.id,
        senderName = bilal.name,
        text = "Great! I have Rs. 4,000 rent and Rs. 5,000 security deposit cash ready. See you at 4 PM.",
        timestamp = System.currentTimeMillis() - 3600000 * 2,
        isSystem = false
      )
    )
    dao.insertChatMessage(
      ChatMessageEntity(
        requestId = requestId,
        senderId = salman.id,
        senderName = salman.name,
        text = "Perfect! Please bring your original CNIC copy for verification. Safe handover.",
        timestamp = System.currentTimeMillis() - 3600000,
        isSystem = false
      )
    )

    // 5. Settings
    dao.saveSetting(AppSettingEntity("commission_rate", "10"))
    dao.saveSetting(AppSettingEntity("admin_email", "admin@rentify.pk"))
    dao.saveSetting(AppSettingEntity("maintenance_mode", "false"))
  }
}
