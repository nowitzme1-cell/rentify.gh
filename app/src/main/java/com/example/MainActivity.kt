package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.admin.AdminPanelScreen
import com.example.ui.components.RentifyBottomNav
import com.example.ui.components.RentifyHeader
import com.example.ui.navigation.BottomTab
import com.example.ui.navigation.Screen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ItemDetailScreen
import com.example.ui.screens.PostItemScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RentRequestSheet
import com.example.ui.screens.RequestsScreen
import com.example.ui.screens.VerificationScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RentifyBg
import com.example.ui.viewmodel.RentifyViewModel

class MainActivity : ComponentActivity() {

  private val viewModel: RentifyViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        RentifyApp(viewModel = viewModel)
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentifyApp(viewModel: RentifyViewModel) {
  val context = LocalContext.current
  val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
  val activeTab by viewModel.activeTab.collectAsStateWithLifecycle()
  val currentUserId by viewModel.currentUserId.collectAsStateWithLifecycle()
  val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

  val filteredItems by viewModel.filteredItems.collectAsStateWithLifecycle()
  val allItems by viewModel.allItems.collectAsStateWithLifecycle()
  val allUsers by viewModel.allUsers.collectAsStateWithLifecycle()
  val allRequests by viewModel.allRequests.collectAsStateWithLifecycle()

  val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
  val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
  val selectedCity by viewModel.selectedCity.collectAsStateWithLifecycle()

  val requestSheetItem by viewModel.requestSheetItem.collectAsStateWithLifecycle()
  val requestDays by viewModel.requestDays.collectAsStateWithLifecycle()
  val requestStartDate by viewModel.requestStartDate.collectAsStateWithLifecycle()
  val requestEndDate by viewModel.requestEndDate.collectAsStateWithLifecycle()
  val requestMessage by viewModel.requestMessage.collectAsStateWithLifecycle()
  val agreeTerms by viewModel.agreeTerms.collectAsStateWithLifecycle()

  val commissionRate by viewModel.commissionRate.collectAsStateWithLifecycle()
  val adminEmail by viewModel.adminEmail.collectAsStateWithLifecycle()
  val maintenanceMode by viewModel.maintenanceMode.collectAsStateWithLifecycle()

  val snackMessage by viewModel.snackMessage.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(snackMessage) {
    snackMessage?.let {
      Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
      viewModel.clearToast()
    }
  }

  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  val showBottomNav = currentScreen is Screen.Home ||
    currentScreen is Screen.Requests ||
    currentScreen is Screen.Profile

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = RentifyBg,
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    bottomBar = {
      if (showBottomNav) {
        RentifyBottomNav(
          activeTab = activeTab,
          onTabSelect = { tab ->
            viewModel.selectTab(tab)
          }
        )
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(RentifyBg)
    ) {
      when (val screen = currentScreen) {
        is Screen.Home -> {
          HomeScreenWithHeader(
            viewModel = viewModel,
            items = filteredItems,
            searchQuery = searchQuery,
            selectedCategory = selectedCategory,
            selectedCity = selectedCity,
            currentUserName = currentUser?.name ?: "User"
          )
        }

        is Screen.ItemDetail -> {
          val item = allItems.find { it.id == screen.itemId }
          if (item != null) {
            ItemDetailScreen(
              item = item,
              onBackClick = { viewModel.navigateBack() },
              onRequestClick = { viewModel.openRentRequest(item) },
              onDirectChatClick = {
                // Find or start chat with owner
                val existingReq = allRequests.find { it.itemId == item.id && it.renterId == currentUserId }
                if (existingReq != null) {
                  viewModel.navigate(Screen.Chat(existingReq.id))
                } else {
                  viewModel.openRentRequest(item)
                }
              },
              onOwnerProfileClick = { ownerId ->
                viewModel.switchUser(ownerId)
                viewModel.navigate(Screen.Profile)
              }
            )
          } else {
            viewModel.navigateBack()
          }
        }

        is Screen.PostItem -> {
          PostItemScreen(
            onBackClick = { viewModel.navigateBack() },
            onSubmitPost = { title, cat, price, dep, loc, desc, specs, img ->
              viewModel.postNewItem(
                title = title,
                category = cat,
                pricePerDay = price,
                deposit = dep,
                location = loc,
                description = desc,
                specs = specs,
                imageUrl = img,
                onSuccess = {
                  viewModel.navigate(Screen.Home)
                }
              )
            }
          )
        }

        is Screen.Requests -> {
          RequestsScreen(
            currentUserId = currentUserId,
            allRequests = allRequests,
            onBackClick = { viewModel.navigateBack() },
            onOpenChat = { reqId ->
              viewModel.navigate(Screen.Chat(reqId))
            },
            onAcceptRequest = { reqId ->
              viewModel.updateRequestStatus(reqId, "ACCEPTED")
            },
            onRejectRequest = { reqId ->
              viewModel.updateRequestStatus(reqId, "REJECTED")
            }
          )
        }

        is Screen.Chat -> {
          val req = allRequests.find { it.id == screen.requestId }
          if (req != null) {
            val messages by viewModel.getChatMessages(req.id).collectAsStateWithLifecycle(emptyList())
            ChatScreen(
              request = req,
              currentUserId = currentUserId,
              messages = messages,
              onBackClick = { viewModel.navigateBack() },
              onSendMessage = { text ->
                viewModel.sendChatMessage(req.id, text)
              }
            )
          } else {
            viewModel.navigateBack()
          }
        }

        is Screen.Profile -> {
          ProfileScreen(
            user = currentUser,
            onBackClick = { viewModel.navigateBack() },
            onNavigateVerification = { viewModel.navigate(Screen.Verification) },
            onNavigateRequests = { viewModel.navigate(Screen.Requests) },
            onNavigateAdmin = { viewModel.navigate(Screen.AdminDashboard) },
            onSwitchUser = { uid -> viewModel.switchUser(uid) }
          )
        }

        is Screen.Verification -> {
          VerificationScreen(
            onBackClick = { viewModel.navigateBack() },
            onSubmitVerification = { front, back, selfie ->
              viewModel.submitVerificationDocs(front, back, selfie)
            }
          )
        }

        is Screen.AdminDashboard,
        is Screen.AdminItems,
        is Screen.AdminUsers,
        is Screen.AdminRequests,
        is Screen.AdminVerifications,
        is Screen.AdminSettings,
        is Screen.AdminLogin -> {
          AdminPanelScreen(
            items = allItems,
            users = allUsers,
            requests = allRequests,
            commissionRate = commissionRate,
            adminEmail = adminEmail,
            maintenanceMode = maintenanceMode,
            onBackToApp = { viewModel.navigate(Screen.Home) },
            onApproveItem = { id -> viewModel.approveItem(id) },
            onRejectItem = { id -> viewModel.rejectItem(id) },
            onVerifyUser = { uid, verified -> viewModel.verifyUser(uid, verified) },
            onToggleBlockUser = { uid, blocked -> viewModel.toggleUserBlock(uid, blocked) },
            onSaveSettings = { rate, email, maint -> viewModel.updateSettings(rate, email, maint) }
          )
        }
      }

      // Rent Request Bottom Sheet
      if (requestSheetItem != null) {
        RentRequestSheet(
          item = requestSheetItem!!,
          sheetState = sheetState,
          days = requestDays,
          startDate = requestStartDate,
          endDate = requestEndDate,
          message = requestMessage,
          agreeTerms = agreeTerms,
          onDaysChange = { d -> viewModel.setRequestDays(d) },
          onMessageChange = { m -> viewModel.setRequestMessage(m) },
          onAgreeChange = { a -> viewModel.setAgreeTerms(a) },
          onDismiss = { viewModel.closeRentRequest() },
          onConfirm = {
            viewModel.submitRentRequest(
              onSuccess = { reqId ->
                viewModel.navigate(Screen.Requests)
              }
            )
          }
        )
      }
    }
  }
}

@Composable
private fun HomeScreenWithHeader(
  viewModel: RentifyViewModel,
  items: List<com.example.data.entity.RentalItemEntity>,
  searchQuery: String,
  selectedCategory: String,
  selectedCity: String,
  currentUserName: String
) {
  androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxSize()) {
    RentifyHeader(
      currentCity = selectedCity,
      onCitySelected = { city -> viewModel.setSelectedCity(city) },
      onNotificationClick = {
        viewModel.showToast("No new notifications in $selectedCity")
      },
      onAdminClick = {
        viewModel.navigate(Screen.AdminDashboard)
      },
      onSwitchUserClick = {
        val next = if (viewModel.currentUserId.value == "user_salman") "user_bilal" else "user_salman"
        viewModel.switchUser(next)
      },
      currentUserName = currentUserName
    )

    HomeScreen(
      items = items,
      searchQuery = searchQuery,
      onSearchChange = { q -> viewModel.setSearchQuery(q) },
      selectedCategory = selectedCategory,
      onCategorySelect = { c -> viewModel.setSelectedCategory(c) },
      currentCity = selectedCity,
      onItemClick = { itemId ->
        viewModel.navigate(Screen.ItemDetail(itemId))
      },
      modifier = Modifier.weight(1f)
    )
  }
}
