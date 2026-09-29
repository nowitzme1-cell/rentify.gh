package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val RentifyLightColorScheme = lightColorScheme(
  primary = RentifyBlack,
  onPrimary = RentifyWhite,
  primaryContainer = RentifySurface,
  onPrimaryContainer = RentifyBlack,
  secondary = RentifyZinc,
  onSecondary = RentifyWhite,
  secondaryContainer = RentifySurface,
  onSecondaryContainer = RentifyBlack,
  tertiary = RentifyPurple,
  onTertiary = RentifyWhite,
  background = RentifyBg,
  onBackground = RentifyBlack,
  surface = RentifyWhite,
  onSurface = RentifyBlack,
  surfaceVariant = RentifySurface,
  onSurfaceVariant = RentifyZinc,
  outline = RentifyBorder,
  outlineVariant = RentifyBorderDarker
)

private val RentifyDarkColorScheme = darkColorScheme(
  primary = RentifyWhite,
  onPrimary = RentifyBlack,
  primaryContainer = RentifyDarkGrey,
  onPrimaryContainer = RentifyWhite,
  secondary = RentifyLightGrey,
  onSecondary = RentifyBlack,
  secondaryContainer = RentifyDarkGrey,
  onSecondaryContainer = RentifyWhite,
  tertiary = RentifyPurple,
  onTertiary = RentifyWhite,
  background = RentifyBlack,
  onBackground = RentifyWhite,
  surface = RentifyDark,
  onSurface = RentifyWhite,
  surfaceVariant = RentifyDarkGrey,
  onSurfaceVariant = RentifyLightGrey,
  outline = RentifyDarkGrey,
  outlineVariant = RentifyMediumGrey
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) RentifyDarkColorScheme else RentifyLightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
