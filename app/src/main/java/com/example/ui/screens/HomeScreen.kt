package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Publish
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.RentalItemEntity
import com.example.ui.components.ItemCard
import com.example.ui.theme.RentifyBg
import com.example.ui.theme.RentifyBlack
import com.example.ui.theme.RentifyBorder
import com.example.ui.theme.RentifyPurple
import com.example.ui.theme.RentifySurface
import com.example.ui.theme.RentifyWhite
import com.example.ui.theme.RentifyZinc

@Composable
fun HomeScreen(
  items: List<RentalItemEntity>,
  searchQuery: String,
  onSearchChange: (String) -> Unit,
  selectedCategory: String,
  onCategorySelect: (String) -> Unit,
  currentCity: String,
  onItemClick: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val categories = listOf("All", "Camera", "AC", "Furniture", "Cars", "Tools", "Generators", "Wedding", "Dresses")

  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    modifier = modifier
      .fillMaxSize()
      .background(RentifyBg)
      .testTag("home_screen"),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 100.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Search Bar (Full span)
    item(span = { GridItemSpan(2) }) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
      ) {
        OutlinedTextField(
          value = searchQuery,
          onValueChange = onSearchChange,
          placeholder = {
            Text(
              text = "Search cameras, cars, tools in $currentCity...",
              style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 13.5.sp,
                color = RentifyZinc
              )
            )
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Outlined.Search,
              contentDescription = "Search",
              tint = RentifyZinc,
              modifier = Modifier.size(20.dp)
            )
          },
          trailingIcon = {
            if (searchQuery.isNotEmpty()) {
              IconButton(onClick = { onSearchChange("") }) {
                Icon(
                  imageVector = Icons.Outlined.Close,
                  contentDescription = "Clear",
                  tint = RentifyZinc,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
          },
          shape = RoundedCornerShape(999.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = RentifyWhite,
            unfocusedContainerColor = RentifySurface,
            focusedBorderColor = RentifyBlack,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = RentifyBlack
          ),
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("search_input")
        )
      }
    }

    // 2. Categories Pill Row (Full span)
    item(span = { GridItemSpan(2) }) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState())
          .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        categories.forEach { category ->
          val isSelected = category.equals(selectedCategory, ignoreCase = true)
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(999.dp))
              .background(if (isSelected) RentifyBlack else RentifySurface)
              .border(
                width = 1.dp,
                color = if (isSelected) RentifyBlack else RentifyBorder,
                shape = RoundedCornerShape(999.dp)
              )
              .clickable { onCategorySelect(category) }
              .padding(horizontal = 16.dp, vertical = 9.dp)
              .testTag("category_pill_$category"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = category,
              style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                color = if (isSelected) RentifyWhite else RentifyBlack
              )
            )
          }
        }
      }
    }

    // 3. Section Title Row (Full span)
    item(span = { GridItemSpan(2) }) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (selectedCategory == "All") "Near you in $currentCity" else "$selectedCategory in $currentCity",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = RentifyBlack
          )
        )

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clickable { onCategorySelect("All") }
        ) {
          Text(
            text = "See all",
            style = MaterialTheme.typography.bodySmall.copy(
              fontWeight = FontWeight.Medium,
              fontSize = 13.sp,
              color = RentifyZinc
            )
          )
          Spacer(modifier = Modifier.width(3.dp))
          Icon(
            imageVector = Icons.Outlined.ArrowForward,
            contentDescription = null,
            tint = RentifyZinc,
            modifier = Modifier.size(13.dp)
          )
        }
      }
    }

    // 4. Empty State if no items found
    if (items.isEmpty()) {
      item(span = { GridItemSpan(2) }) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = RentifyWhite),
          border = androidx.compose.foundation.BorderStroke(1.dp, RentifyBorder)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Outlined.Search,
              contentDescription = null,
              tint = RentifyZinc,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No rental items found",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Try searching for Sony camera, AC, Mehran, or switch city.",
              style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
            )
          }
        }
      }
    } else {
      // 5. Grid of Items (2 columns)
      items(items, key = { it.id }) { item ->
        ItemCard(
          item = item,
          onClick = { onItemClick(item.id) }
        )
      }
    }

    // 6. "How it works?" 3 steps section (Full span)
    item(span = { GridItemSpan(2) }) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 24.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = RentifyWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, RentifyBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Text(
            text = "How Rentify.pk Works",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = RentifyBlack
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Earn from idle items or rent for cheap in your city.",
            style = MaterialTheme.typography.bodySmall.copy(color = RentifyZinc)
          )

          Spacer(modifier = Modifier.height(16.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            HowItWorksStep(
              number = "1",
              title = "Post Item",
              desc = "Camera, AC, Car or Tools lying idle",
              icon = Icons.Outlined.Publish
            )
            HowItWorksStep(
              number = "2",
              title = "Get Requests",
              desc = "Chat, verify CNIC & agree to meet",
              icon = Icons.Outlined.CheckCircleOutline
            )
            HowItWorksStep(
              number = "3",
              title = "Earn & Return",
              desc = "Cash on handover + deposit back",
              icon = Icons.Outlined.Payments
            )
          }
        }
      }
    }
  }
}

@Composable
private fun HowItWorksStep(
  number: String,
  title: String,
  desc: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.width(96.dp)
  ) {
    Box(
      modifier = Modifier
        .size(46.dp)
        .clip(CircleShape)
        .background(RentifySurface),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = RentifyPurple,
        modifier = Modifier.size(22.dp)
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "$number. $title",
      style = MaterialTheme.typography.bodySmall.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        color = RentifyBlack
      )
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = desc,
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 10.sp,
        color = RentifyZinc,
        lineHeight = 13.sp
      ),
      maxLines = 3
    )
  }
}
