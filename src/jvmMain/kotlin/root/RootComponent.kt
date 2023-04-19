package root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import login.LoginComponent
import stats.StatsComponent

interface RootComponent {

	val stack: Value<ChildStack<*, Child>>

	// Defines all possible child components
	sealed class Child {
		class LoginChild(val component: LoginComponent) : Child()
		class StatsChild(val component: StatsComponent) : Child()
	}
}

