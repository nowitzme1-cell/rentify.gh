package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.RentifyDatabase
import com.example.data.entity.ChatMessageEntity
import com.example.data.entity.RentalItemEntity
import com.example.data.entity.RentalRequestEntity
import com.example.data.entity.UserEntity
import com.example.data.repository.RentifyRepository
import com.example.ui.navigation.BottomTab
import com.example.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RentifyViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: RentifyRepository
  val allItems: StateFlow<List<RentalItemEntity>>
  val approvedItems: StateFlow<List<RentalItemEntity>>
  val allUsers: StateFlow<List<UserEntity>>
  val allRequests: StateFlow<List<RentalRequestEntity>>

  // Navigation Stack
  private val _navigationStack = MutableStateFlow<List<Screen>>(listOf(Screen.Home))
  val currentScreen: StateFlow<Screen> = _navigationStack.flatMapLatest { stack ->
    MutableStateFlow(stack.lastOrNull() ?: Screen.Home)
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Screen.Home)

  private val _activeTab = MutableStateFlow(BottomTab.HOME)
  val activeTab: StateFlow<BottomTab> = _activeTab.asStateFlow()

  // Current session user (Salman or Bilal)
  private val _currentUserId = MutableStateFlow("user_bilal") // Bilal the renter by default
  val currentUserId: StateFlow<String> = _currentUserId.asStateFlow()

  val currentUser: StateFlow<UserEntity?>

  // Filters & Search
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedCategory = MutableStateFlow("All")
  val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

  private val _selectedCity = MutableStateFlow("Kashmore")
  val selectedCity: StateFlow<String> = _selectedCity.asStateFlow()

  // Filtered Items for Home Screen
  val filteredItems: StateFlow<List<RentalItemEntity>>

  // Rent Request BottomSheet State
  private val _requestSheetItem = MutableStateFlow<RentalItemEntity?>(null)
  val requestSheetItem: StateFlow<RentalItemEntity?> = _requestSheetItem.asStateFlow()

  private val _requestDays = MutableStateFlow(2)
  val requestDays: StateFlow<Int> = _requestDays.asStateFlow()

  private val _requestStartDate = MutableStateFlow("15 Nov")
  val requestStartDate: StateFlow<String> = _requestStartDate.asStateFlow()

  private val _requestEndDate = MutableStateFlow("16 Nov")
  val requestEndDate: StateFlow<String> = _requestEndDate.asStateFlow()

  private val _requestMessage = MutableStateFlow("Hi Salman, I need camera for my sister wedding on 15-16 Nov, will take care of everything like my own item.")
  val requestMessage: StateFlow<String> = _requestMessage.asStateFlow()

  private val _agreeTerms = MutableStateFlow(true)
  val agreeTerms: StateFlow<Boolean> = _agreeTerms.asStateFlow()

  // Admin state
  private val _isAdminLoggedIn = MutableStateFlow(true) // Ready to access admin panel
  val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

  private val _commissionRate = MutableStateFlow("10")
  val commissionRate: StateFlow<String> = _commissionRate.asStateFlow()

  private val _adminEmail = MutableStateFlow("admin@rentify.pk")
  val adminEmail: StateFlow<String> = _adminEmail.asStateFlow()

  private val _maintenanceMode = MutableStateFlow(false)
  val maintenanceMode: StateFlow<Boolean> = _maintenanceMode.asStateFlow()

  // Toast / feedback message
  private val _snackMessage = MutableStateFlow<String?>(null)
  val snackMessage: StateFlow<String?> = _snackMessage.asStateFlow()

  init {
    val db = RentifyDatabase.getDatabase(application)
    repository = RentifyRepository(db.rentifyDao())
    repository.checkAndSeedInitialData()

    allItems = repository.allItems.stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(5000),
      emptyList()
    )

    approvedItems = repository.approvedItems.stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(5000),
      emptyList()
    )

    allUsers = repository.allUsers.stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(5000),
      emptyList()
    )

    allRequests = repository.allRequests.stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(5000),
      emptyList()
    )

    currentUser = _currentUserId.flatMapLatest { uid ->
      repository.getUserById(uid)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    filteredItems = combine(
      approvedItems,
      _searchQuery,
      _selectedCategory,
      _selectedCity
    ) { items, query, cat, city ->
      items.filter { item ->
        val matchesQuery = query.isBlank() ||
          item.title.contains(query, ignoreCase = true) ||
          item.category.contains(query, ignoreCase = true) ||
          item.description.contains(query, ignoreCase = true) ||
          item.location.contains(query, ignoreCase = true)

        val matchesCategory = cat == "All" || item.category.equals(cat, ignoreCase = true)
        val matchesCity = city == "All Pakistan" || item.location.equals(city, ignoreCase = true)

        matchesQuery && matchesCategory && matchesCity
      }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
  }

  fun navigate(screen: Screen) {
    val current = _navigationStack.value
    _navigationStack.value = current + screen
  }

  fun navigateBack(): Boolean {
    val current = _navigationStack.value
    if (current.size > 1) {
      _navigationStack.value = current.dropLast(1)
      return true
    }
    return false
  }

  fun selectTab(tab: BottomTab) {
    _activeTab.value = tab
    when (tab) {
      BottomTab.HOME -> {
        _navigationStack.value = listOf(Screen.Home)
      }
      BottomTab.SEARCH -> {
        _navigationStack.value = listOf(Screen.Home)
      }
      BottomTab.POST -> {
        navigate(Screen.PostItem)
      }
      BottomTab.REQUESTS -> {
        navigate(Screen.Requests)
      }
      BottomTab.PROFILE -> {
        navigate(Screen.Profile)
      }
    }
  }

  fun switchUser(userId: String) {
    _currentUserId.value = userId
    showToast("Switched user to: ${if (userId == "user_salman") "Salman (Camera Owner)" else "Bilal (Renter)"}")
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun setSelectedCategory(category: String) {
    _selectedCategory.value = category
  }

  fun setSelectedCity(city: String) {
    _selectedCity.value = city
  }

  fun openRentRequest(item: RentalItemEntity) {
    _requestSheetItem.value = item
    _requestDays.value = 2
    _requestStartDate.value = "15 Nov"
    _requestEndDate.value = "16 Nov"
    _agreeTerms.value = true
  }

  fun closeRentRequest() {
    _requestSheetItem.value = null
  }

  fun setRequestDays(days: Int) {
    if (days >= 1) {
      _requestDays.value = days
      _requestEndDate.value = "${14 + days} Nov"
    }
  }

  fun setRequestMessage(msg: String) {
    _requestMessage.value = msg
  }

  fun setAgreeTerms(agree: Boolean) {
    _agreeTerms.value = agree
  }

  fun submitRentRequest(onSuccess: (Long) -> Unit) {
    val item = _requestSheetItem.value ?: return
    val user = currentUser.value ?: return
    val days = _requestDays.value
    val rent = item.pricePerDay * days
    val fee = (rent * 0.10f).toInt()
    val deposit = item.deposit
    val total = rent + fee + deposit

    viewModelScope.launch {
      val request = RentalRequestEntity(
        itemId = item.id,
        itemTitle = item.title,
        itemImage = item.getImageList().firstOrNull() ?: "",
        pricePerDay = item.pricePerDay,
        ownerId = item.ownerId,
        ownerName = item.ownerName,
        ownerPhone = item.ownerPhone,
        renterId = user.id,
        renterName = user.name,
        renterPhone = user.phone,
        startDate = _requestStartDate.value,
        endDate = _requestEndDate.value,
        daysCount = days,
        rentAmount = rent,
        serviceFee = fee,
        depositAmount = deposit,
        totalAmount = total,
        message = _requestMessage.value,
        status = "PENDING"
      )

      val reqId = repository.insertRequest(request)
      _requestSheetItem.value = null
      showToast("Rent request sent to ${item.ownerName}! Track in Requests.")
      onSuccess(reqId)
    }
  }

  fun postNewItem(
    title: String,
    category: String,
    pricePerDay: Int,
    deposit: Int,
    location: String,
    description: String,
    specs: String,
    imageUrl: String,
    onSuccess: () -> Unit
  ) {
    val user = currentUser.value ?: return
    viewModelScope.launch {
      val priceWeek = (pricePerDay * 6) // discount per week
      val item = RentalItemEntity(
        title = title,
        category = category,
        pricePerDay = pricePerDay,
        pricePerWeek = priceWeek,
        deposit = deposit,
        ownerId = user.id,
        ownerName = user.name,
        ownerAvatar = user.avatarUrl,
        ownerPhone = user.phone,
        ownerVerified = user.isVerified,
        location = location.ifBlank { "Kashmore" },
        distance = "0.5km away",
        images = imageUrl.ifBlank { "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800" },
        description = description,
        specs = specs.ifBlank { "Condition 9/10, Verified Owner, Available for rent" },
        availableFrom = "Today",
        status = "PENDING"
      )

      repository.insertItem(item)
      showToast("Submitted! Admin will review in 2 hours. Will be live after approval.")
      onSuccess()
    }
  }

  fun updateRequestStatus(requestId: Long, status: String) {
    viewModelScope.launch {
      repository.updateRequestStatus(requestId, status)
      val text = when (status) {
        "ACCEPTED" -> "Accepted request! Contact info is now shared."
        "REJECTED" -> "Request rejected."
        else -> "Status updated."
      }
      showToast(text)
    }
  }

  fun getChatMessages(requestId: Long) = repository.getChatMessages(requestId)

  fun sendChatMessage(requestId: Long, text: String) {
    if (text.isBlank()) return
    val user = currentUser.value ?: return
    viewModelScope.launch {
      val msg = ChatMessageEntity(
        requestId = requestId,
        senderId = user.id,
        senderName = user.name,
        text = text.trim(),
        isSystem = false
      )
      repository.sendChatMessage(msg)
    }
  }

  // Admin Actions
  fun approveItem(itemId: Long) {
    viewModelScope.launch {
      repository.updateItemStatus(itemId, "APPROVED")
      showToast("Approved! Item is now live in marketplace.")
    }
  }

  fun rejectItem(itemId: Long) {
    viewModelScope.launch {
      repository.updateItemStatus(itemId, "REJECTED")
      showToast("Item marked as Rejected.")
    }
  }

  fun verifyUser(userId: String, verified: Boolean) {
    viewModelScope.launch {
      repository.verifyUser(userId, verified)
      showToast(if (verified) "User granted Verified Blue Tick!" else "Verification revoked.")
    }
  }

  fun toggleUserBlock(userId: String, currentBlocked: Boolean) {
    viewModelScope.launch {
      repository.setUserBlock(userId, !currentBlocked)
      showToast(if (!currentBlocked) "User account blocked." else "User unblocked.")
    }
  }

  fun submitVerificationDocs(frontUrl: String, backUrl: String, selfieUrl: String) {
    val user = currentUser.value ?: return
    viewModelScope.launch {
      val updated = user.copy(
        verificationStatus = "PENDING",
        cnicFront = frontUrl,
        cnicBack = backUrl,
        cnicSelfie = selfieUrl
      )
      repository.updateUser(updated)
      showToast("CNIC submitted! Our team will review and assign Blue Tick within 24 hours.")
      navigateBack()
    }
  }

  fun updateSettings(commission: String, email: String, maintenance: Boolean) {
    _commissionRate.value = commission
    _adminEmail.value = email
    _maintenanceMode.value = maintenance
    showToast("Admin settings saved successfully.")
  }

  fun showToast(message: String) {
    _snackMessage.value = message
  }

  fun clearToast() {
    _snackMessage.value = null
  }
}
