package com.example.ui.admin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.entity.RentalItemEntity
import com.example.data.entity.RentalRequestEntity
import com.example.data.entity.UserEntity
import com.example.ui.theme.RentifyBg
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
import com.example.ui.theme.RentifyBorderDarker
import com.example.ui.theme.RentifyDark
import com.example.ui.theme.RentifyDarkGrey
import com.example.ui.theme.RentifyErrorRed
import com.example.ui.theme.RentifyErrorRedBg
import com.example.ui.theme.RentifyErrorRedText
import com.example.ui.theme.RentifyPurple
import com.example.ui.theme.RentifySuccessGreen
import com.example.ui.theme.RentifySuccessGreenBg
import com.example.ui.theme.RentifySuccessGreenText
import com.example.ui.theme.RentifySurface
import com.example.ui.theme.RentifyVerifiedBlue
import com.example.ui.theme.RentifyWarningYellowBg
import com.example.ui.theme.RentifyWarningYellowText
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc
import java.text.NumberFormat
import java.util.Locale

enum class AdminTab {
  DASHBOARD,
  ITEMS,
  USERS,
  REQUESTS,
  VERIFICATIONS,
  SETTINGS
}

@Composable
fun AdminPanelScreen(
  items: List<RentalItemEntity>,
  users: List<UserEntity>,
  requests: List<RentalRequestEntity>,
  commissionRate: String,
  adminEmail: String,
  maintenanceMode: Boolean,
  onBackToApp: () -> Unit,
  onApproveItem: (Long) -> Unit,
  onRejectItem: (Long) -> Unit,
  onVerifyUser: (String, Boolean) -> Unit,
  onToggleBlockUser: (String, Boolean) -> Unit,
  onSaveSettings: (String, String, Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBackToApp() }

  var activeAdminTab by remember { mutableStateOf(AdminTab.DASHBOARD) }

  val pendingItems = items.filter { it.status == "PENDING" }
  val pendingVerifications = users.filter { it.verificationStatus == "PENDING" }
  val totalCommission = requests.filter { it.status == "ACCEPTED" }.sumOf { it.serviceFee }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RentifyDark)
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("admin_panel_screen")
  ) {
    // Admin Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
          onClick = onBackToApp,
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(RentifyDarkGrey)
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Back to App",
            tint = RentifyWhite,
            modifier = Modifier.size(18.dp)
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
          text = buildAnnotatedString {
            append("rentify. ")
            withStyle(SpanStyle(color = RentifyPurple, fontWeight = FontWeight.Bold)) {
              append("admin")
            }
          },
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp,
            color = RentifyWhite
          )
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(999.dp))
          .background(RentifyBlack)
          .border(1.dp, RentifyPurple, RoundedCornerShape(999.dp))
          .clickable { onBackToApp() }
          .padding(horizontal = 12.dp, vertical = 6.dp)
      ) {
        Text(
          text = "Exit to App",
          style = MaterialTheme.typography.labelSmall.copy(
            color = RentifyWhite,
            fontWeight = FontWeight.SemiBold
          )
        )
      }
    }

    // Admin Navigation Pills
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 16.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      AdminTabPill(
        title = "Dashboard",
        icon = Icons.Outlined.Dashboard,
        badge = null,
        isSelected = activeAdminTab == AdminTab.DASHBOARD,
        onClick = { activeAdminTab = AdminTab.DASHBOARD }
      )
      AdminTabPill(
        title = "Items",
        icon = Icons.Outlined.Category,
        badge = if (pendingItems.isNotEmpty()) "${pendingItems.size}" else null,
        isSelected = activeAdminTab == AdminTab.ITEMS,
        onClick = { activeAdminTab = AdminTab.ITEMS }
      )
      AdminTabPill(
        title = "Users",
        icon = Icons.Outlined.People,
        badge = null,
        isSelected = activeAdminTab == AdminTab.USERS,
        onClick = { activeAdminTab = AdminTab.USERS }
      )
      AdminTabPill(
        title = "Requests",
        icon = Icons.Outlined.Handshake,
        badge = null,
        isSelected = activeAdminTab == AdminTab.REQUESTS,
        onClick = { activeAdminTab = AdminTab.REQUESTS }
      )
      AdminTabPill(
        title = "CNIC Verifications",
        icon = Icons.Outlined.VerifiedUser,
        badge = if (pendingVerifications.isNotEmpty()) "${pendingVerifications.size}" else null,
        isSelected = activeAdminTab == AdminTab.VERIFICATIONS,
        onClick = { activeAdminTab = AdminTab.VERIFICATIONS }
      )
      AdminTabPill(
        title = "Settings",
        icon = Icons.Outlined.Settings,
        badge = null,
        isSelected = activeAdminTab == AdminTab.SETTINGS,
        onClick = { activeAdminTab = AdminTab.SETTINGS }
      )
    }

    Spacer(modifier = Modifier.height(6.dp))

    // Main Content
    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        .background(RentifyBg)
    ) {
      when (activeAdminTab) {
        AdminTab.DASHBOARD -> AdminDashboardView(
          usersCount = users.size,
          itemsCount = items.size,
          pendingItemsCount = pendingItems.size,
          totalCommission = totalCommission,
          pendingItems = pendingItems,
          users = users,
          onApproveItem = onApproveItem,
          onRejectItem = onRejectItem
        )
        AdminTab.ITEMS -> AdminItemsView(
          items = items,
          onApproveItem = onApproveItem,
          onRejectItem = onRejectItem
        )
        AdminTab.USERS -> AdminUsersView(
          users = users,
          onVerifyUser = onVerifyUser,
          onToggleBlockUser = onToggleBlockUser
        )
        AdminTab.REQUESTS -> AdminRequestsView(requests = requests)
        AdminTab.VERIFICATIONS -> AdminVerificationsView(
          users = users,
          onVerifyUser = onVerifyUser
        )
        AdminTab.SETTINGS -> AdminSettingsView(
          commissionRate = commissionRate,
          adminEmail = adminEmail,
          maintenanceMode = maintenanceMode,
          onSaveSettings = onSaveSettings
        )
      }
    }
  }
}

