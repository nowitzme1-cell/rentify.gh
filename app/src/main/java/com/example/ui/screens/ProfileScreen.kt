package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.entity.UserEntity
import com.example.ui.theme.RentifyBg
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
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

@Composable
fun ProfileScreen(
  user: UserEntity?,
  onBackClick: () -> Unit,
  onNavigateVerification: () -> Unit,
  onNavigateRequests: () -> Unit,
  onNavigateAdmin: () -> Unit,
  onSwitchUser: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }
  val context = LocalContext.current
  val safeUser = user ?: UserEntity(
    id = "user_guest",
    name = "Guest User",
    phone = "0300-0000000",
    city = "Kashmore",
    avatarUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200"
  )

  val formattedEarned = NumberFormat.getNumberInstance(Locale.US).format(safeUser.totalEarned)

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RentifyBg)
      .statusBarsPadding()
      .verticalScroll(rememberScrollState())
      .padding(bottom = 100.dp)
      .testTag("profile_screen")
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      IconButton(
        onClick = onBackClick,
        modifier = Modifier
          .size(38.dp)
          .clip(CircleShape)
          .background(RentifySurface)
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
          contentDescription = "Back",
          tint = RentifyBlack,
          modifier = Modifier.size(18.dp)
        )
      }

      Text(
        text = "Profile",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 17.sp,
          color = RentifyBlack
        )
      )

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(999.dp))
          .background(RentifySurface)
          .clickable {
            val nextId = if (safeUser.id == "user_salman") "user_bilal" else "user_salman"
            onSwitchUser(nextId)
          }
          .padding(horizontal = 10.dp, vertical = 6.dp)
          .testTag("switch_user_btn"),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Outlined.SwapHoriz,
            contentDescription = null,
            tint = RentifyPurple,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (safeUser.id == "user_salman") "Switch to Bilal" else "Switch to Salman",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.SemiBold,
              color = RentifyBlack
            )
          )
        }
      }
    }

    // Profile Header Info
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(modifier = Modifier.size(80.dp)) {
        AsyncImage(
          model = ImageRequest.Builder(context)
            .data(safeUser.avatarUrl)
            .crossfade(true)
            .build(),
          contentDescription = safeUser.name,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .border(2.dp, RentifyBorder, CircleShape)
        )

        if (safeUser.isVerified) {
          Box(
            modifier = Modifier
              .size(22.dp)
              .clip(CircleShape)
              .background(RentifyVerifiedBlue)
              .border(2.dp, RentifyWhite, CircleShape)
              .align(Alignment.BottomEnd),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = "Verified",
              tint = RentifyWhite,
              modifier = Modifier.size(14.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = safeUser.name,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp,
          color = RentifyBlack
        )
      )

      Spacer(modifier = Modifier.height(2.dp))

      Text(
        text = "${safeUser.phone} • 📍 ${safeUser.city} City",
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 13.sp,
          color = RentifyZinc
        )
      )
    }

    // Verification Status Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
      if (safeUser.isVerified) {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = RentifySuccessGreenBg),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBBF7D0))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(RentifyVerifiedBlue),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = RentifyWhite,
                modifier = Modifier.size(16.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Identity Verified • Blue Tick Active",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = RentifySuccessGreenText
                )
              )
              Text(
                text = "CNIC verified. Eligible for highest priority in Kashmore rentals.",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 11.5.sp,
                  color = RentifySuccessGreenText.copy(alpha = 0.8f)
                )
              )
            }
          }
        }
      } else {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = RentifyWarningYellowBg),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Get Blue Tick Verified",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = RentifyWarningYellowText
                )
              )
              Text(
                text = "Upload CNIC to unlock 3x more bookings & build trust.",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 11.5.sp,
                  color = RentifyWarningYellowText.copy(alpha = 0.85f)
                )
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(RentifyBlack)
                .clickable { onNavigateVerification() }
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .testTag("verify_now_button"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "Verify",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = RentifyWhite
                )
              )
            }
          }
        }
      }
    }

    // 3 Stats Cards
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      ProfileStatCard(
        number = "${safeUser.itemsCount}",
        label = "Listed Items",
        modifier = Modifier.weight(1f)
      )
      ProfileStatCard(
        number = "Rs. ${safeUser.totalEarned / 1000}k",
        label = "Earned",
        modifier = Modifier.weight(1f)
      )
      ProfileStatCard(
        number = "${safeUser.rating} ★",
        label = "${safeUser.ratingCount} Reviews",
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Menu List
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .border(1.dp, RentifyBorder, RoundedCornerShape(20.dp)),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = RentifyWhite)
    ) {
      Column {
        ProfileMenuItem(
          icon = Icons.Outlined.FormatListBulleted,
          title = "My Rental Listings (${safeUser.itemsCount})",
          onClick = { }
        )
        HorizontalDivider(color = RentifyBorder)
        ProfileMenuItem(
          icon = Icons.Outlined.TaskAlt,
          title = "My Rental Requests",
          onClick = onNavigateRequests
        )
        HorizontalDivider(color = RentifyBorder)
        ProfileMenuItem(
          icon = Icons.Outlined.MonetizationOn,
          title = "Earnings (Rs. $formattedEarned)",
          onClick = { }
        )
        HorizontalDivider(color = RentifyBorder)
        ProfileMenuItem(
          icon = Icons.Outlined.Security,
          title = "CNIC Verification",
          onClick = onNavigateVerification
        )
        HorizontalDivider(color = RentifyBorder)
        ProfileMenuItem(
          icon = Icons.Outlined.AdminPanelSettings,
          title = "Admin Portal (Management)",
          onClick = onNavigateAdmin
        )
        HorizontalDivider(color = RentifyBorder)
        ProfileMenuItem(
          icon = Icons.Outlined.Settings,
          title = "Settings & City Preferences",
          onClick = { }
        )
        HorizontalDivider(color = RentifyBorder)
        ProfileMenuItem(
          icon = Icons.AutoMirrored.Outlined.HelpOutline,
          title = "Help & Kashmore Support",
          onClick = { }
        )
        HorizontalDivider(color = RentifyBorder)
        ProfileMenuItem(
          icon = Icons.AutoMirrored.Outlined.Logout,
          title = "Switch Demo Account",
          textColor = RentifyPurple,
          onClick = {
            val nextId = if (safeUser.id == "user_salman") "user_bilal" else "user_salman"
            onSwitchUser(nextId)
          }
        )
      }
    }
  }
}

@Composable
private fun ProfileStatCard(
  number: String,
  label: String,
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
        .padding(vertical = 14.dp, horizontal = 8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = number,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 17.sp,
          color = RentifyBlack
        )
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 11.sp,
          color = RentifyZinc
        )
      )
    }
  }
}

@Composable
private fun ProfileMenuItem(
  icon: ImageVector,
  title: String,
  textColor: Color = RentifyBlack,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(horizontal = 16.dp, vertical = 14.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (textColor == RentifyBlack) RentifyZinc else textColor,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.width(14.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = FontWeight.Medium,
          fontSize = 14.sp,
          color = textColor
        )
      )
    }

    Icon(
      imageVector = Icons.Outlined.ChevronRight,
      contentDescription = null,
      tint = RentifyZinc,
      modifier = Modifier.size(18.dp)
    )
  }
}
