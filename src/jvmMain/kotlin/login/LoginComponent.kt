package login

import com.arkivanov.decompose.value.Value
import login.model.Credentials

interface LoginComponent {
	val credentials: Value<Credentials>
	fun onLoginButtonClicked()
	fun updateLogin(newLoginText: String)
	fun updatePassword(newPasswordText: String)
}

