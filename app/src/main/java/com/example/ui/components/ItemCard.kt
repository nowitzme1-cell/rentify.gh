package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
import com.example.ui.theme.RentifySurface
import com.example.ui.theme.RentifyVerifiedBlue
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ItemCard(
  item: RentalItemEntity,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val firstImage = item.getImageList().firstOrNull() ?: ""
  val formattedPrice = NumberFormat.getNumberInstance(Locale.US).format(item.pricePerDay)

  Card(
    modifier = modifier
      .fillMaxWidth()
      .shadow(
        elevation = 3.dp,
        shape = RoundedCornerShape(20.dp),
        ambientColor = Color(0x0A000000),
        spotColor = Color(0x14000000)
      )
      .border(1.dp, RentifyBorder, RoundedCornerShape(20.dp))
      .clickable { onClick() }
      .testTag("item_card_${item.id}"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = RentifyWhite)
  ) {
    Column {
      // 1. Cover Image (180dp height, 20dp top rounded corners)
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
          .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
          .background(RentifySurface)
      ) {
        AsyncImage(
          model = ImageRequest.Builder(context)
            .data(firstImage)
            .crossfade(true)
            .build(),
          contentDescription = item.title,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxWidth().height(180.dp)
        )

        // Category Tag Top-Left
        Box(
          modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(RentifyWhite.copy(alpha = 0.90f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .align(Alignment.TopStart)
        ) {
          Text(
            text = item.category,
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = RentifyBlack
            )
          )
        }
      }

      // 2. Content Info
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp)
      ) {
        // Title (15sp, weight 600, 1 line ellipsis)
        Text(
          text = item.title,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = RentifyBlack
          ),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Owner Row: Avatar (24dp circle), name (13sp grey), Instagram Blue Verified Tick
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          AsyncImage(
            model = ImageRequest.Builder(context)
              .data(item.ownerAvatar)
              .crossfade(true)
              .build(),
            contentDescription = item.ownerName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .size(24.dp)
              .clip(CircleShape)
              .border(1.dp, RentifyBorder, CircleShape)
          )

          Text(
            text = item.ownerName,
            style = MaterialTheme.typography.bodySmall.copy(
              fontWeight = FontWeight.Medium,
              fontSize = 13.sp,
              color = RentifyZinc
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false)
          )

          if (item.ownerVerified) {
            // Instagram Verified Blue Tick
            Box(
              modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(RentifyVerifiedBlue),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Verified Owner",
                tint = RentifyWhite,
                modifier = Modifier.size(10.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Price Row: "Rs. 2,000 /day" (Black, premium, not red)
        Text(
          text = buildAnnotatedString {
            withStyle(
              SpanStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = RentifyBlack
              )
            ) {
              append("Rs. $formattedPrice ")
            }
            withStyle(
              SpanStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = RentifyZinc
              )
            ) {
              append("/day")
            }
          }
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Location Row: Location outline icon 12px grey + Text "Kashmore • 1.2km"
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null,
            tint = RentifyZinc,
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "${item.location} • ${item.distance}",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 11.sp,
              color = RentifyZinc
            )
          )
        }
      }
    }
  }
}
