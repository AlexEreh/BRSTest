package skrape

import it.skrape.core.htmlDocument
import it.skrape.fetcher.*
import it.skrape.selects.DocElement
import it.skrape.selects.html5.tbody
import it.skrape.selects.html5.tr
import stats.model.StatisticRow
import stats.model.ScoringType


fun getGrades(login: String, password: String): List<StatisticRow> {
    val resultList = mutableListOf<StatisticRow>()
	val cookiesList: MutableList<Cookie> = mutableListOf()
	skrape(BrowserFetcher) {
		request {
			method = Method.POST
			url = "https://www.cs.vsu.ru/brs/login"
			timeout = 10_000
			followRedirects = true
			body {
				form {
					"login" to login
					"password" to password
					"user_type" to ""
					"button_login" to "Вход"
				}
			}
		}
		response {
			cookies {
				cookiesList += this
			}
			htmlDocument {
				tbody {
					findFirst {
						tr {
							findAll {
								forEach { oneTr ->
									resultList += getRowFromTr(oneTr)
								}
							}
						}
					}
				}
			}
		}

	}

	return resultList
}

fun getRowFromTr(tr: DocElement): StatisticRow {
	val resultTdsStrings = tr.children.map { element ->
		element.ownText.trim(' ')
	}

	val scoringType = ScoringType.getTypeFromString(resultTdsStrings[4])
	var examScore: Byte? = null
	if (scoringType == ScoringType.EXAM){
		examScore = parsePossiblyEmptyGrade(resultTdsStrings[10])
	}

	return StatisticRow(
		studyYear = resultTdsStrings[0],
		semesterNumber = resultTdsStrings[1].toByte(),
		courseNumber = resultTdsStrings[2].toByte(),
		disciplineName = resultTdsStrings[3],
		scoringType = scoringType,
		tutor = resultTdsStrings[5],
		firstAttestationScore = parsePossiblyEmptyGrade(resultTdsStrings[6]),
		secondAttestationScore = parsePossiblyEmptyGrade(resultTdsStrings[7]),
		thirdAttestationScore = parsePossiblyEmptyGrade(resultTdsStrings[8]),
		examScore = examScore,
		additionalScore = parsePossiblyEmptyGradeOrSlash(resultTdsStrings[11]),
		resultScore = parsePossiblyEmptyGrade(resultTdsStrings[12])
	)
}

fun parsePossiblyEmptyGrade(str: String): Byte? {
	var result: Byte? = null
	if (str.isNotBlank()) {
		result = str.toByte()
	}
	return result
}

fun parsePossiblyEmptyGradeOrSlash(str: String): Byte? {
	var result: Byte? = null
	if (str.isNotBlank() && str != "—") {
		result = str.toByte()
	}
	return result
}