@Composable
private fun AdminTabPill(
  title: String,
  icon: ImageVector,
  badge: String?,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(999.dp))
      .background(if (isSelected) RentifyWhite else RentifyDarkGrey)
      .clickable { onClick() }
      .padding(horizontal = 14.dp, vertical = 8.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isSelected) RentifyBlack else RentifyZinc,
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.bodySmall.copy(
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
          color = if (isSelected) RentifyBlack else RentifyWhite
        )
      )
      if (badge != null) {
        Spacer(modifier = Modifier.width(6.dp))
        Box(
          modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(RentifyErrorRed),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = badge,
            style = MaterialTheme.typography.labelSmall.copy(
              color = RentifyWhite,
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp
            )
          )
        }
      }
    }
  }
}

// 1. Dashboard View
@Composable
private fun AdminDashboardView(
  usersCount: Int,
  itemsCount: Int,
  pendingItemsCount: Int,
  totalCommission: Int,
  pendingItems: List<RentalItemEntity>,
  users: List<UserEntity>,
  onApproveItem: (Long) -> Unit,
  onRejectItem: (Long) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    Text(
      text = "Marketplace Overview",
      style = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = RentifyBlack
      )
    )

    Spacer(modifier = Modifier.height(14.dp))

    // 4 Stats Cards
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      AdminMetricCard(
        title = "Total Users",
        value = "$usersCount",
        subtext = "Active in Kashmore",
        icon = Icons.Outlined.People,
        modifier = Modifier.weight(1f)
      )
      AdminMetricCard(
        title = "Items Listed",
        value = "$itemsCount",
        subtext = "$pendingItemsCount pending approval",
        icon = Icons.Outlined.Category,
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      AdminMetricCard(
        title = "Pending Queue",
        value = "$pendingItemsCount",
        subtext = "Needs Admin review",
        icon = Icons.Outlined.VerifiedUser,
        highlight = pendingItemsCount > 0,
        modifier = Modifier.weight(1f)
      )
      AdminMetricCard(
        title = "Commission (10%)",
        value = "Rs. ${NumberFormat.getNumberInstance(Locale.US).format(totalCommission)}",
        subtext = "Total platform profit",
        icon = Icons.Outlined.Savings,
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Pending Items Section
    Text(
      text = "Items Awaiting Approval (${pendingItems.size})",
      style = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = RentifyBlack
      )
    )

    Spacer(modifier = Modifier.height(10.dp))

    if (pendingItems.isEmpty()) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = RentifyWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, RentifyBorder)
      ) {
        Box(
          modifier = Modifier.fillMaxWidth().padding(24.dp),
          contentAlignment = Alignment.Center
        ) {
          Text("All items approved! Queue is clear.", color = RentifyZinc)
        }
      }
    } else {
      pendingItems.forEach { item ->
        AdminItemApprovalCard(
          item = item,
          onApprove = { onApproveItem(item.id) },
          onReject = { onRejectItem(item.id) }
        )
        Spacer(modifier = Modifier.height(10.dp))
      }
    }
  }
}

