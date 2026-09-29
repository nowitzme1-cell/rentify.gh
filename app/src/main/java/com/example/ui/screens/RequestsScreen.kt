package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.entity.RentalRequestEntity
import com.example.ui.theme.RentifyBg
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
import com.example.ui.theme.RentifyBorderDarker
import com.example.ui.theme.RentifyErrorRedBg
import com.example.ui.theme.RentifyErrorRedText
import com.example.ui.theme.RentifySuccessGreen
import com.example.ui.theme.RentifySuccessGreenBg
import com.example.ui.theme.RentifySuccessGreenText
import com.example.ui.theme.RentifySurface
import com.example.ui.theme.RentifyWarningYellowBg
import com.example.ui.theme.RentifyWarningYellowText
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc
import java.text.NumberFormat
import java.util.Locale

@Composable
fun RequestsScreen(
  currentUserId: String,
  allRequests: List<RentalRequestEntity>,
  onBackClick: () -> Unit,
  onOpenChat: (Long) -> Unit,
  onAcceptRequest: (Long) -> Unit,
  onRejectRequest: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  var selectedTab by remember { mutableStateOf("Sent") } // "Sent" or "Received"
  val context = LocalContext.current

  val sentRequests = allRequests.filter { it.renterId == currentUserId }
  val receivedRequests = allRequests.filter { it.ownerId == currentUserId }

  val displayedRequests = if (selectedTab == "Sent") sentRequests else receivedRequests

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RentifyBg)
      .statusBarsPadding()
      .testTag("requests_screen")
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically
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
      Spacer(modifier = Modifier.width(12.dp))
      Text(
        text = "Rental Requests",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 22.sp,
          color = RentifyBlack
        )
      )
    }

    // Tabs container pill: "Sent" | "Received"
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp)
          .clip(RoundedCornerShape(999.dp))
          .background(RentifySurface)
          .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        // Sent Tab
        Box(
          modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .clip(RoundedCornerShape(999.dp))
            .background(if (selectedTab == "Sent") RentifyWhite else Color.Transparent)
            .then(
              if (selectedTab == "Sent") Modifier.shadow(2.dp, RoundedCornerShape(999.dp)) else Modifier
            )
            .clickable { selectedTab = "Sent" }
            .testTag("tab_sent_requests"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Sent (${sentRequests.size})",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = if (selectedTab == "Sent") FontWeight.Bold else FontWeight.Medium,
              color = if (selectedTab == "Sent") RentifyBlack else RentifyZinc
            )
          )
        }

        // Received Tab
        Box(
          modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .clip(RoundedCornerShape(999.dp))
            .background(if (selectedTab == "Received") RentifyWhite else Color.Transparent)
            .then(
              if (selectedTab == "Received") Modifier.shadow(2.dp, RoundedCornerShape(999.dp)) else Modifier
            )
            .clickable { selectedTab = "Received" }
            .testTag("tab_received_requests"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Received (${receivedRequests.size})",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = if (selectedTab == "Received") FontWeight.Bold else FontWeight.Medium,
              color = if (selectedTab == "Received") RentifyBlack else RentifyZinc
            )
          )
        }
      }
    }

    // Requests List
    if (displayedRequests.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            imageVector = Icons.Outlined.Inbox,
            contentDescription = null,
            tint = RentifyZinc,
            modifier = Modifier.size(52.dp)
          )
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = if (selectedTab == "Sent") "No rent requests sent yet" else "No incoming requests right now",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = if (selectedTab == "Sent")
              "Browse cameras, ACs, tools on Home page and send a request."
            else
              "When other users request your items, they will show here.",
            style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
          )
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .testTag("requests_list"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(displayedRequests, key = { it.id }) { req ->
          RequestCard(
            request = req,
            isReceivedTab = selectedTab == "Received",
            onOpenChat = { onOpenChat(req.id) },
            onAccept = { onAcceptRequest(req.id) },
            onReject = { onRejectRequest(req.id) }
          )
        }
      }
    }
  }
}

