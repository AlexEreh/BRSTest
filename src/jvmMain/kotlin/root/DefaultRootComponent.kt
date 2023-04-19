package root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import login.DefaultLoginComponent
import login.LoginComponent
import login.model.Credentials
import root.RootComponent.Child.LoginChild
import root.RootComponent.Child.StatsChild
import stats.DefaultStatsComponent
import stats.StatsComponent

class DefaultRootComponent(
	componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

	private val navigation = StackNavigation<Config>()

	private val _stack =
		childStack(
			source = navigation,
			initialConfiguration = Config.Login,
			handleBackButton = true,
			childFactory = ::child,
		)

	override val stack: Value<ChildStack<*, RootComponent.Child>> = _stack

	private fun child(
		config: Config,
		componentContext: ComponentContext
	): RootComponent.Child {
		return when (config) {
			is Config.Login -> LoginChild(loginComponent(componentContext))
			is Config.Stats -> StatsChild(statsComponent(componentContext, config))
		}
	}

	private fun loginComponent(
		componentContext: ComponentContext
	): LoginComponent {
		return DefaultLoginComponent(
			componentContext = componentContext,
			onLoginClicked = { login: String, password: String ->
				println(_stack.value)
				navigation.push(
					Config.Stats(
						Credentials(login = login, password = password)
					)
				)
				println(_stack.value)
			},
		)
	}

	private fun statsComponent(
		componentContext: ComponentContext,
		config: Config.Stats
	): StatsComponent {
		println(_stack.value)
		return DefaultStatsComponent(
			componentContext = componentContext,
			loginIn = config.credentials.login,
			passwordIn = config.credentials.password,
			onExit = navigation::pop,
		)
	}


	private sealed class Config : Parcelable {
		@Parcelize
		object Login : Config()
		@Parcelize
		data class Stats(val credentials: Credentials) : Config()
	}
}