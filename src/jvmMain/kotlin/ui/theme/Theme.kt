package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

private val darkColorScheme = darkColorScheme(
	primary = Purple80,
	secondary = PurpleGrey80,
	tertiary = Pink80
)

private val lightColorScheme = lightColorScheme(
	primary = Purple40,
	secondary = PurpleGrey40,
	tertiary = Pink40
)

@Immutable
data class ExtendedColors(
	val examBackground: Color,
	val noGradeBackground: Color,
	val badGradeBackground: Color,
	val fineGradeBackground: Color,
	val goodGradeBackground: Color,
	val excellentGradeBackground: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
	ExtendedColors(
		examBackground = Color.Unspecified,
		noGradeBackground = Color.Unspecified,
		badGradeBackground = Color.Unspecified,
		fineGradeBackground = Color.Unspecified,
		goodGradeBackground = Color.Unspecified,
		excellentGradeBackground = Color.Unspecified,
	)
}

val extendedColorsLight = ExtendedColors(
	examBackground = ExamBackGroundLight,
	noGradeBackground = NoScoreBackgroundLight,
	badGradeBackground = BadScoreBackgroundLight,
	fineGradeBackground = FineScoreBackgroundLight,
	goodGradeBackground = GoodScoreBackgroundLight,
	excellentGradeBackground = ExcellentScoreBackgroundLight,
)

val extendedColorsDark = ExtendedColors(
	examBackground = ExamBackGroundDark,
	noGradeBackground = NoScoreBackgroundDark,
	badGradeBackground = BadScoreBackgroundDark,
	fineGradeBackground = FineScoreBackgroundDark,
	goodGradeBackground = GoodScoreBackgroundDark,
	excellentGradeBackground = ExcellentScoreBackgroundDark,
)



@Composable
fun CSFBRSTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	// Dynamic color is available on Android 12+
	content: @Composable () -> Unit
) {
	val colorScheme = when {
		darkTheme -> darkColorScheme
		else -> lightColorScheme
	}
	CompositionLocalProvider(
		LocalExtendedColors provides
				if(darkTheme) extendedColorsDark
				else extendedColorsLight
	) {
		MaterialTheme(
			colorScheme = colorScheme,
			typography = Typography,
			content = content
		)
	}
}

// Use with e.g. ExtendedTheme.colors.tertiary
object ExtendedTheme {
	val colors: ExtendedColors
		@Composable
		get() = LocalExtendedColors.current
}