package login.model

import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Credentials(
	var login: String = "",
	var password: String = "",
)