@file:OptIn(ExperimentalFoundationApi::class)

package stats

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import stats.model.ScoringType
import stats.model.StatisticRow
import ui.theme.ExtendedTheme
import ui.theme.StatsRowBackGroundLight

val TEXT_PADDING: PaddingValues = PaddingValues(
	start = 5.dp,
	top = 10.dp,
	end = 5.dp,
	bottom = 5.dp
)
val SCORE_COLUMN_PADDING: PaddingValues = PaddingValues(
	start = 20.dp,
	top = 10.dp,
	end = 20.dp,
	bottom = 10.dp
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StatsContent(component: StatsComponent) {
	val stats by component.model.subscribeAsState()
	val scrollState = rememberLazyStaggeredGridState()

	LaunchedEffect(null) {
		component.loadStats()
	}
	Scaffold(topBar = {
		TopAppBar(
			navigationIcon = {
				IconButton(
					onClick = {
						component.exitStats()
					}
				) {
					Icon(imageVector = Icons.Default.ArrowBack, "Назад")
				}
			},
			title = {
				Text("Статистика")
			}
		)
	}) { scaffoldPadding ->
		LazyVerticalStaggeredGrid(
			userScrollEnabled = true,
			state = scrollState,
			modifier = Modifier.fillMaxSize().padding(scaffoldPadding),
			columns = StaggeredGridCells.Adaptive(400.dp),
			horizontalArrangement = Arrangement.spacedBy(10.dp),
			verticalItemSpacing = 10.dp,
			contentPadding = PaddingValues(10.dp)
		) {
			itemsIndexed(stats.stats) { _: Int, row: StatisticRow ->
				OneStat(row)
			}
		}
	}
}

@Composable
fun OneStat(row: StatisticRow) {
	OutlinedCard(
		elevation = CardDefaults.outlinedCardElevation(),
		colors = CardDefaults.outlinedCardColors(),
		shape = CardDefaults.outlinedShape
	) {
		Text(
			text = row.disciplineName,
			modifier = Modifier
				.padding(TEXT_PADDING)
				.fillMaxWidth(),
			//.background(ExtendedTheme.colors.examBackground),
			style = MaterialTheme.typography.titleLarge,
			fontWeight = FontWeight.Bold,
			textAlign = TextAlign.Center
		)
		//Divider(thickness = Dp.Hairline, color = Color.Black, modifier = Modifier.fillMaxWidth())
		ScoreRow(
			modifier = Modifier
				.fillMaxWidth()
				.background(StatsRowBackGroundLight),
			disciplineScoring = row.scoringType,
			firstAttestationScore = row.firstAttestationScore,
			secondAttestationScore = row.secondAttestationScore,
			thirdAttestationScore = row.thirdAttestationScore,
			examScore = row.examScore,
			overallScore = row.resultScore
		)
	}
}

@Composable
fun ScoreRow(
	modifier: Modifier = Modifier,
	firstAttestationScore: Byte?,
	secondAttestationScore: Byte?,
	thirdAttestationScore: Byte?,
	examScore: Byte?,
	overallScore: Byte?,
	disciplineScoring: ScoringType
) {
	Row(
		modifier = modifier
			.height(IntrinsicSize.Max)
			.width(IntrinsicSize.Max)
			.background(MaterialTheme.colorScheme.surface)
			.padding(5.dp),
		horizontalArrangement = Arrangement.spacedBy(5.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		ScoreColumn(
			title = "А1",
			score = firstAttestationScore,
			modifier = Modifier.fillMaxWidth().weight(1f)
		)
		ScoreColumn(
			title = "А2",
			score = secondAttestationScore,
			modifier = Modifier.fillMaxWidth().weight(1f)
		)
		ScoreColumn(
			title = "А3",
			score = thirdAttestationScore,
			modifier = Modifier.fillMaxWidth().weight(1f)
		)
		if (disciplineScoring == ScoringType.EXAM) {
			ScoreColumn(
				title = "Э",
				score = examScore,
				modifier = Modifier.fillMaxWidth().weight(1f)
			)
		}
		ScoreColumn(
			title = "И",
			score = overallScore,
			modifier = Modifier.fillMaxWidth().weight(1f)
		)
	}
}

@Composable
fun ScoreColumn(
	modifier: Modifier = Modifier,
	title: String,
	score: Byte?
) {
	val colors = listOf(
		ExtendedTheme.colors.noGradeBackground,
		ExtendedTheme.colors.badGradeBackground,
		ExtendedTheme.colors.fineGradeBackground,
		ExtendedTheme.colors.goodGradeBackground,
		ExtendedTheme.colors.excellentGradeBackground
	)
	val backgroundColor: Color = remember {
		var backgroundColorLocal = colors[0]
		var coefficient: Byte = 1
		if (title == "И") {
			coefficient = 2
		}
		if (score == null) {
			return@remember backgroundColorLocal
		}
		when (score) {
			in 0..24 * coefficient -> {
				backgroundColorLocal = colors[1]
			}

			in 25 * coefficient..34 * coefficient -> {
				backgroundColorLocal = colors[2]
			}

			in 35 * coefficient..44 * coefficient -> {
				backgroundColorLocal = colors[3]
			}

			in 45 * coefficient..50 * coefficient -> {
				backgroundColorLocal = colors[4]
			}
		}
		backgroundColorLocal
	}
	val backgroundColorState = remember {
		mutableStateOf(backgroundColor)
	}
	Column(
		modifier = modifier
			.width(IntrinsicSize.Max)
			.background(
				color = backgroundColorState.value,
				shape = RoundedCornerShape(8.dp)
			),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(0.dp)
	) {
		Column(modifier = Modifier.padding(SCORE_COLUMN_PADDING).width(IntrinsicSize.Max)) {
			Text(
				modifier = Modifier.fillMaxWidth(),
				textAlign = TextAlign.Center,
				text = title,
				fontWeight = FontWeight.Black,
				fontSize = 16.sp
			)
			Box(
				modifier = Modifier
			) {
				Text(
					text = "${score ?: ""}",
					fontWeight = FontWeight.Black,
					fontSize = 20.sp
				)
			}
		}
	}
}