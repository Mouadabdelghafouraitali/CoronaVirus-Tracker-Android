package maa.coronavirustracker_kotlin_vesion.Model

data class All(
    val cases: Long,
    val deaths: Long,
    val recovered: Long,
    val updated: Long,
    val active: Long,
    val affectedCountries: Long
)
