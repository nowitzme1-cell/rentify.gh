package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.RentifyBg
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
import com.example.ui.theme.RentifyBorderDarker
import com.example.ui.theme.RentifyPurple
import com.example.ui.theme.RentifySurface
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc

@Composable
fun PostItemScreen(
  onBackClick: () -> Unit,
  onSubmitPost: (
    title: String,
    category: String,
    pricePerDay: Int,
    deposit: Int,
    location: String,
    description: String,
    specs: String,
    imageUrl: String
  ) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }
  val context = LocalContext.current

  var currentStep by remember { mutableIntStateOf(1) } // 1: Photos, 2: Details, 3: Price

  // Form fields
  var title by remember { mutableStateOf("") }
  var category by remember { mutableStateOf("Camera") }
  var description by remember { mutableStateOf("") }
  var location by remember { mutableStateOf("Kashmore") }
  var specs by remember { mutableStateOf("") }
  var pricePerDayStr by remember { mutableStateOf("") }
  var depositStr by remember { mutableStateOf("") }
  var agreedTerms by remember { mutableStateOf(true) }

  // Demo photos picker presets (or custom URL)
  val photoPresets = listOf(
    "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800", // Camera
    "https://images.unsplash.com/photo-1621905251918-48416bd8575a?w=800", // AC
    "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=800", // Sofa
    "https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800", // Car
    "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=800", // Generator
    "https://images.unsplash.com/photo-1504148455328-c376907d081c?w=800"  // Tool
  )
  var selectedImageUrl by remember { mutableStateOf(photoPresets[0]) }

  val categories = listOf("Camera", "AC", "Furniture", "Cars", "Tools", "Generators", "Wedding", "Dresses")

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RentifyBg)
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("post_item_screen")
  ) {
    // Top Bar
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
      Column {
        Text(
          text = "List your item",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = RentifyBlack
          )
        )
        Text(
          text = "Earn money from unused items in Balochistan & Sindh",
          style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
        )
      }
    }

    // Step Progress Indicator (3 dots line: 1 Photos, 2 Details, 3 Price)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      StepDot(step = 1, title = "Photos", currentStep = currentStep) { currentStep = 1 }
      Box(
        modifier = Modifier
          .weight(1f)
          .height(2.dp)
          .padding(horizontal = 8.dp)
          .background(if (currentStep > 1) RentifyBlack else RentifyBorder)
      )
      StepDot(step = 2, title = "Details", currentStep = currentStep) { currentStep = 2 }
      Box(
        modifier = Modifier
          .weight(1f)
          .height(2.dp)
          .padding(horizontal = 8.dp)
          .background(if (currentStep > 2) RentifyBlack else RentifyBorder)
      )
      StepDot(step = 3, title = "Price", currentStep = currentStep) { currentStep = 3 }
    }

    // Step Content Scrollable
    Column(
      modifier = Modifier
        .weight(1f)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      when (currentStep) {
        1 -> {
          // --- STEP 1: PHOTOS ---
          Text(
            text = "Select Cover Photo",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Clean, clear pictures get 4x more rent inquiries.",
            style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Cover Preview Box
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(200.dp)
              .clip(RoundedCornerShape(16.dp))
              .background(RentifySurface)
              .border(1.dp, RentifyBorder, RoundedCornerShape(16.dp))
          ) {
            AsyncImage(
              model = ImageRequest.Builder(context)
                .data(selectedImageUrl)
                .crossfade(true)
                .build(),
              contentDescription = "Cover Image",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )

            Box(
              modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(RentifyBlack)
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .align(Alignment.BottomStart)
            ) {
              Text(
                text = "Cover Photo",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 11.sp,
                  color = RentifyWhite,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          Text(
            text = "Choose Item Category Photo",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 14.sp
            )
          )
          Spacer(modifier = Modifier.height(10.dp))

          // Preset thumbnails row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            photoPresets.forEachIndexed { idx, url ->
              val isSelected = selectedImageUrl == url
              Box(
                modifier = Modifier
                  .size(76.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .border(
                    width = if (isSelected) 2.5.dp else 1.dp,
                    color = if (isSelected) RentifyBlack else RentifyBorder,
                    shape = RoundedCornerShape(12.dp)
                  )
                  .clickable { selectedImageUrl = url }
              ) {
                AsyncImage(
                  model = ImageRequest.Builder(context).data(url).build(),
                  contentDescription = null,
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.fillMaxSize()
                )
                if (isSelected) {
                  Box(
                    modifier = Modifier
                      .padding(4.dp)
                      .size(18.dp)
                      .clip(CircleShape)
                      .background(RentifyBlack)
                      .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(
                      imageVector = Icons.Outlined.Check,
                      contentDescription = null,
                      tint = RentifyWhite,
                      modifier = Modifier.size(12.dp)
                    )
                  }
                }
              }
            }
          }
        }

        2 -> {
          // --- STEP 2: DETAILS ---
          Text(
            text = "Item Details",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
          )

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = "Title",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Medium,
              fontSize = 13.sp
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text("e.g. Sony A6400 Camera with Lens", color = RentifyZinc) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = RentifySurface,
              unfocusedContainerColor = RentifySurface,
              focusedBorderColor = RentifyBlack,
              unfocusedBorderColor = Color.Transparent
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("post_title_input")
          )

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "Category",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Medium,
              fontSize = 13.sp
            )
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            categories.forEach { cat ->
              val isSelected = category.equals(cat, ignoreCase = true)
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(999.dp))
                  .background(if (isSelected) RentifyBlack else RentifySurface)
                  .clickable { category = cat }
                  .padding(horizontal = 14.dp, vertical = 8.dp)
              ) {
                Text(
                  text = cat,
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) RentifyWhite else RentifyBlack
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "Location (City)",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Medium,
              fontSize = 13.sp
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            leadingIcon = {
              Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                tint = RentifyPurple,
                modifier = Modifier.size(18.dp)
              )
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = RentifySurface,
              unfocusedContainerColor = RentifySurface,
              focusedBorderColor = RentifyBlack,
              unfocusedBorderColor = Color.Transparent
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("post_location_input")
          )

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "Description",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Medium,
              fontSize = 13.sp
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text("Describe condition, accessories included, bills available...", color = RentifyZinc) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = RentifySurface,
              unfocusedContainerColor = RentifySurface,
              focusedBorderColor = RentifyBlack,
              unfocusedBorderColor = Color.Transparent
            ),
            minLines = 3,
            modifier = Modifier.fillMaxWidth().testTag("post_desc_input")
          )

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "Key Specs (comma-separated)",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Medium,
              fontSize = 13.sp
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          OutlinedTextField(
            value = specs,
            onValueChange = { specs = it },
            placeholder = { Text("Brand Sony, Model A6400, Condition 9/10, Original Charger", color = RentifyZinc) },
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
        }

        3 -> {
          // --- STEP 3: PRICING & TERMS ---
          Text(
            text = "Rent & Deposit",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Rentify charges 10% commission when rental completes successfully.",
            style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
          )

          Spacer(modifier = Modifier.height(16.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Rent Per Day (Rs.)",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Medium,
                  fontSize = 13.sp
                )
              )
              Spacer(modifier = Modifier.height(4.dp))
              OutlinedTextField(
                value = pricePerDayStr,
                onValueChange = { pricePerDayStr = it },
                placeholder = { Text("2000", color = RentifyZinc) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedContainerColor = RentifySurface,
                  unfocusedContainerColor = RentifySurface,
                  focusedBorderColor = RentifyBlack,
                  unfocusedBorderColor = Color.Transparent
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("post_price_input")
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Refundable Deposit (Rs.)",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Medium,
                  fontSize = 13.sp
                )
              )
              Spacer(modifier = Modifier.height(4.dp))
              OutlinedTextField(
                value = depositStr,
                onValueChange = { depositStr = it },
                placeholder = { Text("5000", color = RentifyZinc) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedContainerColor = RentifySurface,
                  unfocusedContainerColor = RentifySurface,
                  focusedBorderColor = RentifyBlack,
                  unfocusedBorderColor = Color.Transparent
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("post_deposit_input")
              )
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
          ) {
            Checkbox(
              checked = agreedTerms,
              onCheckedChange = { agreedTerms = it },
              colors = CheckboxDefaults.colors(checkedColor = RentifyBlack)
            )
            Text(
              text = "I confirm I am the rightful owner and this item is in working condition.",
              style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
            )
          }
        }
      }
    }

    // Bottom Navigation Bar for Steps
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      if (currentStep < 3) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(RentifyBlack)
            .clickable { currentStep += 1 }
            .testTag("post_next_button"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Continue",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 15.sp,
              color = RentifyWhite
            )
          )
        }
      } else {
        val canSubmit = title.isNotBlank() && pricePerDayStr.isNotBlank() && agreedTerms
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(if (canSubmit) RentifyBlack else RentifyZinc)
            .clickable(enabled = canSubmit) {
              val p = pricePerDayStr.toIntOrNull() ?: 2000
              val d = depositStr.toIntOrNull() ?: 5000
              val desc = if (description.isNotBlank()) description else "Well maintained $title available for rent in $location."
              val sp = if (specs.isNotBlank()) specs else "Condition 9/10, Verified Owner, Available today"

              onSubmitPost(
                title,
                category,
                p,
                d,
                location,
                desc,
                sp,
                selectedImageUrl
              )
            }
            .testTag("submit_post_button"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Post Item • Send for Review",
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
}

@Composable
private fun StepDot(
  step: Int,
  title: String,
  currentStep: Int,
  onClick: () -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.clickable { onClick() }
  ) {
    Box(
      modifier = Modifier
        .size(26.dp)
        .clip(CircleShape)
        .background(if (currentStep >= step) RentifyBlack else RentifySurface)
        .border(1.dp, if (currentStep >= step) RentifyBlack else RentifyBorder, CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "$step",
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp,
          color = if (currentStep >= step) RentifyWhite else RentifyZinc
        )
      )
    }
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = title,
      style = MaterialTheme.typography.bodySmall.copy(
        fontWeight = if (currentStep == step) FontWeight.Bold else FontWeight.Medium,
        color = if (currentStep == step) RentifyBlack else RentifyZinc
      )
    )
  }
}
