package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.entity.RentalItemEntity
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
import com.example.ui.theme.RentifyPurple
import com.example.ui.theme.RentifySuccessGreen
import com.example.ui.theme.RentifySurface
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentRequestSheet(
  item: RentalItemEntity,
  sheetState: SheetState,
  days: Int,
  startDate: String,
  endDate: String,
  message: String,
  agreeTerms: Boolean,
  onDaysChange: (Int) -> Unit,
  onMessageChange: (String) -> Unit,
  onAgreeChange: (Boolean) -> Unit,
  onDismiss: () -> Unit,
  onConfirm: () -> Unit
) {
  val context = LocalContext.current
  val rent = item.pricePerDay * days
  val platformFee = (rent * 0.10f).toInt() // 10%
  val deposit = item.deposit
  val totalOnMeet = rent + platformFee + deposit

  val formattedRent = NumberFormat.getNumberInstance(Locale.US).format(rent)
  val formattedFee = NumberFormat.getNumberInstance(Locale.US).format(platformFee)
  val formattedDeposit = NumberFormat.getNumberInstance(Locale.US).format(deposit)
  val formattedTotal = NumberFormat.getNumberInstance(Locale.US).format(totalOnMeet)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = RentifyWhite,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    dragHandle = {
      Box(
        modifier = Modifier
          .padding(vertical = 12.dp)
          .size(width = 36.dp, height = 4.dp)
          .clip(CircleShape)
          .background(RentifyBorder)
      )
    },
    modifier = Modifier.testTag("rent_request_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 8.dp)
        .padding(bottom = 32.dp)
    ) {
      Text(
        text = "Send Rent Request",
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp,
          color = RentifyBlack
        )
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Item mini card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, RentifyBorder, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = RentifySurface)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          AsyncImage(
            model = ImageRequest.Builder(context)
              .data(item.getImageList().firstOrNull() ?: "")
              .crossfade(true)
              .build(),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .size(48.dp)
              .clip(RoundedCornerShape(8.dp))
          )
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = item.title,
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = RentifyBlack
              ),
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
            Text(
              text = "Owner: ${item.ownerName} • ${item.location}",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = RentifyZinc
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Days Selector & Dates
      Text(
        text = "Rental Duration",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 14.sp,
          color = RentifyBlack
        )
      )
      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "$startDate - $endDate",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 14.sp,
              color = RentifyBlack
            )
          )
          Text(
            text = "$days days rental",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 12.sp,
              color = RentifyZinc
            )
          )
        }

        // Stepper (+ / -)
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(RentifySurface)
            .padding(4.dp)
        ) {
          IconButton(
            onClick = { if (days > 1) onDaysChange(days - 1) },
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Remove,
              contentDescription = "Decrease days",
              tint = RentifyBlack,
              modifier = Modifier.size(16.dp)
            )
          }

          Text(
            text = "$days d",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
          )

          IconButton(
            onClick = { onDaysChange(days + 1) },
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Increase days",
              tint = RentifyBlack,
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Price Calculation Summary Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, RentifyBorder, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = RentifySurface)
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
              text = "$days days rent (Rs. ${item.pricePerDay} × $days)",
              style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
            )
            Text(
              text = "Rs. $formattedRent",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = RentifyBlack
              )
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Rentify platform fee (10%)",
              style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
            )
            Text(
              text = "Rs. $formattedFee",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = RentifyPurple
              )
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Refundable deposit (cash)",
              style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
            )
            Text(
              text = "Rs. $formattedDeposit",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = RentifyBlack
              )
            )
          }

          HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            color = RentifyBorder
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Total Cash on Handover",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = RentifyBlack
                )
              )
              Text(
                text = "Includes Rs. $formattedDeposit returned after 2 days",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 11.sp,
                  color = RentifySuccessGreen
                )
              )
            }
            Text(
              text = "Rs. $formattedTotal",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = RentifyBlack
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Message Textarea
      Text(
        text = "Message to Owner",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 13.sp,
          color = RentifyBlack
        )
      )
      Spacer(modifier = Modifier.height(6.dp))
      OutlinedTextField(
        value = message,
        onValueChange = onMessageChange,
        placeholder = {
          Text(
            text = "Tell owner what you need it for, handover timing...",
            style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
          )
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = RentifySurface,
          unfocusedContainerColor = RentifySurface,
          focusedBorderColor = RentifyBlack,
          unfocusedBorderColor = Color.Transparent
        ),
        minLines = 3,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("request_message_input")
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Checkbox Agreement
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Checkbox(
          checked = agreeTerms,
          onCheckedChange = onAgreeChange,
          colors = CheckboxDefaults.colors(
            checkedColor = RentifyBlack,
            uncheckedColor = RentifyZinc
          ),
          modifier = Modifier.testTag("agree_terms_checkbox")
        )
        Text(
          text = "I agree to return on time, handle with care, and present original CNIC on meet.",
          style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 12.sp,
            color = RentifyZinc,
            lineHeight = 16.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Confirm Button
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .clip(RoundedCornerShape(999.dp))
          .background(if (agreeTerms) RentifyBlack else RentifyZinc)
          .clickable(enabled = agreeTerms) { onConfirm() }
          .testTag("confirm_rent_request_button"),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "Confirm • Send Request",
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
