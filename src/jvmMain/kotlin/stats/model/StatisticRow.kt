package stats.model

data class StatisticRow (
	val studyYear: String,
	val semesterNumber: Byte,
	val courseNumber: Byte,
	val disciplineName: String,
	val scoringType: ScoringType,
	val tutor: String,
	val firstAttestationScore: Byte? = null,
	val secondAttestationScore: Byte? = null,
	val thirdAttestationScore: Byte? = null,
	val examScore: Byte? = null,
	val additionalScore: Byte? = null,
	val resultScore: Byte? = null,
)

