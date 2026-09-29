package com.example.ui.navigation

sealed class Screen {
  object Home : Screen()
  data class ItemDetail(val itemId: Long) : Screen()
  object PostItem : Screen()
  object Requests : Screen()
  data class Chat(val requestId: Long) : Screen()
  object Profile : Screen()
  object Verification : Screen()

  // Admin routes
  object AdminLogin : Screen()
  object AdminDashboard : Screen()
  object AdminItems : Screen()
  object AdminUsers : Screen()
  object AdminRequests : Screen()
  object AdminVerifications : Screen()
  object AdminSettings : Screen()
}

enum class BottomTab {
  HOME,
  SEARCH,
  POST,
  REQUESTS,
  PROFILE
}
