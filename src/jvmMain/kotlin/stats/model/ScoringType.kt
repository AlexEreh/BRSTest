package stats.model

@Suppress("unused")
enum class ScoringType(var type: String) {
	EXAM("Экзамен"),
	CREDIT("Зачёт"),
	DIFFERENTIATED_CREDIT("Зачёт с оценкой");
	companion object {
		fun getTypeFromString(str: String): ScoringType {
			when (str) {
				EXAM.type -> {
					return EXAM
				}
				CREDIT.type -> {
					return CREDIT
				}
				DIFFERENTIATED_CREDIT.type -> {
					return DIFFERENTIATED_CREDIT
				}
			}
			return CREDIT
		}
	}

}