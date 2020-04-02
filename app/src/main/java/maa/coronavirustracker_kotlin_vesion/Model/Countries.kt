package maa.coronavirustracker_kotlin_vesion.Model


data class Country(
    val country: String,
    val countryInfo: CountryInfo,
    val cases: Long,
    val todayCases: Long,
    val deaths: Long,
    val todayDeaths: Long,
    val recovered: Long,
    val active: Long,
    val critical: Long,
    val casesPerOneMillion: Double? = null,
    val deathsPerOneMillion: Double? = null,
    val updated: Long
)

data class CountryInfo(
    val id: Long? = null,
    val iso2: String? = null,
    val iso3: String? = null,
    val lat: Double,
    val long: Double,
    val flag: String
)