@Composable
private fun AdminMetricCard(
  title: String,
  value: String,
  subtext: String,
  icon: ImageVector,
  highlight: Boolean = false,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.border(1.dp, RentifyBorder, RoundedCornerShape(16.dp)),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = RentifyWhite)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.bodySmall.copy(
            color = RentifyZinc,
            fontWeight = FontWeight.Medium
          )
        )
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = if (highlight) RentifyErrorRed else RentifyPurple,
          modifier = Modifier.size(18.dp)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = value,
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp,
          color = RentifyBlack
        )
      )

      Spacer(modifier = Modifier.height(2.dp))

      Text(
        text = subtext,
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 11.sp,
          color = if (highlight) RentifyErrorRedText else RentifyZinc
        )
      )
    }
  }
}

// 2. Items Management View
@Composable
private fun AdminItemsView(
  items: List<RentalItemEntity>,
  onApproveItem: (Long) -> Unit,
  onRejectItem: (Long) -> Unit
) {
  var filter by remember { mutableStateOf("PENDING") } // "ALL", "PENDING", "APPROVED", "REJECTED"

  val filtered = when (filter) {
    "PENDING" -> items.filter { it.status == "PENDING" }
    "APPROVED" -> items.filter { it.status == "APPROVED" }
    "REJECTED" -> items.filter { it.status == "REJECTED" }
    else -> items
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    // Filter tabs
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      listOf("PENDING", "APPROVED", "REJECTED", "ALL").forEach { status ->
        val count = when (status) {
          "PENDING" -> items.count { it.status == "PENDING" }
          "APPROVED" -> items.count { it.status == "APPROVED" }
          "REJECTED" -> items.count { it.status == "REJECTED" }
          else -> items.size
        }
        val isSelected = filter == status
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(if (isSelected) RentifyBlack else RentifySurface)
            .clickable { filter = status }
            .padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
          Text(
            text = "$status ($count)",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = if (isSelected) RentifyWhite else RentifyBlack
            )
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    LazyColumn(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(filtered, key = { it.id }) { item ->
        AdminItemApprovalCard(
          item = item,
          onApprove = { onApproveItem(item.id) },
          onReject = { onRejectItem(item.id) }
        )
      }
    }
  }
}

@Composable
private fun AdminItemApprovalCard(
  item: RentalItemEntity,
  onApprove: () -> Unit,
  onReject: () -> Unit
) {
  val context = LocalContext.current

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, RentifyBorder, RoundedCornerShape(16.dp)),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = RentifyWhite)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        AsyncImage(
          model = ImageRequest.Builder(context)
            .data(item.getImageList().firstOrNull() ?: "")
            .crossfade(true)
            .build(),
          contentDescription = item.title,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(RentifySurface)
        )

        Column(modifier = Modifier.weight(1f)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = item.title,
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = RentifyBlack
              ),
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier.weight(1f)
            )

            // Status Badge
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(
                  when (item.status) {
                    "APPROVED" -> RentifySuccessGreenBg
                    "REJECTED" -> RentifyErrorRedBg
                    else -> RentifyWarningYellowBg
                  }
                )
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = item.status,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = when (item.status) {
                    "APPROVED" -> RentifySuccessGreenText
                    "REJECTED" -> RentifyErrorRedText
                    else -> RentifyWarningYellowText
                  }
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(2.dp))

          Text(
            text = "Owner: ${item.ownerName} (${item.ownerPhone}) • 📍 ${item.location}",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 12.sp,
              color = RentifyZinc
            )
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Rs. ${item.pricePerDay}/day • Deposit: Rs. ${item.deposit}",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = RentifyBlack
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Action row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (item.status == "PENDING") {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(999.dp))
              .border(1.dp, RentifyBorderDarker, RoundedCornerShape(999.dp))
              .clickable { onReject() }
              .padding(horizontal = 14.dp, vertical = 6.dp)
          ) {
            Text(
              text = "Reject",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                color = RentifyZinc
              )
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(999.dp))
              .background(RentifyBlack)
              .clickable { onApprove() }
              .padding(horizontal = 16.dp, vertical = 6.dp)
          ) {
            Text(
              text = "Approve Item",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = RentifyWhite
              )
            )
          }
        } else if (item.status == "APPROVED") {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(999.dp))
              .border(1.dp, RentifyBorder, RoundedCornerShape(999.dp))
              .clickable { onReject() }
              .padding(horizontal = 12.dp, vertical = 5.dp)
          ) {
            Text("Revoke", style = MaterialTheme.typography.labelSmall.copy(color = RentifyZinc))
          }
        } else {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(999.dp))
              .background(RentifyBlack)
              .clickable { onApprove() }
              .padding(horizontal = 12.dp, vertical = 5.dp)
          ) {
            Text("Re-Approve", style = MaterialTheme.typography.labelSmall.copy(color = RentifyWhite))
          }
        }
      }
    }
  }
}

