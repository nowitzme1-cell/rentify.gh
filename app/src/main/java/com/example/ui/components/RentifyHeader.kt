package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
import com.example.ui.theme.RentifyPurple
import com.example.ui.theme.RentifySurface
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc

@Composable
fun RentifyHeader(
  currentCity: String,
  onCitySelected: (String) -> Unit,
  onNotificationClick: () -> Unit,
  onAdminClick: () -> Unit,
  onSwitchUserClick: () -> Unit,
  currentUserName: String,
  modifier: Modifier = Modifier
) {
  var showCityMenu by remember { mutableStateOf(false) }
  val cities = listOf("Kashmore", "Usta Muhammad", "Kandhkot", "Sui", "Dera Allah Yar", "Jacobabad", "Shikarpur", "All Pakistan")

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .statusBarsPadding(),
    color = RentifyWhite.copy(alpha = 0.95f),
    shadowElevation = 0.5.dp
  ) {
    Column {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(60.dp)
          .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        // Logo: "rentify." with purple dot
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.testTag("app_logo")
        ) {
          Text(
            text = buildAnnotatedString {
              append("rentify")
              withStyle(SpanStyle(color = RentifyPurple, fontWeight = FontWeight.Black)) {
                append(".")
              }
            },
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 22.sp,
              color = RentifyBlack,
              letterSpacing = (-0.03).sp
            )
          )
        }

        // Action Buttons Row
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Switch Demo User chip (Salman / Bilal)
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(999.dp))
              .background(RentifySurface)
              .clickable { onSwitchUserClick() }
              .padding(horizontal = 8.dp, vertical = 6.dp)
              .testTag("switch_user_chip"),
            contentAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Outlined.SwapHoriz,
                contentDescription = "Switch user",
                tint = RentifyZinc,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = currentUserName.split(" ").firstOrNull() ?: "User",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 11.sp,
                  color = RentifyBlack
                )
              )
            }
          }

          // Location pill button
          Box {
            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(RentifySurface)
                .clickable { showCityMenu = true }
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .testTag("location_pill"),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "Select Location",
                tint = RentifyPurple,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = currentCity,
                style = MaterialTheme.typography.bodySmall.copy(
                  fontWeight = FontWeight.Medium,
                  fontSize = 12.sp,
                  color = RentifyBlack
                )
              )
            }

            DropdownMenu(
              expanded = showCityMenu,
              onDismissRequest = { showCityMenu = false },
              modifier = Modifier.background(RentifyWhite)
            ) {
              cities.forEach { city ->
                DropdownMenuItem(
                  text = {
                    Text(
                      text = city,
                      style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = if (city == currentCity) FontWeight.Bold else FontWeight.Normal,
                        color = if (city == currentCity) RentifyPurple else RentifyBlack
                      )
                    )
                  },
                  onClick = {
                    onCitySelected(city)
                    showCityMenu = false
                  }
                )
              }
            }
          }

          // Admin Dashboard button
          IconButton(
            onClick = onAdminClick,
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(RentifySurface)
              .testTag("admin_button")
          ) {
            Icon(
              imageVector = Icons.Outlined.AdminPanelSettings,
              contentDescription = "Admin Panel",
              tint = RentifyBlack,
              modifier = Modifier.size(18.dp)
            )
          }

          // Notification Bell
          IconButton(
            onClick = onNotificationClick,
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(RentifySurface)
              .testTag("notification_button")
          ) {
            Icon(
              imageVector = Icons.Outlined.Notifications,
              contentDescription = "Notifications",
              tint = RentifyZinc,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }

      // Thin hairline divider
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(1.dp)
          .background(RentifyBorder)
      )
    }
  }
}
