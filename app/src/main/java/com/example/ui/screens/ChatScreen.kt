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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.data.entity.ChatMessageEntity
import com.example.data.entity.RentalRequestEntity
import com.example.ui.theme.RentifyBg
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
import com.example.ui.theme.RentifySuccessGreen
import com.example.ui.theme.RentifySurface
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc

@Composable
fun ChatScreen(
  request: RentalRequestEntity,
  currentUserId: String,
  messages: List<ChatMessageEntity>,
  onBackClick: () -> Unit,
  onSendMessage: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }
  val context = LocalContext.current
  var textInput by remember { mutableStateOf("") }
  val listState = rememberLazyListState()

  val otherUserName = if (currentUserId == request.ownerId) request.renterName else request.ownerName
  val otherUserPhone = if (currentUserId == request.ownerId) request.renterPhone else request.ownerPhone

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RentifyBg)
      .statusBarsPadding()
      .navigationBarsPadding()
      .imePadding()
      .testTag("chat_screen")
  ) {
    // Top Bar (Header with user info + item title + online dot)
    Surface(
      modifier = Modifier.fillMaxWidth(),
      color = RentifyWhite,
      shadowElevation = 1.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier.size(36.dp)
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Back",
            tint = RentifyBlack,
            modifier = Modifier.size(18.dp)
          )
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Avatar
        Box(modifier = Modifier.size(40.dp)) {
          AsyncImage(
            model = ImageRequest.Builder(context)
              .data("https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200")
              .crossfade(true)
              .build(),
            contentDescription = otherUserName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .border(1.dp, RentifyBorder, CircleShape)
          )

          // Online green dot
          Box(
            modifier = Modifier
              .size(10.dp)
              .clip(CircleShape)
              .background(RentifySuccessGreen)
              .border(1.5.dp, RentifyWhite, CircleShape)
              .align(Alignment.BottomEnd)
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = otherUserName,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 14.sp,
              color = RentifyBlack
            )
          )
          Text(
            text = "${request.itemTitle} • $otherUserPhone",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 11.5.sp,
              color = RentifyZinc
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }

        // Phone call icon
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(RentifySurface),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Outlined.Call,
            contentDescription = "Call",
            tint = RentifyBlack,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }

    // Messages Area
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(messages, key = { it.id }) { msg ->
        if (msg.isSystem) {
          // System Message Pill in Center
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
          ) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(RentifySurface)
                .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
              Text(
                text = "🛡️ ${msg.text}",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 11.5.sp,
                  color = RentifyZinc
                )
              )
            }
          }
        } else {
          val isMe = msg.senderId == currentUserId
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
          ) {
            Box(
              modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                  if (isMe)
                    RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 4.dp)
                  else
                    RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 4.dp, bottomEnd = 18.dp)
                )
                .background(if (isMe) RentifyBlack else RentifyWhite)
                .then(
                  if (!isMe) Modifier.border(1.dp, RentifyBorder, RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 4.dp, bottomEnd = 18.dp)) else Modifier
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
              Text(
                text = msg.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontSize = 13.5.sp,
                  color = if (isMe) RentifyWhite else RentifyBlack,
                  lineHeight = 19.sp
                )
              )
            }
          }
        }
      }
    }

    // Input Bar (Floating pill with send button)
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
      color = Color.Transparent
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .clip(RoundedCornerShape(999.dp))
          .background(RentifySurface)
          .padding(start = 16.dp, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = textInput,
          onValueChange = { textInput = it },
          placeholder = {
            Text(
              text = "Type message to agree meet time...",
              style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
            )
          },
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = RentifyBlack
          ),
          singleLine = true,
          modifier = Modifier
            .weight(1f)
            .testTag("chat_input_field")
        )

        // Send Button (Black circle 38dp with send arrow)
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(RentifyBlack)
            .clickable {
              if (textInput.isNotBlank()) {
                onSendMessage(textInput)
                textInput = ""
              }
            }
            .testTag("chat_send_button"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Send",
            tint = RentifyWhite,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}