// 3. Users View
@Composable
private fun AdminUsersView(
  users: List<UserEntity>,
  onVerifyUser: (String, Boolean) -> Unit,
  onToggleBlockUser: (String, Boolean) -> Unit
) {
  val context = LocalContext.current

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    items(users, key = { it.id }) { user ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, RentifyBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = RentifyWhite)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            AsyncImage(
              model = ImageRequest.Builder(context).data(user.avatarUrl).build(),
              contentDescription = user.name,
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .border(1.dp, RentifyBorder, CircleShape)
            )

            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = user.name,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                  )
                )
                if (user.isVerified) {
                  Spacer(modifier = Modifier.width(4.dp))
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Verified",
                    tint = RentifyVerifiedBlue,
                    modifier = Modifier.size(14.dp)
                  )
                }
              }

              Text(
                text = "${user.phone} • 📍 ${user.city}",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 12.sp,
                  color = RentifyZinc
                )
              )

              Text(
                text = "${user.itemsCount} items • Rs. ${user.totalEarned} earned",
                style = MaterialTheme.typography.labelSmall.copy(color = RentifyZinc)
              )
            }

            // Verify Action
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(if (user.isVerified) RentifySurface else RentifyVerifiedBlue)
                .clickable { onVerifyUser(user.id, !user.isVerified) }
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text(
                text = if (user.isVerified) "Revoke Tick" else "Verify CNIC",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (user.isVerified) RentifyZinc else RentifyWhite
                )
              )
            }
          }
        }
      }
    }
  }
}

