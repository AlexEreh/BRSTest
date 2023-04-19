package login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(component: LoginComponent){
	val credentials by component.credentials.subscribeAsState()

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = Modifier
			.fillMaxSize()
			.width(IntrinsicSize.Max)
			.height(IntrinsicSize.Max)
	) {
		TextField(
			value = credentials.login,
			singleLine = true,
			placeholder = {
				Text("Логин")
			},
			onValueChange = {
				component.updateLogin(it)
			}
		)
		TextField(
			value = credentials.password,
			singleLine = true,
			placeholder = {
				Text("Пароль")
			},
			onValueChange = {
				component.updatePassword(it)
			},
			visualTransformation = PasswordVisualTransformation()
		)
		Button(onClick = {
			component.onLoginButtonClicked()
		}) {
			Text(text = "Войти")
		}
	}
}