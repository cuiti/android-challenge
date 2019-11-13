package es.npatarino.android.gotchallenge.model

object MockData {

    private val characterA = Character("aaa","","This is a description","","house","house")
    private val characterB = Character("abc","","Different description","","house","house")

    val characters = listOf(characterA, characterB)
    val houses = listOf(characterA.house)

    const val multipleResultSearchInput = "a"
    const val singleResultSearchInput = "ab"
    const val zeroResultSearchInput = "zzzzz"
    val multipleResultSearchResult = characters
    val singleResultSearchResult = listOf(characterB)
}