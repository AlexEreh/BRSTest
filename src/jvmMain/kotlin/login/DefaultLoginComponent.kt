package login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import login.model.Credentials

class DefaultLoginComponent(
	componentContext: ComponentContext,
	private val onLoginClicked: (login: String, password: String) -> Unit
): LoginComponent, ComponentContext by componentContext {
	private val _credentials = MutableValue(Credentials())
	override val credentials: Value<Credentials> = _credentials
	override fun onLoginButtonClicked() {
		onLoginClicked(credentials.value.login, credentials.value.password)
	}

	override fun updateLogin(newLoginText: String) {
		_credentials.update { Credentials(newLoginText, it.password) }
	}

	override fun updatePassword(newPasswordText: String) {
		_credentials.update { Credentials(it.login, newPasswordText) }
	}
}