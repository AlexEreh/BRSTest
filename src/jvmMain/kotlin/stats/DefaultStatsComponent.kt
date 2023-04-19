package stats

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import skrape.getGrades
import stats.model.StatisticRow

class DefaultStatsComponent(
	componentContext: ComponentContext,
	var loginIn: String,
	var passwordIn: String,
	private val onExit: () -> Unit
): StatsComponent, ComponentContext by componentContext {
	private var _model = MutableValue(StatsComponent.Model(stats = emptyList()))
	override val model: Value<StatsComponent.Model> = _model

	override fun loadStats() {
		val grades: List<StatisticRow> = getGrades(loginIn, passwordIn)
		_model.update { StatsComponent.Model(grades) }
	}

	override fun exitStats() {
		onExit()
	}
}