@Composable
private fun RequestCard(
  request: RentalRequestEntity,
  isReceivedTab: Boolean,
  onOpenChat: () -> Unit,
  onAccept: () -> Unit,
  onReject: () -> Unit
) {
  val context = LocalContext.current
  val formattedRent = NumberFormat.getNumberInstance(Locale.US).format(request.rentAmount)
  val formattedDeposit = NumberFormat.getNumberInstance(Locale.US).format(request.depositAmount)

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, RentifyBorder, RoundedCornerShape(16.dp))
      .testTag("request_item_${request.id}"),
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
        // Item Image
        AsyncImage(
          model = ImageRequest.Builder(context)
            .data(request.itemImage)
            .crossfade(true)
            .build(),
          contentDescription = request.itemTitle,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(RentifySurface)
        )

        // Info
        Column(modifier = Modifier.weight(1f)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
          ) {
            Text(
              text = request.itemTitle,
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
            StatusPill(status = request.status)
          }

          Spacer(modifier = Modifier.height(2.dp))

          Text(
            text = if (isReceivedTab) "From: ${request.renterName}" else "Owner: ${request.ownerName}",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 12.sp,
              color = RentifyZinc
            )
          )

          Spacer(modifier = Modifier.height(2.dp))

          Text(
            text = "${request.startDate} - ${request.endDate} • ${request.daysCount} days",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 12.sp,
              color = RentifyZinc
            )
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Rs. $formattedRent rent + Rs. $formattedDeposit deposit",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = RentifyBlack
            )
          )
        }
      }

      // Renter message
      if (request.message.isNotBlank()) {
        Spacer(modifier = Modifier.height(10.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(RentifySurface)
            .padding(10.dp)
        ) {
          Text(
            text = "\"${request.message}\"",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 12.sp,
              color = RentifyBlack
            )
          )
        }
      }

      // Action row
      Spacer(modifier = Modifier.height(12.dp))

      if (isReceivedTab && request.status == "PENDING") {
        // Accept / Reject buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .height(38.dp)
              .clip(RoundedCornerShape(999.dp))
              .border(1.dp, RentifyBorderDarker, RoundedCornerShape(999.dp))
              .clickable { onReject() }
              .testTag("reject_request_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = null,
                tint = RentifyZinc,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Decline",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontWeight = FontWeight.Medium,
                  color = RentifyZinc
                )
              )
            }
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .height(38.dp)
              .clip(RoundedCornerShape(999.dp))
              .background(RentifyBlack)
              .clickable { onAccept() }
              .testTag("accept_request_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                tint = RentifyWhite,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Accept Request",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = RentifyWhite
                )
              )
            }
          }
        }
      } else {
        // Accepted / Active state
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          if (request.status == "ACCEPTED") {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Outlined.Phone,
                contentDescription = null,
                tint = RentifySuccessGreen,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (isReceivedTab) request.renterPhone else request.ownerPhone,
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = RentifySuccessGreenText
                )
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(RentifyBlack)
                .clickable { onOpenChat() }
                .padding(horizontal = 14.dp, vertical = 7.dp)
                .testTag("open_chat_button"),
              contentAlignment = Alignment.Center
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.AutoMirrored.Outlined.Chat,
                  contentDescription = null,
                  tint = RentifyWhite,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Chat in App",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = RentifyWhite
                  )
                )
              }
            }
          } else {
            Spacer(modifier = Modifier.width(1.dp))
            Text(
              text = if (request.status == "PENDING") "Awaiting owner response" else "Request declined",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = RentifyZinc
              )
            )
          }
        }
      }
    }
  }
}

@Composable
private fun StatusPill(status: String) {
  val (bgColor, textColor, text) = when (status) {
    "ACCEPTED" -> Triple(RentifySuccessGreenBg, RentifySuccessGreenText, "Accepted")
    "REJECTED" -> Triple(RentifyErrorRedBg, RentifyErrorRedText, "Declined")
    else -> Triple(RentifyWarningYellowBg, RentifyWarningYellowText, "Pending")
  }

  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(999.dp))
      .background(bgColor)
      .padding(horizontal = 8.dp, vertical = 3.dp)
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 10.5.sp,
        fontWeight = FontWeight.Bold,
        color = textColor
      )
    )
  }
}
