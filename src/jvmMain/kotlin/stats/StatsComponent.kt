package stats

import com.arkivanov.decompose.value.Value
import stats.model.StatisticRow

interface StatsComponent {
	val model: Value<Model>

	fun loadStats()
	fun exitStats()
	data class Model(
		var stats: List<StatisticRow>,
	)
}