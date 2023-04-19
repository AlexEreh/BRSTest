package root

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import login.LoginContent
import stats.StatsContent
import ui.theme.CSFBRSTheme

@Composable
@Preview
fun RootContent(component: RootComponent) {
	val componentStack by component.stack.subscribeAsState()
	CSFBRSTheme {
		Children(
			stack = componentStack,
			modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
			//animation = stackAnimation(fade() + scale()),
		) {
			when (val child = it.instance) {
				is RootComponent.Child.LoginChild -> LoginContent(component = child.component)
				is RootComponent.Child.StatsChild -> StatsContent(component = child.component)
			}
		}
	}
}