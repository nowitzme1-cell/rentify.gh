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
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.entity.RentalItemEntity
import com.example.ui.theme.RentifyBg
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
import com.example.ui.theme.RentifyBorderDarker
import com.example.ui.theme.RentifyPurple
import com.example.ui.theme.RentifySuccessGreen
import com.example.ui.theme.RentifySuccessGreenBg
import com.example.ui.theme.RentifySuccessGreenText
import com.example.ui.theme.RentifySurface
import com.example.ui.theme.RentifyVerifiedBlue
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ItemDetailScreen(
  item: RentalItemEntity,
  onBackClick: () -> Unit,
  onRequestClick: () -> Unit,
  onDirectChatClick: () -> Unit,
  onOwnerProfileClick: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val context = LocalContext.current
  val images = item.getImageList()
  var selectedImageIndex by remember { mutableIntStateOf(0) }
  val formattedPrice = NumberFormat.getNumberInstance(Locale.US).format(item.pricePerDay)
  val formattedWeekPrice = NumberFormat.getNumberInstance(Locale.US).format(item.pricePerWeek)
  val formattedDeposit = NumberFormat.getNumberInstance(Locale.US).format(item.deposit)
  val weeklySavings = (item.pricePerDay * 7) - item.pricePerWeek

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(RentifyBg)
      .testTag("item_detail_screen")
  ) {
    // Scrollable Content
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(bottom = 100.dp)
    ) {
      // 1. Image Carousel with Floating Back Button
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(340.dp)
          .background(RentifySurface)
      ) {
        val currentImg = images.getOrElse(selectedImageIndex) { images.firstOrNull() ?: "" }

        AsyncImage(
          model = ImageRequest.Builder(context)
            .data(currentImg)
            .crossfade(true)
            .build(),
          contentDescription = item.title,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Floating Back Button
        IconButton(
          onClick = onBackClick,
          modifier = Modifier
            .statusBarsPadding()
            .padding(16.dp)
            .size(42.dp)
            .shadow(6.dp, CircleShape)
            .clip(CircleShape)
            .background(RentifyWhite.copy(alpha = 0.95f))
            .testTag("detail_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Back",
            tint = RentifyBlack,
            modifier = Modifier.size(20.dp)
          )
        }

        // Image Dots Indicator
        if (images.size > 1) {
          Row(
            modifier = Modifier
              .align(Alignment.BottomCenter)
              .padding(bottom = 12.dp)
              .clip(RoundedCornerShape(999.dp))
              .background(RentifyWhite.copy(alpha = 0.85f))
              .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            images.forEachIndexed { index, _ ->
              Box(
                modifier = Modifier
                  .size(if (selectedImageIndex == index) 8.dp else 6.dp)
                  .clip(CircleShape)
                  .background(if (selectedImageIndex == index) RentifyBlack else RentifyZinc.copy(alpha = 0.5f))
                  .clickable { selectedImageIndex = index }
              )
            }
          }
        }
      }

      // 2. Content Details
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 16.dp)
      ) {
        // Title (22sp weight 700)
        Text(
          text = item.title,
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = RentifyBlack,
            lineHeight = 28.sp
          )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Owner Card
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, RentifyBorder, RoundedCornerShape(16.dp))
            .clickable { onOwnerProfileClick(item.ownerId) }
            .testTag("owner_profile_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = RentifyWhite)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              AsyncImage(
                model = ImageRequest.Builder(context)
                  .data(item.ownerAvatar)
                  .crossfade(true)
                  .build(),
                contentDescription = item.ownerName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                  .size(44.dp)
                  .clip(CircleShape)
                  .border(1.5.dp, RentifyBorder, CircleShape)
              )

              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = item.ownerName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontWeight = FontWeight.SemiBold,
                      fontSize = 14.sp,
                      color = RentifyBlack
                    )
                  )
                  if (item.ownerVerified) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                      modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(RentifyVerifiedBlue),
                      contentAlignment = Alignment.Center
                    ) {
                      Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Verified",
                        tint = RentifyWhite,
                        modifier = Modifier.size(10.dp)
                      )
                    }
                  }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "📍 ${item.location} • 12 items • ",
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontSize = 12.sp,
                      color = RentifyZinc
                    )
                  )
                  Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(13.dp)
                  )
                  Text(
                    text = " 4.9 (23)",
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Medium,
                      color = RentifyZinc
                    )
                  )
                }
              }
            }

            Icon(
              imageVector = Icons.Outlined.ChevronRight,
              contentDescription = "View Profile",
              tint = RentifyZinc,
              modifier = Modifier.size(20.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Price Breakdown Card
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
              Text(
                text = "Per day rental",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontSize = 13.sp,
                  color = RentifyZinc
                )
              )
              Text(
                text = "Rs. $formattedPrice",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontSize = 20.sp,
                  fontWeight = FontWeight.Bold,
                  color = RentifyBlack
                )
              )
            }

            HorizontalDivider(
              modifier = Modifier.padding(vertical = 12.dp),
              color = RentifyBorder
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "Per week (7 days)",
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                    color = RentifyZinc
                  )
                )
                if (weeklySavings > 0) {
                  Spacer(modifier = Modifier.width(6.dp))
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(999.dp))
                      .background(RentifySuccessGreenBg)
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = "Save Rs. $weeklySavings",
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = RentifySuccessGreenText
                      )
                    )
                  }
                }
              }
              Text(
                text = "Rs. $formattedWeekPrice",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontSize = 14.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = RentifyBlack
                )
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Refundable deposit",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontSize = 13.sp,
                  color = RentifyZinc
                )
              )
              Text(
                text = "Rs. $formattedDeposit",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Medium,
                  color = RentifyBlack
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // About this item
        Text(
          text = "About this item",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = RentifyBlack
          )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = item.description,
          style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 14.sp,
            color = Color(0xFF3F3F46),
            lineHeight = 22.sp
          )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Specs & Details (bullet list)
        Text(
          text = "Item Specifications",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = RentifyBlack
          )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          item.getSpecsList().forEach { spec ->
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = RentifyPurple,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = spec,
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontSize = 13.sp,
                  color = RentifyBlack
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Location Box
        Text(
          text = "Handover Location",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = RentifyBlack
          )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, RentifyBorder, RoundedCornerShape(16.dp)),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = RentifySurface)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(RentifyWhite),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                tint = RentifyPurple,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "${item.location} City • ${item.distance}",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 14.sp,
                  color = RentifyBlack
                )
              )
              Text(
                text = "Near Bus Stand / Public Center • Exact address shared once booked",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 12.sp,
                  color = RentifyZinc
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Safety Tips
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, RentifyBorder, RoundedCornerShape(16.dp)),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = RentifySurface)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Outlined.Security,
              contentDescription = null,
              tint = RentifySuccessGreen,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Rentify Safety Guarantee: Meet in a safe public spot. Inspect item and test condition before taking. Keep CNIC photo for security deposit protection.",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = RentifyZinc,
                lineHeight = 17.sp
              )
            )
          }
        }
      }
    }

    // Fixed Bottom Action Bar (Apple / Airbnb style glass pill bar)
    Surface(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .navigationBarsPadding(),
      color = RentifyWhite.copy(alpha = 0.95f),
      shadowElevation = 8.dp
    ) {
      Column {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(RentifyBorder)
        )
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Left 35%: Secondary Pill "Chat"
          Box(
            modifier = Modifier
              .weight(0.35f)
              .height(48.dp)
              .clip(RoundedCornerShape(999.dp))
              .background(RentifyWhite)
              .border(1.dp, RentifyBorderDarker, RoundedCornerShape(999.dp))
              .clickable { onDirectChatClick() }
              .testTag("chat_owner_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Outlined.Chat,
                contentDescription = "Chat",
                tint = RentifyBlack,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Chat",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 14.sp,
                  color = RentifyBlack
                )
              )
            }
          }

          // Right 65%: Primary Black Pill "Send Request • Rs. 2,000/day"
          Box(
            modifier = Modifier
              .weight(0.65f)
              .height(48.dp)
              .clip(RoundedCornerShape(999.dp))
              .background(RentifyBlack)
              .clickable { onRequestClick() }
              .testTag("send_rent_request_button"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Send Request • Rs. $formattedPrice/d",
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = RentifyWhite
              )
            )
          }
        }
      }
    }
  }
}
