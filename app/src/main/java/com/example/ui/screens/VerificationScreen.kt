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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.UploadFile
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.RentifyBg
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
import com.example.ui.theme.RentifyPurple
import com.example.ui.theme.RentifySurface
import com.example.ui.theme.RentifyVerifiedBlue
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc

@Composable
fun VerificationScreen(
  onBackClick: () -> Unit,
  onSubmitVerification: (front: String, back: String, selfie: String) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val context = LocalContext.current
  var frontUrl by remember { mutableStateOf("https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=400") }
  var backUrl by remember { mutableStateOf("https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=400") }
  var selfieUrl by remember { mutableStateOf("https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=400") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RentifyBg)
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("verification_screen")
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
        text = "Get Blue Tick Verified",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp,
          color = RentifyBlack
        )
      )
    }

    Column(
      modifier = Modifier
        .weight(1f)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
      // Info box with Blue Tick
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = RentifyWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, RentifyBorder)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(RentifyVerifiedBlue),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = null,
              tint = RentifyWhite,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Government ID Verification",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
              )
            )
            Text(
              text = "Verified owners get safe handover guarantee, 3x more bookings, and trusted blue badge.",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = RentifyZinc
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Document 1: CNIC Front
      UploadBox(
        title = "1. CNIC Front Side",
        subtitle = "Photo of front side showing full name & 13-digit CNIC number",
        imageUrl = frontUrl,
        icon = Icons.Outlined.CreditCard,
        onSelect = { frontUrl = "https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=400" }
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Document 2: CNIC Back
      UploadBox(
        title = "2. CNIC Back Side",
        subtitle = "Photo of back side showing Kashmore / Balochistan permanent address",
        imageUrl = backUrl,
        icon = Icons.Outlined.CreditCard,
        onSelect = { backUrl = "https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=400" }
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Document 3: Selfie with CNIC
      UploadBox(
        title = "3. Selfie Holding CNIC",
        subtitle = "Hold your original CNIC near your chest with clearly visible face",
        imageUrl = selfieUrl,
        icon = Icons.Outlined.Face,
        onSelect = { selfieUrl = "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=400" }
      )
    }

    // Submit Button
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .clip(RoundedCornerShape(999.dp))
          .background(RentifyBlack)
          .clickable { onSubmitVerification(frontUrl, backUrl, selfieUrl) }
          .testTag("submit_verification_button"),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "Submit for Admin Review",
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = RentifyWhite
          )
        )
      }
    }
  }
}

@Composable
private fun UploadBox(
  title: String,
  subtitle: String,
  imageUrl: String,
  icon: ImageVector,
  onSelect: () -> Unit
) {
  val context = LocalContext.current

  Column {
    Text(
      text = title,
      style = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.5.sp,
        color = RentifyBlack
      )
    )
    Text(
      text = subtitle,
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 11.5.sp,
        color = RentifyZinc
      )
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(110.dp)
        .border(1.dp, RentifyBorder, RoundedCornerShape(14.dp))
        .clickable { onSelect() },
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = RentifyWhite)
    ) {
      Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        AsyncImage(
          model = ImageRequest.Builder(context).data(imageUrl).crossfade(true).build(),
          contentDescription = title,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(86.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(RentifySurface)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(RentifyVerifiedBlue),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = RentifyWhite,
                modifier = Modifier.size(11.dp)
              )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Photo Selected",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = RentifyBlack
              )
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Tap to change photo",
            style = MaterialTheme.typography.labelSmall.copy(color = RentifyPurple)
          )
        }
      }
    }
  }
}
