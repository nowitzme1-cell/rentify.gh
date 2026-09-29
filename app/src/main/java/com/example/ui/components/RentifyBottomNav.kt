package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.navigation.BottomTab
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyDarkGrey
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc

@Composable
fun RentifyBottomNav(
  activeTab: BottomTab,
  onTabSelect: (BottomTab) -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .navigationBarsPadding()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    contentAlignment = Alignment.BottomCenter
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .shadow(
          elevation = 16.dp,
          shape = RoundedCornerShape(999.dp),
          ambientColor = Color(0x22000000),
          spotColor = Color(0x33000000)
        )
        .border(1.dp, Color(0x12000000), RoundedCornerShape(999.dp)),
      shape = RoundedCornerShape(999.dp),
      color = RentifyWhite.copy(alpha = 0.95f)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Tab 1: Home
        NavIconButton(
          icon = Icons.Outlined.Home,
          isActive = activeTab == BottomTab.HOME,
          testTag = "nav_home",
          onClick = { onTabSelect(BottomTab.HOME) }
        )

        // Tab 2: Search
        NavIconButton(
          icon = Icons.Outlined.Search,
          isActive = activeTab == BottomTab.SEARCH,
          testTag = "nav_search",
          onClick = { onTabSelect(BottomTab.SEARCH) }
        )

        // Tab 3: Post (Center elevated + button)
        Box(
          modifier = Modifier
            .offset(y = (-8).dp)
            .size(54.dp)
            .shadow(12.dp, CircleShape, spotColor = Color(0x40000000))
            .border(3.5.dp, RentifyWhite, CircleShape)
            .clip(CircleShape)
            .background(
              Brush.verticalGradient(
                colors = listOf(RentifyBlack, RentifyDarkGrey)
              )
            )
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) { onTabSelect(BottomTab.POST) }
            .testTag("nav_post_center"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Post Item",
            tint = RentifyWhite,
            modifier = Modifier.size(26.dp)
          )
        }

        // Tab 4: Requests
        NavIconButton(
          icon = Icons.Outlined.ChatBubbleOutline,
          isActive = activeTab == BottomTab.REQUESTS,
          testTag = "nav_requests",
          onClick = { onTabSelect(BottomTab.REQUESTS) }
        )

        // Tab 5: Profile
        NavIconButton(
          icon = Icons.Outlined.Person,
          isActive = activeTab == BottomTab.PROFILE,
          testTag = "nav_profile",
          onClick = { onTabSelect(BottomTab.PROFILE) }
        )
      }
    }
  }
}

@Composable
private fun NavIconButton(
  icon: ImageVector,
  isActive: Boolean,
  testTag: String,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .size(44.dp)
      .clip(CircleShape)
      .background(if (isActive) RentifyBlack else Color.Transparent)
      .clickable { onClick() }
      .testTag(testTag),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      imageVector = icon,
      contentDescription = testTag,
      tint = if (isActive) RentifyWhite else RentifyZinc,
      modifier = Modifier.size(22.dp)
    )
  }
}