// 4. Requests View (Commission tracking)
@Composable
private fun AdminRequestsView(requests: List<RentalRequestEntity>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    items(requests, key = { it.id }) { req ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, RentifyBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = RentifyWhite)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Request #${req.id} • ${req.itemTitle}",
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
              ),
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier.weight(1f)
            )

            Text(
              text = req.status,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (req.status == "ACCEPTED") RentifySuccessGreenText else RentifyWarningYellowText
              )
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Renter: ${req.renterName} -> Owner: ${req.ownerName}",
            style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
          )

          Spacer(modifier = Modifier.height(4.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "${req.daysCount} days: Rs. ${req.rentAmount} rent",
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
            )
            Text(
              text = "Rentify 10%: Rs. ${req.serviceFee}",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = RentifyPurple
              )
            )
          }
        }
      }
    }
  }
}

// 5. CNIC Verifications Inspector View
@Composable
private fun AdminVerificationsView(
  users: List<UserEntity>,
  onVerifyUser: (String, Boolean) -> Unit
) {
  val context = LocalContext.current
  val listWithDocs = users.filter { it.cnicFront != null || it.verificationStatus == "PENDING" }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    items(listWithDocs, key = { it.id }) { u ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, RentifyBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = RentifyWhite)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "${u.name} (${u.city})",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "Phone: ${u.phone} • Status: ${u.verificationStatus}",
                style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(if (u.isVerified) RentifySurface else RentifyBlack)
                .clickable { onVerifyUser(u.id, !u.isVerified) }
                .padding(horizontal = 14.dp, vertical = 7.dp)
            ) {
              Text(
                text = if (u.isVerified) "Verified ✓" else "Approve Blue Tick",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (u.isVerified) RentifyZinc else RentifyWhite
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // 3 CNIC Image thumbnails
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text("Front", style = MaterialTheme.typography.labelSmall.copy(color = RentifyZinc))
              AsyncImage(
                model = ImageRequest.Builder(context).data(u.cnicFront ?: "").build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                  .fillMaxWidth()
                  .height(70.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(RentifySurface)
              )
            }
            Column(modifier = Modifier.weight(1f)) {
              Text("Back", style = MaterialTheme.typography.labelSmall.copy(color = RentifyZinc))
              AsyncImage(
                model = ImageRequest.Builder(context).data(u.cnicBack ?: "").build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                  .fillMaxWidth()
                  .height(70.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(RentifySurface)
              )
            }
            Column(modifier = Modifier.weight(1f)) {
              Text("Selfie", style = MaterialTheme.typography.labelSmall.copy(color = RentifyZinc))
              AsyncImage(
                model = ImageRequest.Builder(context).data(u.cnicSelfie ?: "").build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                  .fillMaxWidth()
                  .height(70.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(RentifySurface)
              )
            }
          }
        }
      }
    }
  }
}

// 6. Settings View
@Composable
private fun AdminSettingsView(
  commissionRate: String,
  adminEmail: String,
  maintenanceMode: Boolean,
  onSaveSettings: (String, String, Boolean) -> Unit
) {
  var rate by remember { mutableStateOf(commissionRate) }
  var email by remember { mutableStateOf(adminEmail) }
  var maintenance by remember { mutableStateOf(maintenanceMode) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, RentifyBorder, RoundedCornerShape(20.dp)),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = RentifyWhite)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        Text(
          text = "Platform Settings",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "Platform Commission Percentage (%)",
          style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = rate,
          onValueChange = { rate = it },
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = RentifySurface,
            unfocusedContainerColor = RentifySurface,
            focusedBorderColor = RentifyBlack,
            unfocusedBorderColor = Color.Transparent
          ),
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "Admin Email for Alerts",
          style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = RentifySurface,
            unfocusedContainerColor = RentifySurface,
            focusedBorderColor = RentifyBlack,
            unfocusedBorderColor = Color.Transparent
          ),
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Maintenance Mode",
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
              text = "Temporarily suspend booking requests",
              style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
            )
          }

          Switch(
            checked = maintenance,
            onCheckedChange = { maintenance = it },
            colors = SwitchDefaults.colors(checkedThumbColor = RentifyBlack)
          )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(RentifyBlack)
            .clickable { onSaveSettings(rate, email, maintenance) },
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Save Settings",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              color = RentifyWhite
            )
          )
        }
      }
    }
  }
}
